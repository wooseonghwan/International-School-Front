package com.fw.core.dto.kicc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KiccTransactionCallbackDTO {
    // 결과코드(정상 : "0000")
    private String resCd;
    // 결과 메시지
    private String resMsg;
    // 상점 주문번호. 결제등록 요청 시 전달받은 값
    private String shopOrderNo;
    // 인증 거래번호. 승인요청 시 필수 값
    private String authorizationId;
    // 필드1, 결제등록 요청 시 전달받은 값 - 고객번호
    private String shopValue1;
    // 필드2, 결제등록 요청 시 전달받은 값 - 언어
    private String shopValue2;
    // 필드3, 결제등록 요청 시 전달받은 값 - 사이트 내 결제금액
    private String shopValue3;
}
