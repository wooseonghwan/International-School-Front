package com.fw.fo.main.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoExpertDTO;
import com.fw.core.mapper.db1.fo.FoExpertMapper;
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
public class FoExpertService {
    private final FoExpertMapper foExpertMapper;
    private final CommonFileService commonFileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int checkId(String id) {
        return foExpertMapper.selectIdCheck(id);
    }

    public String inserExpert(FoExpertDTO foExpertDTO) throws IOException {

        Integer fileSeq = commonFileService.fileUpload(foExpertDTO.getExpertfile());
        if (fileSeq != null) {
            foExpertDTO.setFileSeq(String.valueOf(fileSeq)); // fileSeq1로 설정
        }

        Integer subSeq = commonFileService.fileUpload(foExpertDTO.getExpertsub());
        if (subSeq != null) {
            foExpertDTO.setSubSeq(String.valueOf(subSeq)); // subSeq1로 설정
        }
        foExpertDTO.setPassword(bCryptPasswordEncoder.encode(foExpertDTO.getPassword()));
        foExpertMapper.inserExpert(foExpertDTO);

        log.info("Idx :: {}", foExpertDTO.getExpertSeq());
        // 경력 등록
        if (CollectionUtils.isEmpty(foExpertDTO.getExpertList())) {
            foExpertMapper.inserExpertCareer(foExpertDTO);
        } else {
            for (FoExpertDTO expert : foExpertDTO.getExpertList()) {
                expert.setExpertSeq(foExpertDTO.getExpertSeq());
                foExpertMapper.inserExpertCareer(expert);
            }
        }
        // 자문 선호 시간대 등록
        FoExpertDTO advisoryDetail = foExpertDTO.getAdvisoryDetail();
        List<String> regList = advisoryDetail.getAdvisoryNeedTime();
        if (regList != null) {
            if (regList.contains("WEEKDAY")) {
                FoExpertDTO weekDayDTO = advisoryDetail.getWeekday();
                weekDayDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                weekDayDTO.setAdvisoryNeedTimeDb("WEEKDAY");
                foExpertMapper.inserAdvisory(weekDayDTO);
            }

            if (regList.contains("WEEKEND")) {
                FoExpertDTO weekendDTO = advisoryDetail.getWeekend();
                weekendDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                weekendDTO.setAdvisoryNeedTimeDb("WEEKEND");
                foExpertMapper.inserAdvisory(weekendDTO);
            }

            if (regList.contains("TF")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .advisoryNeedTimeDb("TF")
                        .build();
                foExpertMapper.inserAdvisory(tfDTO);
            }
        }
        // 상세 직무
        FoExpertDTO jobCategoryDetail1 = foExpertDTO.getJobCategoryDetails1();
        List<String> regList1 = jobCategoryDetail1.getJobCategoryDetail();
        if (regList1 != null) {
            if (regList1.contains("ETC")) {
                FoExpertDTO etcDTO = jobCategoryDetail1.getEtc();
                etcDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                etcDTO.setJobCategoryDetailDb("ETC");
                foExpertMapper.insertJobCategoryDetail(etcDTO);
            }
            if (regList1.contains("AI")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("AI")
                        .build();
                foExpertMapper.insertJobCategoryDetail(tfDTO);
            }
            if (regList1.contains("EN")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("EN")
                        .build();
                foExpertMapper.insertJobCategoryDetail(tfDTO);
            }
            if (regList1.contains("AD")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("AD")
                        .build();
                foExpertMapper.insertJobCategoryDetail(tfDTO);
            }
            if (regList1.contains("IDC")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("IDC")
                        .build();
                foExpertMapper.insertJobCategoryDetail(tfDTO);
            }
            if (regList1.contains("BB")) {
                FoExpertDTO tfDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("BB")
                        .build();
                foExpertMapper.insertJobCategoryDetail(tfDTO);
            }
        }
        return foExpertDTO.getExpertSeq();
    }

