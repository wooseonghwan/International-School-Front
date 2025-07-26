package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoGolfPriceDTO extends CommonDTO {

    private String idx;
    private String golfName;
    private String person;
    private String days;
    private String startDate;
    private String endDate;
    private String lodging;
    private String totalPrice;
    private String day;
}
