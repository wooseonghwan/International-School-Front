package com.fw.core.dto.kicc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KiccTransactionResDTO {
    // 결과코드(정상: "0000")
    private String resCd;
    // 결과 메시지
    private String resMsg;
    // 결제창 호출 URL
    private String authPageUrl;
}
