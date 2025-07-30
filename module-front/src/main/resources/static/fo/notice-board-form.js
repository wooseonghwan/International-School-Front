$(document).ready(function () {
    $('input[name="passwordYn"]').on('change', function () {
        const isPasswordRequired = $('input[name="passwordYn"]:checked').val() === 'Y';
        $('input[name="password"]').prop('disabled', !isPasswordRequired);
    });

    // 초기 상태 설정 (페이지 로드 시)
    const initPasswordRequired = $('input[name="passwordYn"]:checked').val() === 'Y';
    $('input[name="password"]').prop('disabled', !initPasswordRequired);
});
$('[data-role="insert"]').on('click', function () {
    const title = $.trim($('input[name="title"]').val());
    const content = $.trim($('textarea[name="content"]').val());
    const passwordYn = $('input[name="passwordYn"]:checked').val();
    const password = $.trim($('input[name="password"]').val());

    // 유효성 검사
    if (title === '') {
        alert('제목을 입력해주세요.');
        return;
    }

    if (content === '') {
        alert('내용을 입력해주세요.');
        return;
    }

    if (passwordYn === 'Y') {
        if (password === '') {
            alert('비밀번호를 입력해주세요.');
            return;
        }
        if (!/^\d{4}$/.test(password)) {
            alert('비밀번호는 4자리 숫자여야 합니다.');
            return;
        }
    }

    const data = {
        title: title,
        content: content,
        passwordYn: passwordYn,
        password: passwordYn === 'Y' ? password : null, // 설정 안함일 경우 빈값 처리
    };

    if (!confirm("등록하시겠습니까?")) {
        return;
    }

    $.ajax({
        url: '/fo/notice-board-insert',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            alert('게시글이 등록되었습니다.');
            location.href = '/fo/notice-board';
        },
        error: function (xhr) {
            alert('등록 중 오류가 발생했습니다.');
            console.error(xhr.responseText);
        }
    });
});