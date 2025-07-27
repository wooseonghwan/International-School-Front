package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoGolfPriceDTO;
import com.fw.core.dto.fo.FoReservationDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Main Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMainController {

	private final FoMainService foMainService;
	/**
	 * Front 메인 페이지
	 */
	@GetMapping({ "/" })	
	public String main() {
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1" })
	public String main1(ModelMap modelMap) {
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1-en" })
	public String main1En(ModelMap modelMap) {
		return "fo/main1-en";
	}
}
