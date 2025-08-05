package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.core.dto.fo.FoPaymentDescDTO;
import com.fw.core.dto.kicc.*;
import com.fw.core.mapper.db1.fo.FoPaymentMapper;
import com.fw.core.util.KiccUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoPaymentService {
    // KICC MID
    @Value("${kicc.mid}")
    private String KICC_MID;
    // KICC 결제등록 URL
    @Value("${kicc.transaction-url}")
    private String KICC_TRANSACTION_URL;
    // KICC 결제콜백 URL
    @Value("${kicc.transaction-callback-url}")
    private String KICC_TRANSACTION_CALLBACK_URL;
    // KICC 결제승인 URL
    @Value("${kicc.transaction-approval-url}")
    private String KICC_TRANSACTION_APPROVAL_URL;
    // KICC 결제취소 URL
    @Value("${kicc.transaction-revise-url}")
    private String KICC_TRANSACTION_REVISE_URL;
    // KICC SecretKey
    @Value("${kicc.secret-key}")
    private String KICC_SECRET_KEY;

    private final RestTemplate restTemplate;
    private final FoPaymentMapper foPaymentMapper;

    // KICC 거래등록
    public KiccTransactionResDTO kiccTransaction(KiccTransactionDTO kiccTransactionDTO) {
        // =================================================
        //              결제창 호출을 위한 파라미터 설정
        // =================================================
        // MID
        kiccTransactionDTO.setMallId(KICC_MID);
        // 결제수단 코드 (신용카드만 가능 - 11고정)
        kiccTransactionDTO.setPayMethodTypeCode("11");
        // 통화코드 (원화 - 00고정)
        kiccTransactionDTO.setCurrency("00");
        // 인증완료 후 이동 URL
        kiccTransactionDTO.setReturnUrl(KICC_TRANSACTION_CALLBACK_URL);
        // 고객코드 (00고정)
        kiccTransactionDTO.setClientTypeCode("00");

        // =================================================
        //                 KICC 결제등록 API 호출
        // =================================================
        // 요청
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 요청 엔티티 구성
            HttpEntity<KiccTransactionDTO> requestEntity = new HttpEntity<>(kiccTransactionDTO, headers);

            ResponseEntity<KiccTransactionResDTO> response = restTemplate.exchange(
                    KICC_TRANSACTION_URL,
                    HttpMethod.POST,
                    requestEntity,
                    KiccTransactionResDTO.class
            );
            KiccTransactionResDTO result = response.getBody();
            assert result != null;
            log.info("[결제등록 API] 응답코드: {}, 응답메세지: {}, 결제창 URL: {}", result.getResCd(), result.getResMsg(), result.getAuthPageUrl());
            return result;
        } catch (Exception e) {
            log.error("KICC 거래등록 중 예외 발생", e);
        }
        return null;
    }

    // KICC 결제승인
    @Transactional
    public String kiccTransactionApproval(KiccTransactionCallbackDTO kiccTransactionCallbackDTO) {
        // 기본 return 값 설정
        String resultCode = "SUCCESS";
        // =================================================
        //            승인 API 호출을 위한 파라미터 설정
        // =================================================
        // 일별 고유 트랜잭션 ID 생성
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String shopTransactionId = today + "-" + kiccTransactionCallbackDTO.getShopOrderNo();
        // DTO 생성
        KiccTransactionApprovalReqDTO kiccTransactionApprovalReqDTO = KiccTransactionApprovalReqDTO.builder()
                .mallId(KICC_MID)
                .shopTransactionId(shopTransactionId)
                .authorizationId(kiccTransactionCallbackDTO.getAuthorizationId())
                .shopOrderNo(kiccTransactionCallbackDTO.getShopOrderNo())
                .approvalReqDate(today)
                .build();

        // =================================================
        //                 KICC 승인 API 호출
        // =================================================
        // 요청
        KiccTransactionApprovalResDTO kiccTransactionApprovalRes;
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 요청 엔티티 구성
            HttpEntity<KiccTransactionApprovalReqDTO> requestEntity = new HttpEntity<>(kiccTransactionApprovalReqDTO, headers);

            ResponseEntity<KiccTransactionApprovalResDTO> response = restTemplate.exchange(
                    KICC_TRANSACTION_APPROVAL_URL,
                    HttpMethod.POST,
                    requestEntity,
                    KiccTransactionApprovalResDTO.class
            );
            kiccTransactionApprovalRes = response.getBody();
            assert kiccTransactionApprovalRes != null;
            log.info("[승인 API] 응답코드: {}, 응답메세지: {}", kiccTransactionApprovalRes.getResCd(), kiccTransactionApprovalRes.getResMsg());
            // 승인 요청 처리 실패 시, 기존 화면 이동 (결제취소 or 비정상요청)
            if (!"0000".equals(kiccTransactionApprovalRes.getResCd())) {
                return "";
            }
        } catch (Exception e) {
            // 예외 발생 시 이후 처리 없이 바로 return
            log.error("KICC 승인 API 호출 예외 발생", e);
            return "FAIL";
        }

        // 관리자 처리용 취소 페이로드 로그
        String shopTransactionIdForCancel = KiccUtil.generateShopTransactionId();
        log.info("[Transaction Cancel Payload] {}", String.format(
                "{\n" +
                        "    \"mallId\": \"%s\",\n" +
                        "    \"shopTransactionId\": \"%s\",\n" +
                        "    \"pgCno\": \"%s\",\n" +
                        "    \"reviseTypeCode\": \"40\",\n" +
                        "    \"cancelReqDate\": \"%s\",\n" +
                        "    \"msgAuthValue\": \"%s\"\n" +
                        "}",
                KICC_MID,
                shopTransactionIdForCancel,
                kiccTransactionApprovalRes.getPgCno(),
                today,
                KiccUtil.generateHmac(String.format("%s|%s", kiccTransactionApprovalRes.getPgCno(), shopTransactionIdForCancel), KICC_SECRET_KEY)
        ));

        // =================================================
        //     승인 정상 처리 이후 결제 요청 금액 <-> 승인 금액 비교
        // =================================================
        // 결제 요청 금액
        int requestAmount = Integer.parseInt(kiccTransactionCallbackDTO.getShopValue3());
        // 실제 승인 금액
        int approvalAmount = kiccTransactionApprovalRes.getAmount();
        // 다를 경우 취소 처리
        if (requestAmount != approvalAmount) {
            log.error("결제 요청 금액과 실제 승인 금액이 일치하지 않습니다. 해당 결제를 취소합니다.");
            try {
                // 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // 취소 요청 파라미터 구성
                String cancel_shopTransactionId = KiccUtil.generateShopTransactionId();
                KiccTransactionReviseReqDTO kiccTransactionReviseReqDTO = KiccTransactionReviseReqDTO.builder()
                        .mallId(KICC_MID)
                        .shopTransactionId(cancel_shopTransactionId)
                        .pgCno(kiccTransactionApprovalRes.getPgCno())
                        .reviseTypeCode("40")
                        .cancelReqDate(today)
                        .msgAuthValue(KiccUtil.generateHmac(String.format("%s|%s", kiccTransactionApprovalRes.getPgCno(), cancel_shopTransactionId), KICC_SECRET_KEY))
                        .build();

                // 요청 엔티티 구성
                HttpEntity<KiccTransactionReviseReqDTO> requestEntity = new HttpEntity<>(kiccTransactionReviseReqDTO, headers);

                ResponseEntity<KiccTransactionReviceResDTO> response = restTemplate.exchange(
                        KICC_TRANSACTION_REVISE_URL,
                        HttpMethod.POST,
                        requestEntity,
                        KiccTransactionReviceResDTO.class
                );

                // 요청결과
                KiccTransactionReviceResDTO kiccTransactionReviceRes = response.getBody();
                log.info("[취소 API] 응답코드: {}, 응답메세지: {}", kiccTransactionReviceRes.getResCd(), kiccTransactionReviceRes.getResMsg());
                // 금액이 달라 취소의 경우, 결제가 이루어지지 않은 것과 동일하게 간주 빈값 return
                return "";
            } catch (Exception e) {
                log.error("KICC 취소 API 호출 예외 발생", e);
                return "FAIL";
            }
        }

        // =================================================
        //                결제(충전)내역 DB등록
        // =================================================
        try {
            // 결제(충전)내역 둥록
            FoPaymentDTO foPaymentDTO = FoPaymentDTO.builder()
                    .ilno(foPaymentMapper.getIlnoForRegisterPayment())
                    .custNo(kiccTransactionCallbackDTO.getShopValue1())
                    .amnt(kiccTransactionApprovalRes.getAmount())
                    .build();
            foPaymentMapper.registerPayment(foPaymentDTO);
            // 결제(충전)내역 상세 등록
            FoPaymentDescDTO foPaymentDescDTO = FoPaymentDescDTO.builder()
                    .ilno(foPaymentDTO.getIlno())
                    .snno(foPaymentMapper.getSnnoForRegisterPaymentDesc())
                    .custNo(kiccTransactionCallbackDTO.getShopValue1())
                    .amnt(kiccTransactionApprovalRes.getAmount())
                    .build();
            foPaymentMapper.registerPaymentDesc(foPaymentDescDTO);
        } catch (Exception e) {
            log.error("KICC 승인완료 > 결제(충전)내역 DB등록 오류발생 > 해당 결제를 취소합니다.", e);
            try {
                // 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // 취소 요청 파라미터 구성
                String cancel_shopTransactionId = KiccUtil.generateShopTransactionId();
                KiccTransactionReviseReqDTO kiccTransactionReviseReqDTO = KiccTransactionReviseReqDTO.builder()
                        .mallId(KICC_MID)
                        .shopTransactionId(cancel_shopTransactionId)
                        .pgCno(kiccTransactionApprovalRes.getPgCno())
                        .reviseTypeCode("40")
                        .cancelReqDate(today)
                        .msgAuthValue(KiccUtil.generateHmac(String.format("%s|%s", kiccTransactionApprovalRes.getPgCno(), cancel_shopTransactionId), KICC_SECRET_KEY))
                        .build();

                // 요청 엔티티 구성
                HttpEntity<KiccTransactionReviseReqDTO> requestEntity = new HttpEntity<>(kiccTransactionReviseReqDTO, headers);

                ResponseEntity<KiccTransactionReviceResDTO> response = restTemplate.exchange(
                        KICC_TRANSACTION_REVISE_URL,
                        HttpMethod.POST,
                        requestEntity,
                        KiccTransactionReviceResDTO.class
                );

                // 요청결과
                KiccTransactionReviceResDTO kiccTransactionReviceRes = response.getBody();
                log.info("[취소 API] 응답코드: {}, 응답메세지: {}", kiccTransactionReviceRes.getResCd(), kiccTransactionReviceRes.getResMsg());
            } catch (Exception e1) {
                log.error("KICC 취소 API 호출 예외 발생", e1);
            }
            return "FAIL";
        }
        return resultCode;
    }

    public List<FoPaymentDTO> selectPaymentChargeList(FoPaymentDTO foPaymentDTO){
        return foPaymentMapper.selectPaymentChargeList(foPaymentDTO);
    }

    public int selectPaymentChargeListCnt(FoPaymentDTO foPaymentDTO){
        return foPaymentMapper.selectPaymentChargeListCnt(foPaymentDTO);
    }

    public List<FoPaymentDTO> selectPaymentUseList(FoPaymentDTO foPaymentDTO){
        return foPaymentMapper.selectPaymentUseList(foPaymentDTO);
    }

    public int selectPaymentUseListCnt(FoPaymentDTO foPaymentDTO){
        return foPaymentMapper.selectPaymentUseListCnt(foPaymentDTO);
    }

    public List<FoPaymentDTO> getTargeListtForSyncBalance() {
        return foPaymentMapper.getTargeListtForSyncBalance();
    }

    @Transactional
    public void syncBalance(List<FoPaymentDTO> targetList) {
        // 잔액 동기화 처리
        targetList.forEach(target -> {
            // balance 설정 (gbn - CHARGE 충전이외 모두 사용)
            int balance = "CHARGE".equals(target.getGbn()) ? (target.getCurrentBalance() + target.getAmnt()) : (target.getCurrentBalance() - target.getAmnt());
            // 잔액 동기화
            target.setBalance(balance);
            foPaymentMapper.syncBalance(target);
        });
    }

}
