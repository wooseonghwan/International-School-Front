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
        url: '/fo/expert/check-id',
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
// 이메일 형식 체크
$('#formUserEmail').on('input', function() {
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
// 현재 경력정보의 수
let careerCount = 3;
// 삭제 버튼 클릭 시 해당 경력정보 제거
$(document).on('click', '.delete-career-btn', function () {
    // 해당 경력 정보 삭제
    $(this).closest('.form-header-2nd').next('.form').remove();
    $(this).closest('.form-header-2nd').remove();
    // 남은 경력 정보 번호 재정렬
    reassignCareerNumbers();
});
$(document).on('click', '.add-career-btn', function () {
    careerCount++;
    addCareerSection(careerCount);
});
// 경력정보 번호 재정렬 함수
function reassignCareerNumbers() {
    // 남아있는 모든 경력 정보 섹션을 선택
    $('#career-section-container .form-header-2nd').each(function(index) {
        // 새로운 번호 (0부터 시작하므로 index + 1)
        const newNumber = index + 1;

        // 제목의 번호를 업데이트
        $(this).find('.form-subtitle').text(`경력정보 ${newNumber}`);

        // 관련된 form 요소들의 id와 name 속성도 업데이트
        const formSection = $(this).next('.form');

        formSection.find('[id^="formUserCompany"]').attr('id', `formUserCompany${newNumber}`);
        formSection.find('[for^="formUserCompany"]').attr('for', `formUserCompany${newNumber}`);

        formSection.find('[id^="formCompanyPosition"]').attr('id', `formCompanyPosition${newNumber}`);
        formSection.find('[for^="formCompanyPosition"]').attr('for', `formCompanyPosition${newNumber}`);

        formSection.find('[id^="formWorkPeriod1_1_"]').attr('id', `formWorkPeriod1_1_${newNumber}`);
        formSection.find('[id^="formWorkPeriod1_2_"]').attr('id', `formWorkPeriod1_2_${newNumber}`);

        formSection.find('[id^="formTask"]').attr('id', `formTask${newNumber}`);
        formSection.find('[for^="formTask"]').attr('for', `formTask${newNumber}`);
    });

    // 전체 경력 정보 개수를 재설정
    careerCount = $('#career-section-container .form-header-2nd').length;
}

// 추가된 경력사항에 대한 필드 및 DatePicker 초기화
function addCareerSection(count) {
    const careerSection = `
        <div class="form-header-2nd mt-4">
            <div class="form-subtitle">경력정보 ${count}</div>
            <div class="form-help">
                <div class="d-flex align-items-center gap-4">
                    <button type="button" class="btn btn-outline-danger btn-sm delete-career-btn">삭제</button>
                </div>
            </div>
        </div>
        <div class="form" style="--labelWidth: 120px">
            <div class="form-group">
                <label for="formUserCompany${count}" class="form-label">회사명 <span class="text-danger">*</span></label>
                <div class="form-input-wrap">
                    <input type="text" class="form-control" id="formUserCompany${count}" name="companyName" placeholder="회사명을 입력해주세요."/>
                </div>
            </div>
            <div class="form-group">
                <label for="formCompanyPosition${count}" class="form-label">직급/부서명</label>
                <div class="form-input-wrap">
                    <input type="text" class="form-control" id="formCompanyPosition${count}" name="rankName" placeholder="직급/부서명 입력해주세요."/>
                </div>
            </div>
            <div class="form-group">
                <label for="formWorkPeriod1_1_${count}" class="form-label">재직기간</label>
                <div class="form-input-wrap">
                    <div class="row align-items-center">
                        <div class="col">
                            <input type="text" class="form-control" id="formWorkPeriod1_1_${count}" name="careerStartDt" placeholder="YYYY-MM-DD" data-picker="date"/>
                        </div>
                        <div class="col-auto">~</div>
                        <div class="col">
                            <input type="text" class="form-control" id="formWorkPeriod1_2_${count}" name="careerEndDt" placeholder="YYYY-MM-DD" data-picker="date"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="formTask${count}" class="form-label">담당직무 <span class="text-danger">*</span></label>
                <div class="flex-grow-1">
                    <textarea id="formTask${count}" class="form-control" name="job" placeholder="내용을 입력해주세요."></textarea>
                </div>
            </div>
        </div>`;

    // 새로운 경력정보 섹션을 추가
    $('#career-section-container').append(careerSection);

    // Datepicker 초기화
    $(`#formWorkPeriod1_1_${count}, #formWorkPeriod1_2_${count}[data-picker='date']`).datepicker({
        dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
        monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        changeYear: true,
        changeMonth: true,
        showMonthAfterYear: true,
        dateFormat: 'yy-mm-dd',
        autoclose: true,
        todayHighlight: true
    });
}
$("button[data-role='fileUpload']").click(function(){
    //$("input[id='fileUpload']").click();
    let target = $(this).data("target");
    $("input[id='"+target+"']").click();
});
let fileSeqNo = 0;
let subSeqNo = 0;

let fileSeqArr = new Array();
let subSeqArr = new Array();

$("input[id='fileSeq'],input[id='subSeq']").change(function(){
    let id = $(this).attr("id");
    let fileNo = 0;
    let selectedFileLength = $(this)[0].files.length;

    for (let i=0; i<selectedFileLength; i++) {
        let file = $(this)[0].files[i];

        if( validateFile(file) ) { //이미지 파일 체크
            if (true) { //이미지 파일 체크
                let reader = new FileReader();
                reader.onload = function () {
                    if (id == "fileSeq") {
                        fileSeqArr.push(file);
                        fileNo = fileSeqNo++;
                    } else if (id == "subSeq") {
                        subSeqArr.push(file);
                        fileNo = subSeqNo++;
                    }
                };
                reader.readAsDataURL(file);

                let htmlData = '';
                htmlData += '<div id="new-' + id + '-' + fileNo + '" class="filebox" style="float: left; margin-right: 5px;">';
                htmlData += '   <span class="name" style="margin-right: 5px;">' + file.name + '</span>';
                htmlData += '   <a href="javascript:void(0);" class="delete-file" onclick="deleteTypeFile(\'' + id + '\',' + fileNo + ');" style="cursor: pointer;">';
                htmlData += '       <i class="far fa-minus-square"></i>';
                htmlData += '   </a>&nbsp;&nbsp;';
                htmlData += '</div>';
                $("div[id='" + id + "-div']").append(htmlData);
            } else {
                return false;
            }
        }
    }
    $("#fileSeq").value = "";
});
function deleteTypeFile(id, no) {
    $("#new-"+id+"-"+no).remove();
    if( id=="fileSeq" ) {
        fileSeqArr[no].is_delete = true;
    }
    else if( id=="subSeq" ) {
        subSeqArr[no].is_delete = true;
    }
}
function validateFile(file) {
    let types = ['application/pdf', 'application/msword',
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];

    if (!types.includes(file.type)) {
        showModalAlert('PDF 또는 Word 형식의 문서만 첨부 가능합니다.');
        return false;
    } else {
        return true;
    }
}
let formArea = document.getElementById('formArea');
let extraInputBox = document.getElementById('extraInputBox');
formArea.addEventListener('change', function() {
    if (this.value === 'ETC') {
        extraInputBox.style.display = 'block';
    } else {
        extraInputBox.style.display = 'none';
    }
});
// 기타 선택시 입력칸 생성
let checkbox = document.getElementById('formDetailJob6');
let inputField = document.getElementById('jobCategoryEtc');
checkbox.addEventListener('change', function() {
    if (this.checked) {
        inputField.disabled = false;
    } else {
        inputField.disabled = true;
        inputField.value = '';
    }
});
// 등록 버튼 클릭시
$('button[data-role="save"]').click( function () {

    if (!isIdChecked) {
        // 중복 체크가 완료되지 않았거나 실패한 경우
        showModalAlert('아이디 중복 체크를 먼저 해주세요.');
        return false; // 저장 작업을 막음
    }

    function getBirth() {
        const year = $('select[name=year]').val();
        const month = $('select[name=month]').val();
        const day = $('select[name=day]').val();

        // 연, 월, 일이 모두 선택된 경우만 합침
        if (year && month && day) {
            const birth = `${year}/${month.padStart(2, '0')}/${day.padStart(2, '0')}`;
            return birth;
        }
        return null; // 하나라도 선택되지 않았을 경우 null 반환
    }
    // 경력
    let careerInfo = [];
    $('#career-section-container .form').each(function () {
        const $this = $(this);

        const companyName = $this.find('input[name=companyName]').val().trim();
        const rankName = $this.find('input[name=rankName]').val().trim();
        const careerStartDt = $this.find('input[name=careerStartDt]').val().trim();
        const careerEndDt = $this.find('input[name=careerEndDt]').val().trim();
        const job = $this.find('textarea[name=job]').val().trim();

            const careerDetails = {
                companyName: companyName,
                rankName: rankName,
                careerStartDt: careerStartDt,
                careerEndDt: careerEndDt,
                job: job
            };
            careerInfo.push(careerDetails);
    });

    // 각 경력 정보를 ','로 구분하여 출력
    careerInfo.forEach((info, index) => {
        console.log(`경력정보 ${index + 1}: `, info);
    });
    // 자문 시간
    const advisoryNeedTimes = $('input[name=advisoryNeedTime]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    // 주중 자문 시간대 정보
    const weekDayAdvisoryStartDt = $('select[name=weekDayAdvisoryStartDt]').val();
    const weekDayAdvisoryEndDt = $('select[name=weekDayAdvisoryEndDt]').val();
    const weekDayOption = $('select[name=weekDayOption]').val();
    // 주말 자문 시간대 정보
    const weekendAdvisoryStartDt = $('select[name=weekendAdvisoryStartDt]').val();
    const weekendAdvisoryEndDt = $('select[name=weekendAdvisoryEndDt]').val();
    const weekendOption = $('select[name=weekendOption]').val();

    // 모든 자문 시간대 정보 객체
    const advisoryDetails = {
        advisoryNeedTime: advisoryNeedTimes,
        weekday: {
            weekDayAdvisoryStartDt: weekDayAdvisoryStartDt,
            weekDayAdvisoryEndDt: weekDayAdvisoryEndDt,
            weekDayOption: weekDayOption,
        },
        weekend: {
            weekendAdvisoryStartDt: weekendAdvisoryStartDt,
            weekendAdvisoryEndDt: weekendAdvisoryEndDt,
            weekendOption: weekendOption,
        },
    };

    const jobCategoryDetails = $('input[name=jobCategoryDetail]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const jobCategoryEtc = $('input[name=jobCategoryEtc]').val();
    const jobCategoryDetails1 = {
        jobCategoryDetail: jobCategoryDetails,
        etc: {
            jobCategoryEtc: jobCategoryEtc
        }
    }
    console.log(jobCategoryDetails1);

    const id = $('input[name=id]').val().trim();
    const password = $('input[name=password]').val().trim();
    const name = $('input[name=name]').val().trim();
    const gender = $('input[name=gender]:checked').val();
    const phone = $('input[name=phone]').val().trim();
    const birth = getBirth();
    const email = $('input[name=email]').val().trim();
    const nation = $('select[name=nation]').val();
    const needTime = $('select[name=needTime]').val();
    const linkedinUrl = $('input[name=linkedinUrl]').val();
    const selfIntroKr = $('textarea[name=selfIntroKr]').val();
    const selfIntroEn = $('textarea[name=selfIntroEn]').val();
    const infoAgreeYn = $('input[name=infoAgreeYn]').is(':checked') ? 'Y' : 'N';
    const useAgreeYn = $('input[name=useAgreeYn]').is(':checked') ? 'Y' : 'N';
    const expertSolutionYn = $('input[name=expertSolutionYn]').is(':checked') ? 'Y' : 'N';
    const expertList = careerInfo
    const advisoryDetail = advisoryDetails
    const jobCategory =  $('select[name=jobCategory]').val();
    const advisoryLang1 =  $('select[name=advisoryLang1]').val();
    const advisoryLang2 =  $('select[name=advisoryLang2]').val();
    const timeAdvisory =  $('input[name=timeAdvisory]').val();
    const timeAdvisoryOption =  $('select[name=timeAdvisoryOption]').val();
    const companyName =  $('input[name=companyName]').val();
    const job =  $('textarea[name=job]').val();
    const advisoryNeedTime =  $('input[name=advisoryNeedTime]:checked').val();
    const area =  $('select[name=area]').val();
    const areaEtc =  $('input[name=areaEtc]').val();

    if (!id) {
        showModalAlert('아이디를 입력해 주세요.');
        return;
    }
    if (!password) {
        showModalAlert('비밀번호를 입력해 주세요.');
        return;
    }
    if (!name) {
        showModalAlert('이름을 입력해 주세요.');
        return;
    }
    if (!phone) {
        showModalAlert('전화번호를 입력해 주세요.');
        return;
    }
    if (!email) {
        showModalAlert('이메일을 입력해 주세요.');
        return;
    }
    if (!timeAdvisory) {
        showModalAlert('시간당 자문료를 선택해 주세요.');
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
    if (!companyName) {
       showModalAlert('회사명을 입력해 주세요.');
       return;
    }
    if (!job) {
       showModalAlert('담당직무를 입력해 주세요.');
       return;
    }
    if (!advisoryNeedTime) {
       showModalAlert('자문 선호 시간대를 선택해 주세요.');
       return;
    }
    let formData = new FormData();

    // JSON 데이터를 문자열로 변환하여 추가
    formData.append("foExpertDTO", new Blob([JSON.stringify({
        id, password, name, gender, phone, birth, email, nation, needTime, linkedinUrl,
        selfIntroKr, selfIntroEn, infoAgreeYn, useAgreeYn, expertList, advisoryDetail,
        advisoryLang1, advisoryLang2, timeAdvisory, timeAdvisoryOption, jobCategory,
        jobCategoryDetails1, area, areaEtc, expertSolutionYn
    })], { type: "application/json" }));

    // 파일 데이터를 추가
    fileSeqArr.forEach((file, index) => {
        if (!file.is_delete) {
            formData.append(`expertFile`, file); // expertFile 배열로 전송
        }
    });

    subSeqArr.forEach((file, index) => {
        if (!file.is_delete) {
            formData.append(`expertSub`, file); // expertSub 배열로 전송
        }
    });

    showConfirmModal('전문가 등록을 완료하시겠습니까?', function() {
        $.ajax({
            type: "POST",
            url: "/fo/expert/insert-expert",
            data: formData,
            processData: false,
            contentType: false,
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
    });
})
// 임시저장 버튼 클릭시
$('button[data-role="tempSave"]').click( function () {

    function getBirth() {
        const year = $('select[name=year]').val();
        const month = $('select[name=month]').val();
        const day = $('select[name=day]').val();

        // 연, 월, 일이 모두 선택된 경우만 합침
        if (year && month && day) {
            const birth = `${year}/${month.padStart(2, '0')}/${day.padStart(2, '0')}`;
            return birth;
        }
        return null; // 하나라도 선택되지 않았을 경우 null 반환
    }
    // 경력
    let careerInfo = [];
    $('#career-section-container .form').each(function () {
        const $this = $(this);

        const companyName = $this.find('input[name=companyName]').val().trim();
        const rankName = $this.find('input[name=rankName]').val().trim();
        const careerStartDt = $this.find('input[name=careerStartDt]').val().trim();
        const careerEndDt = $this.find('input[name=careerEndDt]').val().trim();
        const job = $this.find('textarea[name=job]').val().trim();

        const careerDetails = {
            companyName: companyName,
            rankName: rankName,
            careerStartDt: careerStartDt,
            careerEndDt: careerEndDt,
            job: job
        };
        careerInfo.push(careerDetails);
    });

    // 각 경력 정보를 ','로 구분하여 출력
    careerInfo.forEach((info, index) => {
        console.log(`경력정보 ${index + 1}: `, info);
    });
    // 자문 시간
    const advisoryNeedTimes = $('input[name=advisoryNeedTime]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    // 주중 자문 시간대 정보
    const weekDayAdvisoryStartDt = $('select[name=weekDayAdvisoryStartDt]').val();
    const weekDayAdvisoryEndDt = $('select[name=weekDayAdvisoryEndDt]').val();
    const weekDayOption = $('select[name=weekDayOption]').val();
    // 주말 자문 시간대 정보
    const weekendAdvisoryStartDt = $('select[name=weekendAdvisoryStartDt]').val();
    const weekendAdvisoryEndDt = $('select[name=weekendAdvisoryEndDt]').val();
    const weekendOption = $('select[name=weekendOption]').val();

    // 모든 자문 시간대 정보 객체
    const advisoryDetails = {
        advisoryNeedTime: advisoryNeedTimes,
        weekday: {
            weekDayAdvisoryStartDt: weekDayAdvisoryStartDt,
            weekDayAdvisoryEndDt: weekDayAdvisoryEndDt,
            weekDayOption: weekDayOption,
        },
        weekend: {
            weekendAdvisoryStartDt: weekendAdvisoryStartDt,
            weekendAdvisoryEndDt: weekendAdvisoryEndDt,
            weekendOption: weekendOption,
        },
    };

    const jobCategoryDetails = $('input[name=jobCategoryDetail]:checked').map(function() {
        return this.value; // 체크된 체크박스의 value 값
    }).get()
    const jobCategoryEtc = $('input[name=jobCategoryEtc]').val();
    const jobCategoryDetails1 = {
        jobCategoryDetail: jobCategoryDetails,
        etc: {
            jobCategoryEtc: jobCategoryEtc
        }
    }

    const id = $('input[name=id]').val().trim();
    const password = $('input[name=password]').val().trim();
    const name = $('input[name=name]').val().trim();
    const gender = $('input[name=gender]:checked').val();
    const phone = $('input[name=phone]').val().trim();
    const birth = getBirth();
    const email = $('input[name=email]').val().trim();
    const nation = $('select[name=nation]').val();
    const needTime = $('select[name=needTime]').val();
    const linkedinUrl = $('input[name=linkedinUrl]').val();
    const selfIntroKr = $('textarea[name=selfIntroKr]').val();
    const selfIntroEn = $('textarea[name=selfIntroEn]').val();
    const infoAgreeYn = $('input[name=infoAgreeYn]').is(':checked') ? 'Y' : 'N';
    const useAgreeYn = $('input[name=useAgreeYn]').is(':checked') ? 'Y' : 'N';
    const expertSolutionYn = $('input[name=expertSolutionYn]').is(':checked') ? 'Y' : 'N';
    const expertList = careerInfo
    const advisoryDetail = advisoryDetails
    const jobCategory =  $('select[name=jobCategory]').val();
    const advisoryLang1 =  $('select[name=advisoryLang1]').val();
    const advisoryLang2 =  $('select[name=advisoryLang2]').val();
    const timeAdvisory =  $('input[name=timeAdvisory]').val();
    const timeAdvisoryOption =  $('select[name=timeAdvisoryOption]').val();
    const companyName =  $('input[name=companyName]').val();
    const job =  $('textarea[name=job]').val();
    const advisoryNeedTime =  $('input[name=advisoryNeedTime]:checked').val();
    const area =  $('select[name=area]').val();
    const areaEtc =  $('input[name=areaEtc]').val();
    let formData = new FormData();

    // JSON 데이터를 문자열로 변환하여 추가
    formData.append("foExpertDTO", new Blob([JSON.stringify({
        id, password, name, gender, phone, birth, email, nation, needTime, linkedinUrl,
        selfIntroKr, selfIntroEn, infoAgreeYn, useAgreeYn, expertList, advisoryDetail,
        advisoryLang1, advisoryLang2, timeAdvisory, timeAdvisoryOption, jobCategory,
        jobCategoryDetails1, area, areaEtc, expertSolutionYn
    })], { type: "application/json" }));

    showConfirmModal('임시 저장하시겠습니까?', function() {
        $.ajax({
            type: "POST",
            url: "/fo/expert/insert-expert-temp",
            data: formData,
            processData: false,
            contentType: false,
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
// 00시 ~ 23시까지의 option 추가 (주중 및 주말 공용)
function populateTimeOptions(startSelectId, endSelectId) {
    const startSelect = document.getElementById(startSelectId);
    const endSelect = document.getElementById(endSelectId);

    for (let i = 0; i <= 23; i++) {
        let time = i.toString().padStart(2, '0') + ":00"; // "00:00", "01:00" 등 형식
        let optionStart = document.createElement('option');
        let optionEnd = document.createElement('option');

        optionStart.value = time;
        optionStart.textContent = time;
        optionEnd.value = time;
        optionEnd.textContent = time;

        startSelect.appendChild(optionStart);
        endSelect.appendChild(optionEnd);
    }
}
// 유효성 검사: 종료 시간이 시작 시간보다 빠르거나 같으면 경고(alert)
function validateTimeSelection(startSelectId, endSelectId) {
    const startTime = document.getElementById(startSelectId).value;
    const endTime = document.getElementById(endSelectId).value;

    if (startTime && endTime && startTime >= endTime) {
        showModalAlert('종료 시간은 시작 시간보다 늦어야 합니다.');
        return false;
    }
    return true;
}
// 페이지 로딩 시 시간 옵션을 동적으로 추가 및 유효성 검사 설정
document.addEventListener('DOMContentLoaded', function () {
    // 주중 시간 설정
    populateTimeOptions('weekDayAdvisoryStartDt', 'weekDayAdvisoryEndDt');
    document.getElementById('weekDayAdvisoryEndDt').addEventListener('change', function() {
        validateTimeSelection('weekDayAdvisoryStartDt', 'weekDayAdvisoryEndDt');
    });
    // 주말 시간 설정
    populateTimeOptions('weekendAdvisoryStartDt', 'weekendAdvisoryEndDt');
    document.getElementById('weekendAdvisoryEndDt').addEventListener('change', function() {
        validateTimeSelection('weekendAdvisoryStartDt', 'weekendAdvisoryEndDt');
    });
});
// 모달 메세지 입력 폼
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
// 모달에서 확인 버튼 클릭시, 자동 체크
$('#modal-expert-participation .btn-primary').click(function() {
    $('#formParticipationAgree').prop('checked', true);
});