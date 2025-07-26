/*
function isPersonnelSelected() {
    return $('input[name="optionPersonnel"]:checked').length > 0;
}
function isDaysSelected() {
    return $('input[name="optionRange"]:checked').length > 0;
}
function isLodgingSelected() {
    return $('input[name="optionHotel"]:checked').length > 0;
}
// 모달 열림을 완전히 막기 위해 Bootstrap 이벤트 사용
$('#modal-option-step2, #modal-option-step3, #modal-option-details').on('show.bs.modal', function (event) {
    const modalId = $(event.target).attr('id');

    const isDetail = modalId === 'modal-option-details';

    if (isDetail) {
        if (!isPersonnelSelected()) {
            alert('인원을 선택해주세요.');
            event.preventDefault(); // 모달 열림 막기
            return false;
        }
    }

    if (isDetail) {
        if (!isDaysSelected()) {
            alert('일정을 선택해주세요.');
            event.preventDefault(); // 모달 열림 막기
            return false;
        }
    }

    if (isDetail) {
        if (!isLodgingSelected()) {
            alert('숙소를 선택해주세요.');
            event.preventDefault(); // 모달 열림 막기
            return false;
        }
    }
});
/!*!// 메인에서 예약하기 버튼 클릭 시
$('button[data-role="openReservation"]').on('click', function () {
    $('#modal-option-details').modal('show');
    const golfNameMap = {
        "ECC": "에치젠 CC",
    };
    // 선택된 인원, 일정, 숙소 가져오기
    const selectedPersonnel = $('#personnelBtn').text();
    const selectedSchedule = $('#scheduleBtn').text();
    const selectedLodging = $('#lodgeBtn').text();

    // 선택된 인원, 일정, 숙소 정보
    const golfCode = $('#tourButton').attr('data-value');
    const golfName = golfNameMap[golfCode] || golfCode;
    const packageName = "고마츠 골프 투어 패키지";
    const totalPrice = $(".total span").text();

    // 모달 내용 업데이트
    $('#modal-option-details .d-flex .badge').eq(0).text(golfName); // 골프장
    $('#modal-option-details .d-flex .badge').eq(1).text(selectedPersonnel); // 인원
    $('#modal-option-details .d-flex .badge').eq(2).text(selectedSchedule); // 일정
    $('#modal-option-details .d-flex .badge').eq(3).text(selectedLodging); // 숙소

    $('#modal-option-details .option-details dt').eq(0).next().text(packageName); // 패키지 명
    $('#modal-option-details .option-details dt').eq(1).next().text(golfName); // 골프장
    $('#modal-option-details .option-details dt').eq(2).next().text(selectedSchedule); // 여행 일정
    $('#modal-option-details .option-details dt').eq(3).next().text(selectedPersonnel); // 여행 인원
    $('#modal-option-details .option-details dt').eq(4).next().text(selectedLodging); // 숙소 정보
    $('#modal-option-details .option-details dt').eq(5).next().find('span').text(totalPrice); // 총 비용
});
// 이름, 전화번호 까지 입력 후 예약완료 버튼 클릭 시
$('button[data-role="reserve"]').on('click', function () {

    const person = $('input[name=optionPersonnel]:checked').val();
    const days = $('input[name=optionRange]:checked').val();
    const golfName = $('#tourButton').attr('data-value');
    const startDate = window.selectedStartDate;
    const endDate = window.selectedEndDate;
    const lodging = $('input[name=optionHotel]:checked').val();
    const totalPriceText = $(".total span").text().replace(/,/g, '');
    const totalPrice = parseInt(totalPriceText, 10);
    const reserveNm = $('input[name=name]').val();
    const reservePhone = $('input[name=phone]').val();
    const marketingYn = $('input[name=marketingYn]').is(':checked') ? 'Y' : 'N';

    $.ajax({
        type: "POST",
        url: "/fo/main/insert-reservation",
        data: JSON.stringify({ person, days, golfName, startDate, endDate, lodging, totalPrice, reserveNm, reservePhone, marketingYn }),
        contentType: "application/json",
        success: function (response) {
            location.reload();
        },
        error: function (xhr, status, error) {
            alert('예약에 실패했습니다. 다시 시도해 주세요.');
        }
    });
})*!/


*/
