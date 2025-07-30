package com.fw.core.dto.kicc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiccTransactionDTO {
    // KICC에서 부여한 상점ID
    private String mallId;
    // 상점 주문번호 (반드시 Unique 값으로 생성)
    private String shopOrderNo;
    // 결제요청 금액
    private int amount;
    // 결제수단 코드
    private String payMethodTypeCode;
    // 통화코드
    private String currency;
    // 인증 완료 후 이동할 URL
    private String returnUrl;
    // 결제고객 단말구분(pc/mobile)
    private String deviceTypeCode;
    // 고정값 "00"
    private String clientTypeCode;
    // KOR: 한국어, ENG:영어, JPN:일본어, CHN:중국어
    private String langFlag;
    // 결제 주문정보
    private KiccOrderInfo orderInfo;
    // 가맹점 필드 (승인 및 노티 응답으로 전송됨)
    private KiccShopValueInfo shopValueInfo;

    // DTO 내 존재하지 않는 항목들은 불필요 판단
    // 추후 필요 시, 추가 필요
    // 참고링크: https://developer.easypay.co.kr/integrated-api/payRegistration#orderInfo
}
