package com.fw.fo.main.controller;

import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import com.fw.core.dto.fo.FoUserDTO;
import com.fw.fo.main.service.FoMainService;
import com.fw.fo.main.service.FoOtherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
	@PostMapping("/fo/check-card-id")
	public ResponseEntity<Boolean> checkCardId(@RequestBody Map<String, String> body) {
		String cardId = body.get("cardId");
		boolean exists = foMainService.checkDuplicateCardId(cardId);
		return ResponseEntity.ok(exists);
	}
	@PostMapping("/fo/check-user-id")
	public ResponseEntity<Boolean> checkUserId(@RequestBody Map<String, String> body) {
		String webId = body.get("webId");
		boolean exists = foMainService.checkDuplicateWebId(webId);
		return ResponseEntity.ok(exists);
	}
	@PostMapping("/fo/insert-user")
	public ResponseEntity<String> insertUser(@RequestBody FoUserDTO userDTO) {
		boolean result = foMainService.registerUser(userDTO);
		return result ? ResponseEntity.ok("success") : ResponseEntity.status(500).body("등록 실패");
	}
	@PostMapping("/fo/login-check")
	@ResponseBody
	public Map<String, Object> loginCheck(@RequestBody FoUserDTO foUserDTO, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		FoUserDTO loginUser = foMainService.login(foUserDTO);
		// 세션에 저장
		if (loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			result.put("result", true);
		} else {
			result.put("result", false);
			result.put("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		return result;
	}
	@GetMapping("/fo/logout")
	public String logout(HttpSession session) {
		// 세션 전체 무효화
		session.invalidate();
		// 로그인 페이지로 리다이렉트
		return "redirect:/fo/login";
	}
	@GetMapping({ "/fo/signup-en" })
	public String signupEn() {
		return "fo/signup-en";
	}
}
