package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoExpertDTO extends CommonDTO {
    private String expertSeq;
    private String formStatus;
    private String id;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String birth;
    private String email;
    private String nation;
    private String needTime;
    private String jobCategory;
    private String jobCategoryEtc;
    private String linkedinUrl;
    private String selfIntroKr;
    private String selfIntroEn;
    private String fileSeq;
    private String subSeq;
    private List<String> advisoryNeedTime;
    private String advisoryNeedTimeDb;
    private String weekDayOption;
    private String weekDayAdvisoryStartDt;
    private String weekDayAdvisoryEndDt;
    private String weekendOption;
    private String weekendAdvisoryStartDt;
    private String weekendAdvisoryEndDt;
    private String advisoryLang1;
    private String advisoryLang2;
    private String timeAdvisory;
    private String timeAdvisoryOption;
    private String infoAgreeYn;
    private String useAgreeYn;
    private String expertSolutionYn;


    // tb_career
    private String careerSeq;
    private String companyName;
    private String rankName;
    private String careerStartDt;
    private String careerEndDt;
    private String job;

    private List<FoExpertDTO> expertList;
    private FoExpertDTO advisoryDetail;
    private FoExpertDTO weekday;
    private FoExpertDTO weekend;
    private List<FoExpertDTO> advisoryNeedTimes;
    private MultipartFile[] expertfile;
    private MultipartFile[] expertsub;

    private String expertListStr;
    private String advisoryDetailStr;
    private FoExpertDTO etc;
    private String area;
    private String areaEtc;

    private FoExpertDTO jobCategoryDetails;
    private FoExpertDTO jobCategoryDetails1;
    private List<String> jobCategoryDetail;
    private String jobCategoryDetailDb;
}
