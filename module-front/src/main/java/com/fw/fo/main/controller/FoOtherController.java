package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoNoticeBoardDTO;
import com.fw.core.dto.fo.FoNoticeDTO;
import com.fw.core.dto.fo.FoUserDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoOtherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoOtherController {

    private final FoOtherService foOtherService;

    @GetMapping({ "/fo/menu" })
    public String menu(ModelMap modelMap) {
        return "fo/menu";
    }
    @GetMapping({ "/fo/menu-en" })
    public String menuEn(ModelMap modelMap) {
        return "fo/menu-en";
    }
    @GetMapping({ "/fo/notice-board" })
    public String noticeBoard(ModelMap model, FoNoticeBoardDTO foNoticeBoardDTO, HttpSession httpSession) {
        if (httpSession.getAttribute("loginUser") == null) {
            return "redirect:/fo/login";
        }
        int total = foOtherService.selectNoticeBoardListCnt(foNoticeBoardDTO);
        foNoticeBoardDTO.setTotalCount(total);
        foNoticeBoardDTO.calculatePaging();
        model.addAttribute("noticeBoardList", foOtherService.selectNoticeBoardList(foNoticeBoardDTO));
        model.addAttribute("totalCount", foOtherService.selectNoticeBoardListCnt(foNoticeBoardDTO));
        model.addAttribute("searchInfo", foNoticeBoardDTO);
        return "fo/notice-board";
    }
    @PostMapping("/fo/notice-board-check-password")
    @ResponseBody
    public boolean checkPassword(@RequestBody FoNoticeBoardDTO foNoticeBoardDTO) {
        return foOtherService.checkPassword(foNoticeBoardDTO);
    }
    @GetMapping({ "/fo/notice-board-en" })
    public String noticeBoardEn(ModelMap model, FoNoticeBoardDTO foNoticeBoardDTO, HttpSession httpSession) {
        if (httpSession.getAttribute("loginUser") == null) {
            return "redirect:/fo/login";
        }
        int total = foOtherService.selectNoticeBoardListEnCnt(foNoticeBoardDTO);
        foNoticeBoardDTO.setTotalCount(total);
        foNoticeBoardDTO.calculatePaging();
        model.addAttribute("noticeBoardListEn", foOtherService.selectNoticeBoardListEn(foNoticeBoardDTO));
        model.addAttribute("totalCount", foOtherService.selectNoticeBoardListEnCnt(foNoticeBoardDTO));
        model.addAttribute("searchInfo", foNoticeBoardDTO);
        return "fo/notice-board-en";
    }
    @GetMapping({ "/fo/notice-board-form" })
    public String noticeBoardForm(ModelMap modelMap, FoNoticeBoardDTO foNoticeBoardDTO) {
        return "fo/notice-board-form";
    }
    @PostMapping("/fo/notice-board-insert")
    @ResponseBody
    ResponseEntity<ResponseVO> insertNoticeBoard(HttpServletRequest request, @RequestBody FoNoticeBoardDTO foNoticeBoardDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        // 세션에서 로그인 유저 정보 꺼내기
        FoUserDTO sessionUser = (FoUserDTO) request.getSession().getAttribute("loginUser");
        if (sessionUser != null && sessionUser.getWebId() != null) {
            foNoticeBoardDTO.setCreateId(sessionUser.getWebId());
        } else {
            code = ResponseCode.PAGE_NOT_FOUND;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseVO.builder(code).message("로그인 세션이 만료되었거나 잘못되었습니다.").build());
        }
        foOtherService.insertNoticeBoard(foNoticeBoardDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
    @GetMapping({ "/fo/notice-board-form-en" })
    public String noticeBoardFormEn(ModelMap modelMap, FoNoticeBoardDTO foNoticeBoardDTO) {
        return "fo/notice-board-form-en";
    }
    @GetMapping({ "/fo/notice-board-detail" })
    public String noticeBoardDetail(ModelMap model, @RequestParam String qnaId) {
        foOtherService.increaseViewCount(qnaId);
        model.addAttribute("detail", foOtherService.selectNoticeBoardDetail(qnaId));
        return "fo/notice-board-detail";
    }
    @GetMapping({ "/fo/notice-board-detail-en" })
    public String noticeBoardDetailEn(ModelMap model, @RequestParam String qnaId) {
        foOtherService.increaseViewCount(qnaId);
        model.addAttribute("detail", foOtherService.selectNoticeBoardDetailEn(qnaId));
        return "fo/notice-board-detail-en";
    }
    @GetMapping({ "/fo/notice-announcement" })
    public String notice(ModelMap model, FoNoticeDTO foNoticeDTO) {
        int total = foOtherService.selectNoticeListCnt(foNoticeDTO);
        foNoticeDTO.setTotalCount(total);
        foNoticeDTO.calculatePaging();
        model.addAttribute("noticeList", foOtherService.selectNoticeList(foNoticeDTO));
        model.addAttribute("searchInfo", foNoticeDTO);
        return "fo/notice-announcement";
    }
    @GetMapping({ "/fo/notice-announcement-en" })
    public String noticeEn(ModelMap model, FoNoticeDTO foNoticeDTO) {
        int total = foOtherService.selectNoticeListCnt(foNoticeDTO);
        foNoticeDTO.setTotalCount(total);
        foNoticeDTO.calculatePaging();
        model.addAttribute("noticeListEn", foOtherService.selectNoticeListEn(foNoticeDTO));
        model.addAttribute("searchInfo", foNoticeDTO);
        return "fo/notice-announcement-en";
    }
    @GetMapping({ "/fo/notice-announcement-detail" })
    public String noticeDetail(ModelMap model, @RequestParam String noticeId) {
        model.addAttribute("detail", foOtherService.selectNoticeDetail(noticeId));
        return "fo/notice-announcement-detail";
    }
    @GetMapping({ "/fo/notice-announcement-detail-en" })
    public String noticeDetailEn(ModelMap model, @RequestParam String noticeId) {
        model.addAttribute("detailEn", foOtherService.selectNoticeDetailEn(noticeId));
        return "fo/notice-announcement-detail-en";
    }
    @GetMapping({ "/fo/myinfo" })
    public String myinfo(HttpSession session, Model model, FoUserDTO foUserDTO) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/fo/login";
        }
        FoUserDTO loginUser = (FoUserDTO) session.getAttribute("loginUser");
        model.addAttribute("user", loginUser);
        return "fo/myinfo";
    }
    @GetMapping({ "/fo/myinfo-en" })
    public String myinfoEn(HttpSession session, Model model, FoUserDTO foUserDTO) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/fo/login";
        }
        return "fo/myinfo-en";
    }
}
