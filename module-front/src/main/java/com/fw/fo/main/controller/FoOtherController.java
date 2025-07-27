package com.fw.fo.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoOtherController {
    @GetMapping({ "/fo/menu" })
    public String menu(ModelMap modelMap) {
        return "fo/menu";
    }
    @GetMapping({ "/fo/menu-en" })
    public String menuEn(ModelMap modelMap) {
        return "fo/menu-en";
    }
    @GetMapping({ "/fo/notice-board" })
    public String noticeBoard(ModelMap modelMap) {
        return "fo/notice-board";
    }
    @GetMapping({ "/fo/notice-board-en" })
    public String noticeBoardEn(ModelMap modelMap) {
        return "fo/notice-board-en";
    }
    @GetMapping({ "/fo/notice-announcement" })
    public String notice(ModelMap modelMap) {
        return "fo/notice-announcement";
    }
    @GetMapping({ "/fo/notice-announcement-en" })
    public String noticeEn(ModelMap modelMap) {
        return "fo/notice-announcement-en";
    }
    @GetMapping({ "/fo/myinfo" })
    public String myinfo(ModelMap modelMap) {
        return "fo/myinfo";
    }
    @GetMapping({ "/fo/myinfo-en" })
    public String myinfoEn(ModelMap modelMap) {
        return "fo/myinfo-en";
    }
}
