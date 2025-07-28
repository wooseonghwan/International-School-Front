package com.fw.fo.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoPaymentController {
    @RequestMapping({ "/fo/lunch-payment" })
    public String lunchPayment(ModelMap modelMap) {
        return "fo/lunch-payment";
    }
    @RequestMapping({ "/fo/lunch-payment-en" })
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

    @PostMapping("/easypay/request")
    public ResponseEntity<?> requestPayment(HttpServletRequest request) {
        String url = "https://testpgapi.easypay.co.kr/api/ep9/trades/webpay";

        Map<String, Object> payload = new HashMap<>();
        payload.put("mallId", "T0021430");
        payload.put("shopOrderNo", "TEST_ORDER_" + System.currentTimeMillis());
        payload.put("amount", 500);
        payload.put("payMethodTypeCode", "11");
        payload.put("currency", "00");
        payload.put("returnUrl", "https://cezars.devsp.kr/fo/lunch-payment");
        payload.put("deviceTypeCode", "pc");
        payload.put("clientTypeCode", "00");
        payload.put("langFlag", "KOR");

        Map<String, Object> customerInfo = new HashMap<>();
        customerInfo.put("customerId", "testuser");
        customerInfo.put("customerName", "홍길동");
        customerInfo.put("customerMail", "test@example.com");
        customerInfo.put("customerContactNo", "01012345678");
        customerInfo.put("customerAddr", "서울특별시 강남구 테헤란로 123");

        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("goodsName", "급식카드 충전 테스트상품");
        orderInfo.put("customerInfo", customerInfo);

        List<String> installmentMonthList = new ArrayList<>();
        installmentMonthList.add("00");

        List<String> displayArea = new ArrayList<>();
        displayArea.add("CARD");

        Map<String, Object> cardMethodInfo = new HashMap<>();
        cardMethodInfo.put("paymentType", "");
        cardMethodInfo.put("installmentMonthList", installmentMonthList);
        cardMethodInfo.put("setFreeInstallment", "N");
        cardMethodInfo.put("setCardPoint", "N");
        cardMethodInfo.put("displayArea", displayArea);

        Map<String, Object> payMethodInfo = new HashMap<>();
        payMethodInfo.put("cashReceiptUsed", "X");
        payMethodInfo.put("cardMethodInfo", cardMethodInfo);

        Map<String, Object> shopValueInfo = new HashMap<>();
        shopValueInfo.put("value1", "테스트 메모1");
        shopValueInfo.put("value2", "테스트 메모2");

        payload.put("orderInfo", orderInfo);
        payload.put("payMethodInfo", payMethodInfo);
        payload.put("shopValueInfo", shopValueInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("결제 요청 실패");
        }

        String authPageUrl = (String) response.getBody().get("authPageUrl");

        Map<String, String> result = new HashMap<>();
        result.put("authPageUrl", authPageUrl); // 반드시 key 이름이 'authPageUrl' 이어야 함

        System.out.println("Easypay 응답 전체: " + response.getBody());

        return ResponseEntity.ok(result);
    }

}
