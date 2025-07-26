"use strict";
let Utils = {
    // 이메일 형식이 맞는지 확인한다.
    emailCheck: function(email) {
        if((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/).test(email)) {
            return true;
        } else {
            return false;
        }
    },
    // 비밀번호 규칙이 맞는지 확인한다.
    passwordCheck: function(password) {
        password = password.trim()
        let num = password.search(/[0-9]/g);                         // 숫자 유효성
        let eng = password.search(/[a-z]/gi);                        // 영문자 유효성
        let spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);   // 특수 문자 유효성

        if(password == 0) {
            return false;
        }

        if(password.length > 30 || password.length < 8 || password.search(/\s/) != -1 || (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ) {
            return false;
        }
        return true;
    },
    // 기존 비밀번호와 변경된 비밀번호가 맞는지 확인한다.
    passwordMatch: function(password, confirmPassword) {
        if(password.trim() != "" && (password == confirmPassword)){
            return true;
        } else {
            return false;
        }
    },
    // 숫자만 받도록 강제로 치환한다.
    onlyNumber: function(selector){
        let replaceStr = selector.val().replaceAll(/[^0-9]/g, "");
        selector.val(replaceStr);
    },
    // 숫자만 받고 최소숫자와 최대숫자를 체크한다.
    numberCheck: function(selector, minLength, maxLength){
        let number =/^[0-9]*$/;
        let returnObj = {
            msg: "입력 가능한 숫자입니다.",
            result: true
        };
        if(!number.test(selector.val())){
            selector.val("");
            returnObj = {
                msg: "숫자만 입력가능합니다.",
                result: false
            };
            return returnObj;
        }
        if(selector.val().length > maxLength || selector.val().length < minLength){
            selector.val("");
            returnObj = {
                msg: minLength + "자 이상, " + maxLength + "자 이하 입력하셔야 합니다.",
                result: false
            };
            return returnObj;
        }
        return returnObj;
    },
    // 모바일인지 웹인지 체크한다.
    checkMobile: function() {
        let userAgent = navigator.userAgent.toLowerCase();
        if(userAgent.indexOf('macintosh') > -1 || userAgent.indexOf('iphone') > -1 || userAgent.indexOf('ipad') > -1 || userAgent.indexOf('ipod') > -1) {
            return "IOS";
        } else {
            return "ANDROID";
        }
    },
    // 핸드폰 '-'로 포멧팅을 변경한다.
    formatPhoneNumber: function(phone) {
        return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    },
    // 파일의 사이즈 및 이미지여부를 체크한다.
    fileCheck: function(selector, size, imageCheck){
        let ext = selector.val().split('.').pop().toLowerCase();
        let returnObj = {
            msg: "등록 가능한 파일입니다.",
            result: true
        };

        if(imageCheck){
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                selector.val("");
                returnObj = {
                    msg: "등록할 수 없는 파일입니다.",
                    result: false
                };
                return returnObj;
            }
        }

        if(size != null){
            let fileSize = selector[0].files[0].size;
            if(fileSize > 10 * 1024 * 1024){
                returnObj = {
                    msg: "첨부파일 사이즈는 " + size +"MB 이내로 등록 가능합니다.",
                    result: false
                };
                return returnObj;
            }
        }

        return returnObj;
    },
    // 날짜 검증 (moment.js 필수)
    dateCheck: function(selector, format){
        let returnObj = {
            msg: "입력 가능한 날짜입니다.",
            result: true
        };

        let date = moment(selector.val(), format, true).format();
        if(date == "Invalid date"){
            selector.val("");
            returnObj = {
                msg: "잘못된 날짜를 기입하셨습니다.\n날짜형식을 (" + format + ")에 맞추어 주세요.",
                result: false,
                selector: selector
            };
        }
        return returnObj;
    },
    // 공통 유효성 검사
    valid: function(selector, opt, name){
        let returnObj = {
            msg: "성공",
            result: true
        };
        let val = $(selector).val();
        if(typeof(val) == "string"){
            if(val.trim() == ""){
                returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
            }
        } else if(typeof(val) == "undefined"){
            returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
        } else if(isNaN(data)){
            returnObj = { msg: name + "은(는) 필수값 입니다.", result: false};
        }
        return returnObj;
    },
    // 공통 Alert 메시지
    alert: function(returnObj){
        if(!returnObj.result){
            alert(returnObj.msg);
            // alert 무한루프 방지
            $(document.activeElement).blur();
        }
        return returnObj;
    }
}