package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoNoticeBoardDTO extends CommonDTO {
    private int qnaId;
    private String title;
    private String reply;
    private String content;
    private String createId;
    private String replyId;
    private String createDt;
    private String replyDt;
    private String updateDt;
    private String updateId;
    private String custName;
    private String adminNm;
    private String viewCnt;
    private String replyCnt;
    private String passwordYn;
    private String password;
}
