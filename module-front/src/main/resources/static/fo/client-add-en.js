// 유효한 아이디 패턴 (5~15자리, 영문 또는 영문+숫자 조합)
const idPattern = /^(?=.*[0-9])[a-zA-Z][a-zA-Z0-9]{4,14}$/;
// 아이디 중복체크
let isIdChecked = false; // 중복 체크 여부를 나타내는 변수
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
        invalidFeedback.text('Please enter your ID.');
        invalidFeedback.show();
        return;
    }

    if (!idPattern.test(id)) {
        invalidFeedback.text('The ID must be within 5 to 15 characters, consisting of English letters or a combination of letters and numbers.');
        invalidFeedback.show();
        return;
    }
    // AJAX 요청
    $.ajax({
        url: '/fo/client/check-id',
        type: 'GET',
        data: { id: id },
        success: function(response) {
            // response.data에 count 값이 있으므로, count가 1 이상이면 중복된 아이디로 간주
            if (response.data >= 1) {
                invalidFeedback.text('This ID is already in use.');
                invalidFeedback.show();
                isIdChecked = false; // 중복 체크 실패
            } else {
                invalidFeedback.hide();
                formText.text('This ID is available.');
                formText.show();
                isIdChecked = true; // 중복 체크 성공
            }
        },
        error: function() {
            invalidFeedback.text('An error occurred while checking ID duplicates.');
            invalidFeedback.show();
            isIdChecked = false; // 중복 체크 실패
        },
        complete: function() {
            // 버튼 활성화 및 텍스트 복구
            checkButton.prop('disabled', false).text('Duplicate check');
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
$('[data-role="onlyNumber"]').on('input', function () {
    $(this).val($(this).val().replaceAll(/[^0-9]/g, ""));
})
// 이메일 형식 체크
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
let checkbox = document.getElementById('formConsultingType6');
let inputField = document.getElementById('consultingCateEtc');
let expertCheckbox = document.getElementById('formExpert4');
let expertInput = document.getElementById('expertNeedEtc');
let consultingCheckbox = document.getElementById('formConsultingWay4');
let consultingInput = document.getElementById('consultingMethodEtc');
// 체크박스 상태에 따라 입력 필드 활성화/비활성화 함수
checkbox.addEventListener('change', function() {
    if (this.checked) {
        inputField.disabled = false;
    } else {
        inputField.disabled = true;
        inputField.value = '';
    }
});
expertCheckbox.addEventListener('change', function() {
    if (this.checked) {
        expertInput.disabled = false;
    } else {
        expertInput.disabled = true;
        expertInput.value = '';
    }
});
consultingCheckbox.addEventListener('change', function() {
    if (this.checked) {
        consultingInput.disabled = false;
    } else {
        consultingInput.disabled = true;
        consultingInput.value = '';
    }
});
$('button[data-role="save"]').click( function () {

    if (!isIdChecked) {
        showModalAlert('Please check for duplicate ID first.');
        return false;
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
        showModalAlert('Please enter your ID.');
        return;
    }
    if (!password) {
        showModalAlert('Please enter your password.');
        return;
    }
    if (!companyName) {
        showModalAlert('Please enter your company name.');
        return;
    }
    if (!companyCategory) {
        showModalAlert('Please select a type.');
        return;
    }
    if (!managerName) {
        showModalAlert('Please enter the Contact person name.');
        return;
    }
    if (!managerPhone) {
        showModalAlert('Please enter the Contact person phone number.');
        return;
    }
    if (!managerEmail) {
        showModalAlert('Please enter the Contact person Email address.');
        return;
    }
    if (!consultingCategory) {
        showModalAlert('Please select a consulting type.');
        return;
    }
    if (!expertNeed) {
        showModalAlert('Please select expert selection.');
        return;
    }
    if (!consultingMethod) {
        showModalAlert('Please select a consulting method.');
        return;
    }
    if (!consultingRequestTime) {
        showModalAlert('Please select a consulting request time.');
        return;
    }
    if (!consultingSubject) {
        showModalAlert('Please enter your consulting topic.');
        return;
    }
    if (!coreQuestion) {
        showModalAlert('Please enter your key questions.');
        return;
    }
    if (!timeNeedAdvisory) {
        showModalAlert('Please select a Hourly consulting fee');
        return;
    }
    if (!infoAgreeYn) {
        showModalAlert('Please agree to the collection and use of personal information.');
        return;
    }
    if (!useAgreeYn) {
        showModalAlert('Please agree to the Terms of Use.');
        return;
    }

    showConfirmModal('Would you like to complete your customer registration?', function() {
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
                showModalAlert('Registration has been completed.');
                $('button[data-role="ok"]').click( function () {
                    location.reload();
                });
            },
            error: function (xhr, status, error) {
                showModalAlert('An error occurred. Please contact the administrator.');
            }
        });
    })
})
// 임시저장 버튼 클릭시
$('button[data-role="tempSave"]').click( function () {

    if (!isIdChecked) {
        // 중복 체크가 완료되지 않았거나 실패한 경우
        showModalAlert('Please check for duplicate ID first.');
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

    showConfirmModal('Would you like to save it temporarily?', function() {
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
                showModalAlert('has been temporarily saved.');
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
    showConfirmModal('Would you like to go to the main screen? Registered contents will be initialized.', function() {
        window.location.href = "/fo/main1-en";
    })
});
function showModalAlert(message) {
    // Set the modal message
    document.querySelector('#alert-sample2 .alert-message2').innerText = message;
    // Show the modal (Bootstrap 5)
    let myModal = new bootstrap.Modal(document.getElementById('alert-sample2'));
    myModal.show();
}
function showConfirmModal(message, onConfirm) {
    // 모달 메시지 변경
    document.querySelector('#confirmModal2 .alert-message2').innerText = message;

    // 모달 띄우기
    let myModal = new bootstrap.Modal(document.getElementById('confirmModal2'));
    myModal.show();

    // 확인 버튼 클릭 이벤트 처리
    document.getElementById('confirmModalBtn2').onclick = function() {
        // 모달 닫기
        myModal.hide();

        // 확인 버튼 클릭 시 수행할 작업
        if (onConfirm) {
            onConfirm();
        }
    };
}