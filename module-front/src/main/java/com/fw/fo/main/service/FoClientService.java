package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoClientDTO;
import com.fw.core.dto.fo.FoExpertDTO;
import com.fw.core.mapper.db1.fo.FoClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoClientService {
    private final FoClientMapper foClientMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int checkId(String id) {
        return foClientMapper.selectIdCheck(id);
    }

    public String insertClient(FoClientDTO foClientDTO) throws IOException {
        foClientDTO.setPassword(bCryptPasswordEncoder.encode(foClientDTO.getPassword()));
        foClientMapper.insertClient(foClientDTO);

        log.info("Idx :: {}", foClientDTO.getClientSeq());
        // 컨설팅 유형
        FoClientDTO consultingCategoryDetail = foClientDTO.getConsultingCategoryDetail();
        List<String> regList = consultingCategoryDetail.getConsultingCategory();
        if (regList != null) {
            if (regList.contains("ETC")) {
                FoClientDTO etcDTO = consultingCategoryDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setConsultingCategoryDb("ETC");
                foClientMapper.inserConsultingCategory(etcDTO);
            }
            if (regList.contains("ECC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("ECC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("IPPC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("IPPC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("ES")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("ES")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("PC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("PC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("SMR")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("SMR")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
        }
        // 전문가 선정
        FoClientDTO expertNeedDetail = foClientDTO.getExpertNeedDetail();
        List<String> regList1 = expertNeedDetail.getExpertNeed();
        if (regList1 != null) {
            if (regList1.contains("ETC")) {
                FoClientDTO etcDTO = expertNeedDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setExpertNeedDb("ETC");
                foClientMapper.inserExpertNeed(etcDTO);
            }
            if (regList1.contains("INCUM")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("INCUM")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
            if (regList1.contains("EXE")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("EXE")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
            if (regList1.contains("RETIRE3")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("RETIRE3")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
        }
        // 컨설팅 방식
        FoClientDTO consultingMethodDetail = foClientDTO.getConsultingMethodDetail();
        List<String> regList2 = consultingMethodDetail.getConsultingMethod();
        if (regList2 != null) {
            if (regList2.contains("ETC")) {
                FoClientDTO etcDTO = consultingMethodDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setConsultingMethodDb("ETC");
                foClientMapper.inserConsultingMethod(etcDTO);
            }
            if (regList2.contains("CALL")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("CALL")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
            if (regList2.contains("WRITE")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("WRITE")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
            if (regList2.contains("OFF")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("OFF")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
        }
        // 기타 요청사항
        FoClientDTO etcRequsetDetail = foClientDTO.getEtcRequestDetail();
        List<String> regList3 = etcRequsetDetail.getEtcRequest();
        if (regList3 != null) {
            if (regList3.contains("EXPERTS")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .etcRequestDb("EXPERTS")
                        .build();
                foClientMapper.inserEtcRequest(tfDTO);
            }
            if (regList3.contains("INTER")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .etcRequestDb("INTER")
                        .build();
                foClientMapper.inserEtcRequest(tfDTO);
            }
        }
        return foClientDTO.getClientSeq();
    }

    public String insertClientTemp(FoClientDTO foClientDTO) throws IOException {
        foClientDTO.setPassword(bCryptPasswordEncoder.encode(foClientDTO.getPassword()));
        foClientMapper.insertClientTemp(foClientDTO);

        log.info("Idx :: {}", foClientDTO.getClientSeq());
        // 컨설팅 유형
        FoClientDTO consultingCategoryDetail = foClientDTO.getConsultingCategoryDetail();
        List<String> regList = consultingCategoryDetail.getConsultingCategory();
        if (regList != null) {
            if (regList.contains("ETC")) {
                FoClientDTO etcDTO = consultingCategoryDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setConsultingCategoryDb("ETC");
                foClientMapper.inserConsultingCategory(etcDTO);
            }
            if (regList.contains("ECC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("ECC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("IPPC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("IPPC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("ES")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("ES")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("PC")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("PC")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
            if (regList.contains("SMR")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingCategoryDb("SMR")
                        .build();
                foClientMapper.inserConsultingCategory(tfDTO);
            }
        }
        // 전문가 선정
        FoClientDTO expertNeedDetail = foClientDTO.getExpertNeedDetail();
        List<String> regList1 = expertNeedDetail.getExpertNeed();
        if (regList1 != null) {
            if (regList1.contains("ETC")) {
                FoClientDTO etcDTO = expertNeedDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setExpertNeedDb("ETC");
                foClientMapper.inserExpertNeed(etcDTO);
            }
            if (regList1.contains("INCUM")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("INCUM")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
            if (regList1.contains("EXE")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("EXE")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
            if (regList1.contains("RETIRE3")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .expertNeedDb("RETIRE3")
                        .build();
                foClientMapper.inserExpertNeed(tfDTO);
            }
        }
        // 컨설팅 방식
        FoClientDTO consultingMethodDetail = foClientDTO.getConsultingMethodDetail();
        List<String> regList2 = consultingMethodDetail.getConsultingMethod();
        if (regList2 != null) {
            if (regList2.contains("ETC")) {
                FoClientDTO etcDTO = consultingMethodDetail.getEtc();
                etcDTO.setClientSeq(foClientDTO.getClientSeq());
                etcDTO.setConsultingMethodDb("ETC");
                foClientMapper.inserConsultingMethod(etcDTO);
            }
            if (regList2.contains("CALL")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("CALL")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
            if (regList2.contains("WRITE")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("WRITE")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
            if (regList2.contains("OFF")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .consultingMethodDb("OFF")
                        .build();
                foClientMapper.inserConsultingMethod(tfDTO);
            }
        }
        // 기타 요청사항
        FoClientDTO etcRequsetDetail = foClientDTO.getEtcRequestDetail();
        List<String> regList3 = etcRequsetDetail.getEtcRequest();
        if (regList3 != null) {
            if (regList3.contains("EXPERTS")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .etcRequestDb("EXPERTS")
                        .build();
                foClientMapper.inserEtcRequest(tfDTO);
            }
            if (regList3.contains("INTER")) {
                FoClientDTO tfDTO = FoClientDTO.builder()
                        .clientSeq(foClientDTO.getClientSeq())
                        .etcRequestDb("INTER")
                        .build();
                foClientMapper.inserEtcRequest(tfDTO);
            }
        }
        return foClientDTO.getClientSeq();
    }
}
