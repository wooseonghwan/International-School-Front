package com.fw.fo.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoExpertDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoExpertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoExpertController {
    private final FoExpertService foExpertService;
    private final ObjectMapper objectMapper;

   /* @GetMapping("/fo/expert-add")
    public String expertAdd(ModelMap model, FoExpertDTO foExpertDTO, HttpServletRequest request) {
        return "/fo/expert-add";
    }
    @GetMapping("/fo/expert-add-en")
    public String expertAddEn(ModelMap model, FoExpertDTO foExpertDTO, HttpServletRequest request) {
        return "/fo/expert-add-en";
    }
    @GetMapping("/fo/expert/check-id")
    @ResponseBody
    public ResponseEntity<ResponseVO> checkId(String id) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            int count = foExpertService.checkId(id);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(count).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }

    @PostMapping("/fo/expert/insert-expert")
    @ResponseBody
    public ResponseEntity<ResponseVO> inserExpert(
            @RequestPart("foExpertDTO") FoExpertDTO foExpertDTO,
            @RequestPart(value = "expertFile", required = false) MultipartFile[] expertFile,
            @RequestPart(value = "expertSub", required = false) MultipartFile[] expertSub) {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            if (StringUtils.isNotBlank(foExpertDTO.getExpertListStr())) {
                List<FoExpertDTO> expertList = Arrays.asList(objectMapper.readValue(foExpertDTO.getExpertListStr(), FoExpertDTO[].class));
                foExpertDTO.setExpertList(expertList);
            }

            if (StringUtils.isNotBlank(foExpertDTO.getAdvisoryDetailStr())) {
                FoExpertDTO advisoryDetail = objectMapper.readValue(foExpertDTO.getAdvisoryDetailStr(), FoExpertDTO.class);
                foExpertDTO.setAdvisoryDetail(advisoryDetail);
            }

            foExpertDTO.setExpertfile(expertFile);
            foExpertDTO.setExpertsub(expertSub);
            String expertSeq = foExpertService.inserExpert(foExpertDTO);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(expertSeq).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }


    @PostMapping("/fo/expert/insert-expert-temp")
    @ResponseBody
    public ResponseEntity<ResponseVO> inserExpertTemp(FoExpertDTO foExpertDTO, HttpServletRequest request, HttpServletResponse httpServletResponse,
                                                  @RequestPart(value = "Expertfile", required = false) MultipartFile[] Expertfile,
                                                  @RequestPart(value = "ExpertSub", required = false) MultipartFile[] ExpertSub) throws HttpSessionRequiredException {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foExpertDTO.setExpertfile(Expertfile);
            foExpertDTO.setExpertsub(ExpertSub);
            String expertSeq = foExpertService.inserExpertTemp(foExpertDTO);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(expertSeq).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }*/
}
