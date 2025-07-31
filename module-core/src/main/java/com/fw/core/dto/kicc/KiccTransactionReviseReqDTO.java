package com.fw.core.dto.kicc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiccTransactionReviseReqDTO {
    // KICC에서 부여한 상점ID
    private String mallId;
    // 가맹점 트랜젝션ID (중복요청을 방지를 위해 일별로 Unique 보장)
    private String shopTransactionId;
    // PG 거래번호(원거래 PG 거래번호)
    private String pgCno;
    // 변경구분(전체취소: "40", 부분취소: "32")
    private String reviseTypeCode;
    // 취소 요청일자(yyyyMMdd) 반드시 현재일자로 처리
    private String cancelReqDate;
    // 메시지 인증값(무결성 검증)
    private String msgAuthValue;
}
