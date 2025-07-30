package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoPaymentDTO extends CommonDTO {
    private String fDate;
    private String cDate;
    private String custName;
    private String custNo;
    private Integer balance;
    private String gbn;
    private String rtn;
    private Integer amnt;
    private String ilno;
    private String snno;
    private String loc;
    private String menuCd;
    private String menuNm;
    private String syncDate;
    private String bigo;
}
