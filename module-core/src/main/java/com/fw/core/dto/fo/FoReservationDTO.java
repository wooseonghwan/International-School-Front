package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FoReservationDTO extends CommonDTO {

    private String idx;
    private String golfName;
    private String person;
    private String days;
    private String startDate;
    private String endDate;
    private String lodging;
    private String totalPrice;
    private String reserveNm;
    private String reservePhone;
    private String regDt;
    private String reserveSatus;
    private String marketingYn;
    private String recommendNm;
    private String recommendPhone;
}
