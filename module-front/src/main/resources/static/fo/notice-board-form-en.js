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

    // Validation
    if (title === '') {
        alert('Please enter a title.');
        return;
    }

    if (content === '') {
        alert('Please enter content.');
        return;
    }

    if (passwordYn === 'Y') {
        if (password === '') {
            alert('Please enter a password.');
            return;
        }
        if (!/^\d{4}$/.test(password)) {
            alert('The password must be a 4-digit number.');
            return;
        }
    }

    const data = {
        title: title,
        content: content,
        passwordYn: passwordYn,
        password: passwordYn === 'Y' ? password : null,
    };

    if (!confirm("Do you want to submit this post?")) {
        return;
    }

    $.ajax({
        url: '/fo/notice-board-insert',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            alert('The post has been successfully submitted.');
            location.href = '/fo/notice-board';
        },
        error: function (xhr) {
            alert('An error occurred while submitting the post.');
            console.error(xhr.responseText);
        }
    });
});
