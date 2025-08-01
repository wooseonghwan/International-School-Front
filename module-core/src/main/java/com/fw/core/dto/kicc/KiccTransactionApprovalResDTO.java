package com.fw.core.dto.kicc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KiccTransactionApprovalResDTO {
    // 결과코드
    private String resCd;
    // 결과 메시지
    private String resMsg;
    // 총 결제금액
    private int amount;
    // 가맹점 트랜젝션ID
    private String shopTransactionId;
    // PG 승인거래번호
    private String pgCno;
}
