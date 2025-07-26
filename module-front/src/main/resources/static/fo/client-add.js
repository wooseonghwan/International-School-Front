// 유효한 아이디 패턴 (5~15자리, 영문 또는 영문+숫자 조합)
const idPattern = /^(?=.*[0-9])[a-zA-Z][a-zA-Z0-9]{4,14}$/;
// 아이디 중복체크
let isIdChecked = false;
$('button[data-role="checkId"]').click(function() {
    let userIdInput = $('#formUserId');
    let id = userIdInput.val().trim();
    let invalidFeedback = $('#idCheck');
    let formText = $('#form-text');
    let checkButton = $(this);

    // 이전 메시지 숨기기
    invalidFeedback.hide();
    formText.hide();

    // 아이디 입력 여부 및 유효성 확인
    if (id === '') {
        invalidFeedback.text('아이디를 입력해주세요.');
        invalidFeedback.show();
        return;
    }

    if (!idPattern.test(id)) {
        invalidFeedback.text('아이디는 5~15자리 이내 영문 또는 영문+숫자 조합이어야 합니다.');
        invalidFeedback.show();
        return;
    }
    $.ajax({
        url: '/fo/client/check-id',
        type: 'GET',
        data: { id: id },
        success: function(response) {
            if (response.data >= 1) {
                invalidFeedback.text('이미 사용중인 아이디 입니다.');
                invalidFeedback.show();
                isIdChecked = false;
            } else {
                invalidFeedback.hide();
                formText.text('사용 가능한 아이디입니다.');
                formText.show();
                isIdChecked = true;
            }
        },
        error: function() {
            invalidFeedback.text('아이디 중복 체크 중 오류가 발생했습니다.');
            invalidFeedback.show();
            isIdChecked = false;
        },
        complete: function() {
            // 버튼 활성화 및 텍스트 복구
            checkButton.prop('disabled', false).text('중복확인');
        }
    });
});
// 비밀번호 패턴 검사
const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+[\]{};':"\\|,.<>/?]).{8,16}$/;
function validatePassword() {
    let passwordInput = $('#formUserPassword');
    let passwordReInput = $('#formUserPasswordRe');
    let password = passwordInput.val().trim();
    let passwordRe = passwordReInput.val().trim();
    let invalidPatternFeedback = $('#password-not-pattern');
    let invalidSameFeedback = $('#password-not-same');

    // 비밀번호 유효성 검사
    if (!passwordPattern.test(password)) {
        invalidPatternFeedback.show();
        return false;
    } else {
        invalidPatternFeedback.hide();
    }

    // 비밀번호 확인 일치 여부 검사
    if (password !== passwordRe) {
        invalidSameFeedback.show();
        return false;
    } else {
        invalidSameFeedback.hide();
    }

    return true;
}
// 비밀번호와 비밀번호 확인 입력 시 검사
$('#formUserPassword, #formUserPasswordRe').on('keyup', function() {
    validatePassword();
});
// 폼 제출 시 검사
$('form').on('submit', function(e) {
    if (!validatePassword()) {
        e.preventDefault();  // 비밀번호가 유효하지 않으면 폼 제출 중단
    }
});
// 숫자만 입력
$('[data-role="onlyNumber"]').on('input', function () {
    $(this).val($(this).val().replaceAll(/[^0-9]/g, ""));
})
function toggleInputBox(selectedValue) {
    let inputBox = document.getElementById('extraInputBox');
    if (selectedValue === 'ETC') {
        inputBox.style.display = 'block';
    } else {
        inputBox.style.display = 'none';
    }
}
$('#formUserChargeEmail').on('input', function() {
    const emailInput = $(this);
    const email = emailInput.val().trim();
    const emailInvalidFeedback = $('#email-invalid-feedback');

    // 이메일 형식 정규 표현식
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // 이메일 형식 검사
    if (emailPattern.test(email)) {
        emailInvalidFeedback.hide();
    } else {
        emailInvalidFeedback.show();
    }
});
// 컨설팅 요청시간이 금일중 일때만 상세요청시간 disabled 해제
$('#formLangA').change(function() {
    const selectedValue = $(this).val();

    if (selectedValue === 'TODAY') {
        $('#formLangB').prop('disabled', false);
    } else {
        $('#formLangB').prop('disabled', true).val('');
    }
});
// 기타 일때 입력폼 활성화
let checkbox = document.getElementById('formConsultingType6');
let inputField = document.getElementById('consultingCateEtc');
let expertCheckbox = document.getElementById('formExpert4');
let expertInput = document.getElementById('expertNeedEtc');
let consultingCheckbox = document.getElementById('formConsultingWay4');
let consultingInput = document.getElementById('consultingMethodEtc');
// 컨설팅 유형
checkbox.addEventListener('change', function() {
    if (this.checked) {
        inputField.disabled = false;
    } else {
        inputField.disabled = true;
        inputField.value = '';
    }
});
// 전문가 선정
expertCheckbox.addEventListener('change', function() {
    if (this.checked) {
        expertInput.disabled = false;
    } else {
        expertInput.disabled = true;
        expertInput.value = '';
    }
});
// 컨설팅 유형
consultingCheckbox.addEventListener('change', function() {
    if (this.checked) {
        consultingInput.disabled = false;
    } else {
        consultingInput.disabled = true;
        consultingInput.value = '';
    }
});
// 등록 버튼 클릭시
$('button[data-role="save"]').click( function () {

    if (!isIdChecked) {
        // 중복 체크가 완료되지 않았거나 실패한 경우
        showModalAlert('아이디 중복 체크를 먼저 해주세요.');
        return false; // 저장 작업을 막음
    }
    // 컨설팅 유형
    const consultingCategorys = $('input[name=consultingCategory]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const consultingCateEtc = $('input[name=consultingCateEtc]').val();
    const consultingCategoryDetails = {
        consultingCategory: consultingCategorys,
        etc: {
            consultingCateEtc: consultingCateEtc
        }
    }
    // 전문가 선정
    const expertNeeds = $('input[name=expertNeed]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const expertNeedEtc = $('input[name=expertNeedEtc]').val();
    const expertNeedDetails = {
        expertNeed: expertNeeds,
        etc: {
            expertNeedEtc: expertNeedEtc
        }
    }
    // 컨설팅 방식
    const consultingMethods = $('input[name=consultingMethod]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const consultingMethodEtc = $('input[name=consultingMethodEtc]').val();
    const consultingMethodDetails = {
        consultingMethod: consultingMethods,
        etc: {
            consultingMethodEtc: consultingMethodEtc
        }
    }
    // 기타 요청사항
    const etcRequests = $('input[name=etcRequest]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const etcRequestDetails = {
        etcRequest: etcRequests,
    }

    const id = $('input[name=id]').val().trim();
    const password = $('input[name=password]').val().trim();
    const companyName = $('input[name=companyName]').val().trim();
    const companyCategory = $('select[name=companyCategory]').val();
    const companyCategoryEtc = $('input[name=companyCategoryEtc]').val();
    const managerName = $('input[name=managerName]').val();
    const managerPhone = $('input[name=managerPhone]').val();
    const managerEmail = $('input[name=managerEmail]').val();
    const nation = $('select[name=nation]').val();
    const needTime = $('select[name=needTime]').val();
    const consultingRequestTime = $('select[name=consultingRequestTime]').val();
    const consultingRequestTimeDetail = $('select[name=consultingRequestTimeDetail]').val();
    const consultingRequestTimeEtc = $('input[name=consultingRequestTimeEtc]').val();
    const consultingSubject = $('input[name=consultingSubject]').val();
    const coreQuestion = $('textarea[name=coreQuestion]').val();
    const timeNeedAdvisory = $('select[name=timeNeedAdvisory]').val();
    const timeNeedAdvisoryEtc = $('input[name=timeNeedAdvisoryEtc]').val();
    const infoAgreeYn = $('input[name=infoAgreeYn]').is(':checked') ? 'Y' : 'N';
    const useAgreeYn = $('input[name=useAgreeYn]').is(':checked') ? 'Y' : 'N';
    const consultingCategoryDetail = consultingCategoryDetails
    const expertNeedDetail = expertNeedDetails
    const consultingMethodDetail = consultingMethodDetails
    const etcRequestDetail = etcRequestDetails
    const consultingCategory = $('input[name=consultingCategory]:checked').val();
    const expertNeed = $('input[name=expertNeed]:checked').val();
    const consultingMethod = $('input[name=consultingMethod]:checked').val();

    if (!id) {
        showModalAlert('아이디를 입력해 주세요.');
        return;
    }
    if (!password) {
        showModalAlert('비밀번호를 입력해 주세요.');
        return;
    }
    if (!companyName) {
        showModalAlert('회사명을 입력해 주세요.');
        return;
    }
    if (!companyCategory) {
        showModalAlert('유형을 선택해 주세요.');
        return;
    }
    if (!managerName) {
        showModalAlert('담당자명을 입력해 주세요.');
        return;
    }
    if (!managerPhone) {
        showModalAlert('담당자 휴대폰 번호를 입력해 주세요.');
        return;
    }
    if (!managerEmail) {
        showModalAlert('담당자 이메일을 입력해 주세요.');
        return;
    }
    if (!consultingCategory) {
        showModalAlert('컨설팅 유형을 선택해 주세요.');
        return;
    }
    if (!expertNeed) {
        showModalAlert('전문가 선정을 선택해 주세요.');
        return;
    }
    if (!consultingMethod) {
        showModalAlert('컨설팅 방식을 선택해 주세요.');
        return;
    }
    if (!consultingRequestTime) {
        showModalAlert('컨설팅 요청시간을 선택해 주세요.');
        return;
    }
    if (!consultingSubject) {
        showModalAlert('컨설팅 주제를 입력해 주세요.');
        return;
    }
    if (!timeNeedAdvisory) {
        showModalAlert('핵심 질문을 입력해 주세요.');
        return;
    }
    if (!infoAgreeYn) {
        showModalAlert('개인정보 수집 및 이용에 동의해 주세요.');
        return;
    }
    if (!useAgreeYn) {
        showModalAlert('이용약관에 동의해 주세요.');
        return;
    }

    showConfirmModal('고객사 등록을 완료하시겠습니까?', function() {
        $.ajax({
            type: "POST",
            url: "/fo/client/insert-client",
            data: JSON.stringify({
                id, password, companyName, companyCategory, companyCategoryEtc, managerName, managerPhone, managerEmail, nation, needTime,
                consultingRequestTime, consultingRequestTimeDetail, consultingRequestTimeEtc, consultingSubject, coreQuestion, timeNeedAdvisory, timeNeedAdvisoryEtc, infoAgreeYn, useAgreeYn, consultingCategoryDetail
                , expertNeedDetail, consultingMethodDetail, etcRequestDetail
            }),
            contentType: "application/json",
            success: function (response) {
                showModalAlert('등록이 완료 되었습니다.');
                $('button[data-role="ok"]').click( function () {
                    location.reload();
                });
            },
            error: function (xhr, status, error) {
                showModalAlert('오류가 발생했습니다. 관리자에게 문의해주세요.');
            }
        });
    })
})
// 임시저장 버튼 클릭시
$('button[data-role="tempSave"]').click( function () {

    if (!isIdChecked) {
        // 중복 체크가 완료되지 않았거나 실패한 경우
        showModalAlert('아이디 중복 체크를 먼저 해주세요.');
        return false; // 저장 작업을 막음
    }
    // 컨설팅 유형
    const consultingCategorys = $('input[name=consultingCategory]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const consultingCateEtc = $('input[name=consultingCateEtc]').val();
    const consultingCategoryDetails = {
        consultingCategory: consultingCategorys,
        etc: {
            consultingCateEtc: consultingCateEtc
        }
    }
    // 전문가 선정
    const expertNeeds = $('input[name=expertNeed]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const expertNeedEtc = $('input[name=expertNeedEtc]').val();
    const expertNeedDetails = {
        expertNeed: expertNeeds,
        etc: {
            expertNeedEtc: expertNeedEtc
        }
    }
    // 컨설팅 방식
    const consultingMethods = $('input[name=consultingMethod]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const consultingMethodEtc = $('input[name=consultingMethodEtc]').val();
    const consultingMethodDetails = {
        consultingMethod: consultingMethods,
        etc: {
            consultingMethodEtc: consultingMethodEtc
        }
    }
    // 기타 요청사항
    const etcRequests = $('input[name=etcRequest]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const etcRequestDetails = {
        etcRequest: etcRequests,
    }

    const id = $('input[name=id]').val().trim();
    const password = $('input[name=password]').val().trim();
    const companyName = $('input[name=companyName]').val().trim();
    const companyCategory = $('select[name=companyCategory]').val();
    const companyCategoryEtc = $('input[name=companyCategoryEtc]').val();
    const managerName = $('input[name=managerName]').val();
    const managerPhone = $('input[name=managerPhone]').val();
    const managerEmail = $('input[name=managerEmail]').val();
    const nation = $('select[name=nation]').val();
    const needTime = $('select[name=needTime]').val();
    const consultingRequestTime = $('select[name=consultingRequestTime]').val();
    const consultingRequestTimeDetail = $('select[name=consultingRequestTimeDetail]').val();
    const consultingRequestTimeEtc = $('input[name=consultingRequestTimeEtc]').val();
    const consultingSubject = $('input[name=consultingSubject]').val();
    const coreQuestion = $('textarea[name=coreQuestion]').val();
    const timeNeedAdvisory = $('select[name=timeNeedAdvisory]').val();
    const timeNeedAdvisoryEtc = $('input[name=timeNeedAdvisoryEtc]').val();
    const infoAgreeYn = $('input[name=infoAgreeYn]').is(':checked') ? 'Y' : 'N';
    const useAgreeYn = $('input[name=useAgreeYn]').is(':checked') ? 'Y' : 'N';
    const consultingCategoryDetail = consultingCategoryDetails
    const expertNeedDetail = expertNeedDetails
    const consultingMethodDetail = consultingMethodDetails
    const etcRequestDetail = etcRequestDetails
    const consultingCategory = $('input[name=consultingCategory]:checked').val();
    const expertNeed = $('input[name=expertNeed]:checked').val();
    const consultingMethod = $('input[name=consultingMethod]:checked').val();

    showConfirmModal('임시 저장하시겠습니까?', function() {
        $.ajax({
            type: "POST",
            url: "/fo/client/insert-client-temp",
            data: JSON.stringify({
                id, password, companyName, companyCategory, companyCategoryEtc, managerName, managerPhone, managerEmail, nation, needTime,
                consultingRequestTime, consultingRequestTimeDetail, consultingRequestTimeEtc, consultingSubject, coreQuestion, timeNeedAdvisory, timeNeedAdvisoryEtc, infoAgreeYn, useAgreeYn, consultingCategoryDetail
                , expertNeedDetail, consultingMethodDetail, etcRequestDetail
            }),
            contentType: "application/json",
            success: function (response) {
                showModalAlert('임시저장 되었습니다.');
                $('button[data-role="ok"]').click( function () {
                    location.reload();
                });
            },
            error: function (xhr, status, error) {
                showModalAlert('오류가 발생했습니다. 관리자에게 문의해주세요.');
            }
        });
    })
})
// 메인으로 이동 버튼 클릭시
$('button[data-role="main"]').click(function () {
    showConfirmModal('메인화면으로 이동하시겠습니까? 등록된 내용은 초기화됩니다.', function() {
        window.location.href = "/";
    })
});
function showModalAlert(message) {
    document.querySelector('#alert-sample1 .alert-message').innerText = message;
    let myModal = new bootstrap.Modal(document.getElementById('alert-sample1'));
    myModal.show();
}
function showConfirmModal(message, onConfirm) {
    // 모달 메시지 변경
    document.querySelector('#confirmModal .alert-message').innerText = message;

    // 모달 띄우기
    let myModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    myModal.show();

    // 확인 버튼 클릭 이벤트 처리
    document.getElementById('confirmModalBtn').onclick = function() {
        // 모달 닫기
        myModal.hide();

        // 확인 버튼 클릭 시 수행할 작업
        if (onConfirm) {
            onConfirm();
        }
    };
}