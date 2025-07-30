package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoNoticeDTO extends CommonDTO {
    private int noticeId;
    private String title;
    private String content;
    private int fileId;
    private String createId;
    private String updateId;
    private String createDt;
    private String updateDt;
    private String lang;
    private String delYn;
    private String popupType;
    private String optionYn;
    private String width;
    private String height;
    private String left;
    private String top;
    private String endDt;
    private String endNoLimitYn;
    private String popupYn;
    private String adminNm;
    private String popupYnNm;
    private String langNm;
}