    public String inserExpertTemp(FoExpertDTO foExpertDTO) throws IOException {
        foExpertMapper.inserExpertTemp(foExpertDTO);

        log.info("Idx :: {}", foExpertDTO.getExpertSeq());
        // 경력 등록
        if (CollectionUtils.isEmpty(foExpertDTO.getExpertList())) {
            foExpertMapper.inserExpertCareer(foExpertDTO);
        } else {
            for (FoExpertDTO expert : foExpertDTO.getExpertList()) {
                expert.setExpertSeq(foExpertDTO.getExpertSeq());
                foExpertMapper.inserExpertCareer(expert);
            }
        }
        // 자문 선호 시간대 등록
        FoExpertDTO advisoryDetail = foExpertDTO.getAdvisoryDetail();

// advisoryDetail이 null이 아닌지 확인
        if (advisoryDetail != null) {
            List<String> regList = advisoryDetail.getAdvisoryNeedTime();

            if (regList != null) {
                if (regList.contains("WEEKDAY")) {
                    FoExpertDTO weekDayDTO = advisoryDetail.getWeekday();
                    weekDayDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                    weekDayDTO.setAdvisoryNeedTimeDb("WEEKDAY");
                    foExpertMapper.inserAdvisory(weekDayDTO);
                }

                if (regList.contains("WEEKEND")) {
                    FoExpertDTO weekendDTO = advisoryDetail.getWeekend();
                    weekendDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                    weekendDTO.setAdvisoryNeedTimeDb("WEEKEND");
                    foExpertMapper.inserAdvisory(weekendDTO);
                }

                if (regList.contains("TF")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .advisoryNeedTimeDb("TF")
                            .build();
                    foExpertMapper.inserAdvisory(tfDTO);
                }
            } else {
                // regList가 null일 때 NULL 값 입력
                FoExpertDTO nullAdvisoryDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .advisoryNeedTimeDb("NULL") // 또는 필요한 다른 처리
                        .build();
                foExpertMapper.inserAdvisory(nullAdvisoryDTO);
            }
        } else {
            // advisoryDetail이 null일 때 처리 (필요시)
            FoExpertDTO nullAdvisoryDTO = FoExpertDTO.builder()
                    .expertSeq(foExpertDTO.getExpertSeq())
                    .advisoryNeedTimeDb("NULL") // 또는 필요한 다른 처리
                    .build();
            foExpertMapper.inserAdvisory(nullAdvisoryDTO);
        }

        FoExpertDTO jobCategoryDetail1 = foExpertDTO.getJobCategoryDetails1();

// jobCategoryDetail1이 null인지 확인
        if (jobCategoryDetail1 != null) {
            List<String> regList1 = jobCategoryDetail1.getJobCategoryDetail();

            if (regList1 != null) {
                if (regList1.contains("ETC")) {
                    FoExpertDTO etcDTO = jobCategoryDetail1.getEtc();
                    etcDTO.setExpertSeq(foExpertDTO.getExpertSeq());
                    etcDTO.setJobCategoryDetailDb("ETC");
                    foExpertMapper.insertJobCategoryDetail(etcDTO);
                }
                if (regList1.contains("AI")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .jobCategoryDetailDb("AI")
                            .build();
                    foExpertMapper.insertJobCategoryDetail(tfDTO);
                }
                if (regList1.contains("EN")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .jobCategoryDetailDb("EN")
                            .build();
                    foExpertMapper.insertJobCategoryDetail(tfDTO);
                }
                if (regList1.contains("AD")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .jobCategoryDetailDb("AD")
                            .build();
                    foExpertMapper.insertJobCategoryDetail(tfDTO);
                }
                if (regList1.contains("IDC")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .jobCategoryDetailDb("IDC")
                            .build();
                    foExpertMapper.insertJobCategoryDetail(tfDTO);
                }
                if (regList1.contains("BB")) {
                    FoExpertDTO tfDTO = FoExpertDTO.builder()
                            .expertSeq(foExpertDTO.getExpertSeq())
                            .jobCategoryDetailDb("BB")
                            .build();
                    foExpertMapper.insertJobCategoryDetail(tfDTO);
                }
            } else {
                // regList1가 null일 때 NULL 값 입력
                FoExpertDTO nullJobCategoryDTO = FoExpertDTO.builder()
                        .expertSeq(foExpertDTO.getExpertSeq())
                        .jobCategoryDetailDb("NULL") // 또는 필요한 다른 처리
                        .build();
                foExpertMapper.insertJobCategoryDetail(nullJobCategoryDTO);
            }
        } else {
            // jobCategoryDetail1이 null일 때 처리 (필요시)
            FoExpertDTO nullJobCategoryDTO = FoExpertDTO.builder()
                    .expertSeq(foExpertDTO.getExpertSeq())
                    .jobCategoryDetailDb("NULL") // 또는 필요한 다른 처리
                    .build();
            foExpertMapper.insertJobCategoryDetail(nullJobCategoryDTO);
        }
        return foExpertDTO.getExpertSeq();
    }
}