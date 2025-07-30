package com.fw.core.dto.kicc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiccTransactionApprovalReqDTO {
    // KICC에서 부여한 상점ID
    private String mallId;
    // 가맹점 트랜젝션ID (중복요청을 방지를 위해 일별로 Unique 보장)
    private String shopTransactionId;
    // 인증 거래번호 (결제창 호출 후 받은 값 그대로 사용)
    private String authorizationId;
    // 상점 주분번호 (결제창 호출 후 받은 값 그대로 사용)
    private String shopOrderNo;
    // 승인요청일자(yyyyMMdd)
    private String approvalReqDate;
}
