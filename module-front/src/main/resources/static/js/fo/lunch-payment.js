document.getElementById('btn-payment')?.addEventListener('click', function () {
    const amountInput = document.getElementById('chargeAmount');
    const amount = amountInput ? parseInt(amountInput.value, 10) : 10000;

    const orderData = {
        orderNo: 'ORDER_' + Date.now(),
        productName: '급식비 런치카드 충전',
        amount: amount
    };

    fetch('/easypay/request', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderData)
    })
        .then(res => res.json())
        .then(data => {
            if (data.authPageUrl) {
                window.location.href = data.authPageUrl;
            } else {
                alert("결제창 URL을 받아오지 못했습니다.");
            }
        })
        .catch(err => {
            console.error('결제 요청 오류:', err);
            alert('결제 중 오류가 발생했습니다.');
        });
});
