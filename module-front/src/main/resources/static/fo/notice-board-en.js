let selectedQnaId = null;

// 클릭 시 모달 열기
$(document).on('click', '.secret-link', function () {
    selectedQnaId = $(this).data('qna-id');
    $('#modal-pw-write').modal('show');
    $('#modal-pw-write input.form-control').val('');
    $('#modal-pw-write button.btn-dark').prop('disabled', true);
});

// 입력값 체크 → 버튼 활성화
$('#modal-pw-write input.form-control').on('input', function () {
    const val = $(this).val().trim();
    $('#modal-pw-write button.btn-dark').prop('disabled', val === '');
});

// 확인 버튼 클릭 시 비밀번호 검증
$('#modal-pw-write button.btn-dark').on('click', function () {
    const inputPw = $('#modal-pw-write input.form-control').val().trim();

    if (!selectedQnaId || !inputPw) return;

    $.ajax({
        url: '/fo/notice-board-check-password',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            qnaId: selectedQnaId,
            password: inputPw
        }),
        success: function (res) {
            if (res === true || res.result === true) {
                window.location.href = '/fo/notice-board-detail-en?qnaId=' + selectedQnaId;
            } else {
                alert('The password you entered does not match.');
            }
        },
        error: function () {
            alert('An error occurred while verifying the password.');
        }
    });
});
$(document).ready(function () {
    $('.search-form').keyup(function (event) {
        if (event.keyCode === 13) {
            $('form[name=searchForm]').submit();
        }
    });
    $('button[data-role=btn-search-form]').click(function () {
        $('form[name=searchForm]').submit();
    })
})