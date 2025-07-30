package com.fw.core.dto.kicc;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KiccOrderInfo {
    // 상품명
    private String goodsName;
    // 주문 고객정보
    private KiccCustomerInfo customerInfo;
}
