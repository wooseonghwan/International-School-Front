package com.fw.core.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class KiccUtil {
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
}
