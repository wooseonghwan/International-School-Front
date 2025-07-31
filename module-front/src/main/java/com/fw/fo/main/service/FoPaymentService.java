package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.core.dto.fo.FoPaymentDescDTO;
import com.fw.core.dto.kicc.*;
import com.fw.core.mapper.db1.fo.FoPaymentMapper;
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
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 요청 엔티티 구성
        HttpEntity<KiccTransactionDTO> requestEntity = new HttpEntity<>(kiccTransactionDTO, headers);
        // 요청
        try {
            ResponseEntity<KiccTransactionResDTO> response = restTemplate.exchange(
                    KICC_TRANSACTION_URL,
                    HttpMethod.POST,
                    requestEntity,
                    KiccTransactionResDTO.class
            );
            KiccTransactionResDTO result = response.getBody();
            assert result != null;
            log.info("응답코드: {}, 응답메세지: {}, 결제창 URL: {}", result.getResCd(), result.getResMsg(), result.getAuthPageUrl());
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
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 요청 엔티티 구성
        HttpEntity<KiccTransactionApprovalReqDTO> requestEntity = new HttpEntity<>(kiccTransactionApprovalReqDTO, headers);
        // 요청
        KiccTransactionApprovalResDTO kiccTransactionApprovalRes;
        try {
            ResponseEntity<KiccTransactionApprovalResDTO> response = restTemplate.exchange(
                    KICC_TRANSACTION_APPROVAL_URL,
                    HttpMethod.POST,
                    requestEntity,
                    KiccTransactionApprovalResDTO.class
            );
            kiccTransactionApprovalRes = response.getBody();
            assert kiccTransactionApprovalRes != null;
            log.info("응답코드: {}, 응답메세지: {}", kiccTransactionApprovalRes.getResCd(), kiccTransactionApprovalRes.getResMsg());
        } catch (Exception e) {
            // 예외 발생 시 이후 처리 없이 바로 return
            log.error("KICC 승인 API 호출 예외 발생", e);
            return "FAIL";
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
            log.error("KICC 승인완료 > 결제(충전)내역 DB등록 오류발생", e);
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
}
