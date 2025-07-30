$(document).ready(function () {
    $('[data-role="update"]').on('click', function () {
        const newPw = $.trim($('input[name="webPw"]').val());
        const confirmPw = $.trim($('input[name="webPwConfirm"]').val());

        if (!newPw || !confirmPw) {
            alert('새 비밀번호와 비밀번호 확인을 입력해주세요.');
            return;
        }

        if (newPw !== confirmPw) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        $.ajax({
            url: '/fo/update-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ newPw: newPw }),
            success: function () {
                alert('비밀번호가 성공적으로 변경되었습니다. 다시 로그인해주세요.');
                location.href = '/fo/logout';
            },
            error: function () {
                alert('비밀번호 변경 중 오류가 발생했습니다.');
            }
        });
    });
});