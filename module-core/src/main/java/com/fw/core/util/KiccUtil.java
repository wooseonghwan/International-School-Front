package com.fw.core.util;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class KiccUtil {
    private static final String HMAC_ALGO = "HmacSHA256";

    // 결제고객 단말구분
    public static String getDeviceType(HttpServletRequest request) {
        String ua = Optional.ofNullable(request.getHeader("User-Agent")).orElse("").toLowerCase();

        if (ua.contains("mobi") || ua.contains("android") || ua.contains("iphone") || ua.contains("ipad") ||
                ua.contains("mobile") || ua.contains("phone") || ua.contains("touch") || ua.contains("webview") ||
                ua.contains("windows phone") || ua.contains("blackberry")) {
            return "mobile";
        }

        return "pc";
    }

    // 데이터 무결성 검증 암호화
    public static String generateHmac(String message, String secretKey) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] rawHmac = sha256Hmac.doFinal(message.getBytes());
            return bytesToHex(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("HMAC 생성 중 오류 발생", e);
        }
    }

    // HexString 변환
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 랜덤 가맹점 트랜잭션 ID 생성
    public static String generateShopTransactionId() {
        // 1. 오늘 날짜 (yyyyMMdd)
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 2. 현재 시간의 밀리초 (HHmmssSSS)
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS"));

        // 3. 랜덤값 (세 자리 숫자)
        int random = ThreadLocalRandom.current().nextInt(100, 1000); // 100 ~ 999

        // 최종 조합
        return String.format("%s-%s%d", date, time, random);
    }
}
