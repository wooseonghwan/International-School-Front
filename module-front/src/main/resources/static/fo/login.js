$('[data-role="login"]').on('click', function () {
    const webId = $.trim($('input[name="webId"]').val());
    const password = $.trim($('input[name="password"]').val());

    if (!webId || !password) {
        alert('아이디와 비밀번호를 모두 입력해주세요.');
        return;
    }

    $.ajax({
        url: '/fo/login-check',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ webId, password }),
        success: function (res) {
            if (res.result === true) {
                location.href = '/fo/main1'; // 로그인 후 이동할 경로
            } else {
                alert(res.message || '아이디 또는 비밀번호가 올바르지 않습니다.');
            }
        },
        error: function () {
            alert('로그인 중 오류가 발생했습니다.');
        }
    });
});
