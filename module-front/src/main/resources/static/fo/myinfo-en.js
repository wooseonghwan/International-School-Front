$(document).ready(function () {
    $('[data-role="update"]').on('click', function () {
        const newPw = $.trim($('input[name="webPw"]').val());
        const confirmPw = $.trim($('input[name="webPwConfirm"]').val());

        if (!newPw || !confirmPw) {
            alert('Please enter the new password and confirm it.');
            return;
        }

        if (newPw !== confirmPw) {
            alert('The new password and confirmation do not match.');
            return;
        }

        $.ajax({
            url: '/fo/update-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ newPw: newPw }),
            success: function () {
                alert('Password has been successfully changed. Please log in again.');
                location.href = '/fo/logout';
            },
            error: function () {
                alert('An error occurred while changing the password.');
            }
        });
    });
});