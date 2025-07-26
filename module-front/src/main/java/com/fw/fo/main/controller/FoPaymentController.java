package com.fw.fo.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoPaymentController {
    @GetMapping({ "/fo/lunch-payment" })
    public String lunchPayment(ModelMap modelMap) {
        return "fo/lunch-payment";
    }
    @GetMapping({ "/fo/lunch-payment-en" })
    public String lunchPaymentEn(ModelMap modelMap) {
        return "fo/lunch-payment-en";
    }
    @GetMapping({ "/fo/lunch-payment-history" })
    public String lunchPaymentHist(ModelMap modelMap) {
        return "fo/lunch-payment-history";
    }
    @GetMapping({ "/fo/lunch-payment-history-en" })
    public String lunchPaymentHistEn(ModelMap modelMap) {
        return "fo/lunch-payment-history-en";
    }
    @GetMapping({ "/fo/use-history" })
    public String useHist(ModelMap modelMap) {
        return "fo/use-history";
    }
    @GetMapping({ "/fo/use-history-en" })
    public String useHistEn(ModelMap modelMap) {
        return "fo/use-history-en";
    }
}
