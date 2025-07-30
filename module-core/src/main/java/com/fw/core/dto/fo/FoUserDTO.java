package com.fw.core.dto.fo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoUserDTO {
    private String custNo;
    private String custName;
    private String cardId;
    private String webId;
    private String webPw;
    private String cDate;
    private String syncDate;
    private String bigo;
}
