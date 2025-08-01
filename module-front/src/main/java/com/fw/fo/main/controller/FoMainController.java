package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import com.fw.core.dto.fo.FoUserDTO;
import com.fw.core.util.AesUtil;
import com.fw.core.vo.ResponseVO;
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
	public String main(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO, HttpSession httpSession) {
		FoUserDTO loginUser = (FoUserDTO) httpSession.getAttribute("loginUser");
		model.addAttribute("user", loginUser);
		model.addAttribute("noticeList", foOtherService.selectNoticeListMain(foNoticeDTO));
		model.addAttribute("noticeBoardList", foOtherService.selectNoticeBoardListMain(foNoticeBoardDTO));
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1" })
	public String main1(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO, HttpSession httpSession) {
		FoUserDTO loginUser = (FoUserDTO) httpSession.getAttribute("loginUser");
		model.addAttribute("user", loginUser);
		model.addAttribute("noticeList", foOtherService.selectNoticeListMain(foNoticeDTO));
		model.addAttribute("noticeBoardList", foOtherService.selectNoticeBoardListMain(foNoticeBoardDTO));
		return "fo/main1";
	}

	@GetMapping({ "/fo/main1-en" })
	public String main1En(ModelMap model, FoNoticeDTO foNoticeDTO, FoNoticeBoardDTO foNoticeBoardDTO, HttpSession httpSession) {
		FoUserDTO loginUser = (FoUserDTO) httpSession.getAttribute("loginUser");
		model.addAttribute("user", loginUser);
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
	@ResponseBody
	public ResponseEntity<Map<String, Object>> checkCardId(@RequestBody Map<String, String> body) {
		String cardId = body.get("cardId");
		Map<String, Object> result = new HashMap<>();

		FoUserDTO user = foMainService.getUserByCardId(cardId);
		if (user != null) {
			result.put("duplicate", true);
			result.put("webId", user.getWebId()); // null일 수도 있음
		} else {
			result.put("duplicate", false);
		}

		return ResponseEntity.ok(result);
	}
	@PostMapping("/fo/card-info")
	@ResponseBody
	public Map<String, Object> getCardInfo(@RequestBody FoUserDTO dto) {
		FoUserDTO info = foMainService.getCardInfo(dto.getCardId());
		Map<String, Object> result = new HashMap<>();
		if (info != null) {
			result.put("custName", info.getCustName());
			result.put("custNo", info.getCustNo());
			result.put("webId", info.getWebId());
		}
		return result;
	}
	@PostMapping("/fo/check-user-id")
	public ResponseEntity<Boolean> checkUserId(@RequestBody Map<String, String> body) {
		String webId = body.get("webId");
		boolean exists = foMainService.checkDuplicateWebId(webId);
		return ResponseEntity.ok(exists);
	}
	@PostMapping("/fo/insert-user")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateUser(@RequestBody FoUserDTO foUserDTO) {
		try {
			// 비밀번호가 존재하면 암호화 후 설정
			if (foUserDTO.getWebPw() != null && !foUserDTO.getWebPw().trim().isEmpty()) {
				String encryptedPw = AesUtil.encrypt(foUserDTO.getWebPw());
				foUserDTO.setWebPw(encryptedPw);
			}
			boolean result = foMainService.updateUserByCustNo(foUserDTO);
			return ResponseEntity.ok(ResponseVO.builder(result ? ResponseCode.SUCCESS : ResponseCode.LOGIN_FAIL).build());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ResponseVO.builder(ResponseCode.LOGIN_FAIL).message("비밀번호 암호화 실패").build());
		}
	}
	@PostMapping("/fo/login-check")
	@ResponseBody
	public Map<String, Object> loginCheck(@RequestBody FoUserDTO foUserDTO, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		FoUserDTO userInDb = foMainService.findUserByWebId(foUserDTO.getWebId());

		if (userInDb != null) {
			String inputPw = foUserDTO.getWebPw();
			String encryptedPwInDb = userInDb.getWebPw();

			// 1. 입력값이 암호화된 상태일 경우 (그대로 비교)
			if (inputPw.equals(encryptedPwInDb)) {
				session.setAttribute("loginUser", userInDb);
				result.put("result", true);
				return result;
			}
			// 2. 입력값이 평문인 경우 (복호화 후 비교)
			try {
				String decryptedPw = AesUtil.decrypt(encryptedPwInDb);
				if (decryptedPw.equals(inputPw)) {
					session.setAttribute("loginUser", userInDb);
					result.put("result", true);
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("result", false);
				result.put("message", "비밀번호 복호화 실패");
				return result;
			}
		}

		result.put("result", false);
		result.put("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
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
	// 아이디 찾기
	@PostMapping("/fo/find-web-id")
	@ResponseBody
	public Map<String, Object> findWebId(@RequestBody FoUserDTO dto) {
		FoUserDTO user = foMainService.findWebIdByNameAndCardId(dto);
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("webId", user.getWebId());
		}
		return result;
	}
	// 비밀번호 찾기
	@PostMapping("/fo/find-password")
	@ResponseBody
	public Map<String, Object> findPassword(@RequestBody FoUserDTO dto) {
		Map<String, Object> result = new HashMap<>();
		FoUserDTO user = foMainService.findPassword(dto);

		if (user != null && user.getWebPw() != null) {
			try {
				String decryptedPw = AesUtil.decrypt(user.getWebPw());
				result.put("webPw", decryptedPw);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("error", "비밀번호 복호화 실패");
			}
		} else {
			result.put("error", "일치하는 회원이 없습니다.");
		}
		return result;
	}
	@GetMapping({ "/fo/terms" })
	public String terms() {
		return "fo/terms";
	}
	@GetMapping({ "/fo/terms-en" })
	public String termsEn() {
		return "fo/terms-en";
	}
}
