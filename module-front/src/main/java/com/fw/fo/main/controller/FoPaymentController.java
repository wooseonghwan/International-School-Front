package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.core.dto.fo.FoUserDTO;
import com.fw.core.dto.kicc.KiccTransactionCallbackDTO;
import com.fw.core.dto.kicc.KiccTransactionDTO;
import com.fw.core.dto.kicc.KiccTransactionResDTO;
import com.fw.core.util.KiccUtil;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoPaymentController {

    private final FoPaymentService foPaymentService;

    @RequestMapping({ "/fo/lunch-payment" })
    public String lunchPayment(ModelMap modelMap, @RequestParam(value = "result", required = false) String result, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/fo/login";
        }
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        modelMap.addAttribute("loginUser", loginUser);
        modelMap.addAttribute("result", result);
        return "fo/lunch-payment";
    }

    @RequestMapping({ "/fo/lunch-payment-en" })
    public String lunchPaymentEn(ModelMap modelMap, @RequestParam(value = "result", required = false) String result, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/fo/login-en";
        }
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        modelMap.addAttribute("loginUser", loginUser);
        modelMap.addAttribute("result", result);
        return "fo/lunch-payment-en";
    }

    @GetMapping({ "/fo/lunch-payment-history" })
    public String lunchPaymentHist(ModelMap model, HttpSession session, FoPaymentDTO foPaymentDTO) {
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/fo/login";
        }
        foPaymentDTO.setCustNo(loginUser.getCustNo());
        int total = foPaymentService.selectPaymentChargeListCnt(foPaymentDTO);
        foPaymentDTO.setTotalCount(total);
        foPaymentDTO.calculatePaging();
        model.addAttribute("paymentChargeList", foPaymentService.selectPaymentChargeList(foPaymentDTO));
        model.addAttribute("totalCount", foPaymentService.selectPaymentChargeListCnt(foPaymentDTO));
        model.addAttribute("searchInfo", foPaymentDTO);
        return "fo/lunch-payment-history";
    }

    @GetMapping({ "/fo/lunch-payment-history-en" })
    public String lunchPaymentHistEn(ModelMap model, HttpSession session, FoPaymentDTO foPaymentDTO) {
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/fo/login-en";
        }
        foPaymentDTO.setCustNo(loginUser.getCustNo());
        int total = foPaymentService.selectPaymentChargeListCnt(foPaymentDTO);
        foPaymentDTO.setTotalCount(total);
        foPaymentDTO.calculatePaging();
        model.addAttribute("paymentChargeList", foPaymentService.selectPaymentChargeList(foPaymentDTO));
        model.addAttribute("totalCount", foPaymentService.selectPaymentChargeListCnt(foPaymentDTO));
        model.addAttribute("searchInfo", foPaymentDTO);
        return "fo/lunch-payment-history-en";
    }

    @GetMapping({ "/fo/use-history" })
    public String useHist(ModelMap model, HttpSession session, FoPaymentDTO foPaymentDTO) {
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/fo/login";
        }
        foPaymentDTO.setCustNo(loginUser.getCustNo());
        int total = foPaymentService.selectPaymentUseListCnt(foPaymentDTO);
        foPaymentDTO.setTotalCount(total);
        foPaymentDTO.calculatePaging();
        model.addAttribute("paymentUseList", foPaymentService.selectPaymentUseList(foPaymentDTO));
        model.addAttribute("totalCount", foPaymentService.selectPaymentUseListCnt(foPaymentDTO));
        model.addAttribute("searchInfo", foPaymentDTO);
        return "fo/use-history";
    }

    @GetMapping({ "/fo/use-history-en" })
    public String useHistEn(ModelMap model, HttpSession session, FoPaymentDTO foPaymentDTO) {
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/fo/login-en";
        }
        foPaymentDTO.setCustNo(loginUser.getCustNo());
        int total = foPaymentService.selectPaymentUseListCnt(foPaymentDTO);
        foPaymentDTO.setTotalCount(total);
        foPaymentDTO.calculatePaging();
        model.addAttribute("paymentUseList", foPaymentService.selectPaymentUseList(foPaymentDTO));
        model.addAttribute("totalCount", foPaymentService.selectPaymentUseListCnt(foPaymentDTO));
        model.addAttribute("searchInfo", foPaymentDTO);
        return "fo/use-history-en";
    }

    // KICC 거래등록
    @PostMapping("/payment/kicc/transaction")
    @ResponseBody
    public ResponseEntity<ResponseVO> paymentKiccTransaction(@RequestBody KiccTransactionDTO kiccTransactionDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            // 결제 가능한 금액 리스트
            List<Integer> validAmounts = Arrays.asList(10000, 20000, 30000);
            // 결제 금액 유효성 검증 실패 시
            if (!validAmounts.contains(kiccTransactionDTO.getAmount())) {
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INVALID_AMOUNT").build());
            }
            // 결제고객 단말구분
            kiccTransactionDTO.setDeviceTypeCode(KiccUtil.getDeviceType(request));
            // 거래등록
            KiccTransactionResDTO response = foPaymentService.kiccTransaction(kiccTransactionDTO);
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(response).message("SUCCESS").build());
        } catch (Exception e) {
            log.error("KICC 거래등록 중 오류발생", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).message("INTERNAL_SERVER_ERROR").build());
        }
    }

    // KICC 결제 콜백
    @RequestMapping("/payment/kicc/transaction/callback")
    public String paymentKiccTransactionCallback(KiccTransactionCallbackDTO kiccTransactionCallbackDTO) {
        String result = "";
        String redirectUrl = "KOR".equals(kiccTransactionCallbackDTO.getShopValue2()) ? "/fo/lunch-payment" : "/fo/lunch-payment-en";
        try {
            // 거래승인
            result = foPaymentService.kiccTransactionApproval(kiccTransactionCallbackDTO);
        } catch (Exception e) {
            log.error("KICC 거래 승인 중 오류발생", e);
            result = "FAIL";
        }
        return String.format("redirect:%s?result=%s", redirectUrl, result);
    }


}
