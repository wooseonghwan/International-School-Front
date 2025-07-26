package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoClientDTO extends CommonDTO {
    private String clientSeq;
    private String id;
    private String password;
    private String companyName;
    private String companyCategory;
    private String companyCategoryEtc;
    private String managerName;
    private String managerPhone;
    private String managerEmail;
    private String nation;
    private String needTime;
    private String consultingRequestTime;
    private String consultingRequestTimeDetail;
    private String consultingRequestTimeEtc;
    private String consultingSubject;
    private String coreQuestion;
    private String timeNeedAdvisory;
    private String timeNeedAdvisoryEtc;
    private String infoAgreeYn;
    private String useAgreeYn;

    // tb_consulting_category
    private String consultingCateSeq;
    private String consultingCategoryDb;
    private String consultingCateEtc;

    // tb_consulting_method
    private String consultingMethodSeq;
    private String consultingMethodDb;
    private String consultingMethodEtc;

    // tb_etc_request
    private String etcRequestSeq;
    private String etcRequestDb;

    // tb_expert_need
    private String expertNeedSeq;
    private String expertNeedDb;
    private String expertNeedEtc;

    private FoClientDTO consultingCategoryDetail;
    private FoClientDTO expertNeedDetail;
    private FoClientDTO consultingMethodDetail;
    private FoClientDTO etcRequestDetail;
    private FoClientDTO etc;
    private List<String> consultingCategory;
    private List<String> consultingMethod;
    private List<String> etcRequest;
    private List<String> expertNeed;
}
