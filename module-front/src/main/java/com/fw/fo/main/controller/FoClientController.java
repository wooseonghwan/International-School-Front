package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoClientDTO;
import com.fw.core.dto.fo.FoExpertDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoClientController {
    private final FoClientService foClientService;

    /*@GetMapping("/fo/client-add")
    public String expertAdd(ModelMap model, FoClientDTO foClientDTO, HttpServletRequest request) {
        return "/fo/client-add";
    }
    @GetMapping("/fo/client-add-en")
    public String clientAddEn(ModelMap model, FoClientDTO foClientDTO, HttpServletRequest request) {
        return "/fo/client-add-en";
    }
    @GetMapping("/fo/client/check-id")
    @ResponseBody
    public ResponseEntity<ResponseVO> checkId(String id) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            int count = foClientService.checkId(id);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(count).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }
    @PostMapping("/fo/client/insert-client")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertClient(@RequestBody FoClientDTO foClientDTO, HttpServletRequest request, HttpServletResponse httpServletResponse)throws HttpSessionRequiredException {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            String clientSeq = foClientService.insertClient(foClientDTO);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(clientSeq).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }

    @PostMapping("/fo/client/insert-client-temp")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertClientTemp(@RequestBody FoClientDTO foClientDTO, HttpServletRequest request, HttpServletResponse httpServletResponse)throws HttpSessionRequiredException {

        ResponseCode code = ResponseCode.SUCCESS;
        try {
            String clientSeq = foClientService.insertClientTemp(foClientDTO);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(clientSeq).build());
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }*/

}
