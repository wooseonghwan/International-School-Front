package com.fw.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDTO extends CommonDTO {

    private String id;
    private String fileSeq;
    private String ext;
    private String height;
    private String isImage;
    private String name;
    private String originName;
    private String path;
    private String size;
    private String url;
    private String width;
    private String delFlag;
    private String createSeq;
    private String updateSeq;

}
