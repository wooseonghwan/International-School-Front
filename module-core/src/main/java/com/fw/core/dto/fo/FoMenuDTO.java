package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoMenuDTO extends CommonDTO {
    private String menuId;
    private String fileOrder;
    private String fileSeq;
    private String fileName;
    private String fileUrl;
}
