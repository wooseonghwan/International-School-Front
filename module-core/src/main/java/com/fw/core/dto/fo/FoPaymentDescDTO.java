package com.fw.core.dto.fo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoPaymentDescDTO {
    private String fDate;
    private String rtn;
    private String ilno;
    private String snno;
    private String loc;
    private String custNo;
    private String gbn;
    private int amnt;
    private String menuCd;
    private String menuNm;
    private String balance;
    private String cDate;
    private String syncDate;
    private String bigo;
}
