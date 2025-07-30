$(document).ready(function () {
    let isCardIdValid = false;
    let isWebIdValid = false;

// 카드번호 중복 체크
    $('input[name="cardId"]').on('blur', function () {
        const cardId = $(this).val().trim();
        if (!cardId) return;

        $.ajax({
            url: '/fo/check-card-id',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({cardId}),
            success: function (res) {
                if (res === true || res.result === true) {
                    alert('이미 등록된 카드번호입니다.');
                    isCardIdValid = false;
                    // 아래 항목 입력 비활성화
                    $('input[name="custName"], input[name="webId"], input[name="password"], input[name="passwordConfirm"]').prop('disabled', true);
                } else {
                    isCardIdValid = true;
                    // 아래 항목 입력 가능
                    $('input[name="custName"], input[name="webId"], input[name="password"], input[name="passwordConfirm"]').prop('disabled', false);
                }
            },
            error: function () {
                alert('카드번호 확인 중 오류가 발생했습니다.');
                isCardIdValid = false;
            }
        });
    });

// 아이디 중복 체크
    $('input[name="webId"]').on('blur', function () {
        const webId = $(this).val().trim();
        if (!webId) return;

        $.ajax({
            url: '/fo/check-user-id',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({webId}),
            success: function (res) {
                if (res === true || res.result === true) {
                    alert('이미 사용 중인 아이디입니다.');
                    isWebIdValid = false;

                    // 비밀번호 입력 비활성화
                    $('input[name="password"], input[name="passwordConfirm"]').prop('disabled', true);
                } else {
                    isWebIdValid = true;

                    // 비밀번호 입력 가능
                    $('input[name="password"], input[name="passwordConfirm"]').prop('disabled', false);
                }
            },
            error: function () {
                alert('아이디 확인 중 오류가 발생했습니다.');
                isWebIdValid = false;
            }
        });
    });

// 회원가입 처리
    $('[data-role="signUp"]').on('click', function () {
        const cardId = $.trim($('input[name="cardId"]').val());
        const custName = $.trim($('input[name="custName"]').val());
        const webId = $.trim($('input[name="webId"]').val());
        const password = $.trim($('input[name="password"]').val());
        const passwordConfirm = $.trim($('input[name="passwordConfirm"]').val());

        // 유효성 검사
        if (!cardId || !custName || !webId || !password || !passwordConfirm) {
            alert('모든 항목을 입력해주세요.');
            return;
        }

        if (!isCardIdValid) {
            alert('카드번호가 중복되었거나 확인되지 않았습니다.');
            return;
        }

        if (!isWebIdValid) {
            alert('아이디가 중복되었거나 확인되지 않았습니다.');
            return;
        }

        if (password !== passwordConfirm) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        // 회원가입 실행
        const data = {
            cardId: cardId,
            custName: custName,
            webId: webId,
            password: password
        };

        $.ajax({
            url: '/fo/insert-user',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                alert('회원가입이 완료되었습니다.');
                location.href = '/fo/login';
            },
            error: function (xhr) {
                alert(xhr.responseText || '회원가입 중 오류가 발생했습니다.');
            }
        });
    });
})