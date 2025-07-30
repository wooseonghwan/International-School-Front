package com.fw.fo.main.controller;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import com.fw.fo.main.service.FoMainService;
import com.fw.fo.main.service.FoOtherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoMainController {

	private final FoMainService foMainService;
	private final FoOtherService foOtherService;
	/**
	 * Front 메인 페이지
	 */
	@GetMapping({ "/" })	
	public String main(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO) {
		model.addAttribute("noticeList", foOtherService.selectNoticeListMain(foNoticeDTO));
		model.addAttribute("noticeBoardList", foOtherService.selectNoticeBoardListMain(foNoticeBoardDTO));
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1" })
	public String main1(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO) {
		model.addAttribute("noticeList", foOtherService.selectNoticeListMain(foNoticeDTO));
		model.addAttribute("noticeBoardList", foOtherService.selectNoticeBoardListMain(foNoticeBoardDTO));
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1-en" })
	public String main1En(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO) {
		model.addAttribute("noticeListEn", foOtherService.selectNoticeListEnMain(foNoticeDTO));
		model.addAttribute("noticeBoardListEn", foOtherService.selectNoticeBoardListEnMain(foNoticeBoardDTO));
		return "fo/main1-en";
	}
	@GetMapping({ "/fo/login" })
	public String login() {
		return "fo/login";
	}
	@GetMapping({ "/fo/login-en" })
	public String loginEn() {
		return "fo/login-en";
	}
	@GetMapping({ "/fo/signup" })
	public String signup() {
		return "fo/signup";
	}
	@GetMapping({ "/fo/signup-en" })
	public String signupEn() {
		return "fo/signup-en";
	}
}
