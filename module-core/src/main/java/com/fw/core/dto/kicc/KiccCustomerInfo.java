package com.fw.core.dto.kicc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiccCustomerInfo {
    // 고객ID
    private String customerId;
    // 고객 명
    private String customerName;
    // 고객 Email(에스크로 사용 시 필수)
    private String customerMail;
    // 고객 연락처(숫자만 허용)
    private String customerContactNo;
    // 고객 주소
    private String customerAddr;
}
