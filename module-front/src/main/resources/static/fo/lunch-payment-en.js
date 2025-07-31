$(() => {
    // 결제 콜백 result 존재 시 알럿처리
    const result = $('input[name=result]').data('value');
    if (result) {
        const alertMessage = result === 'SUCCESS' ? 'Your payment has been successfully completed.' : 'A system error has occurred. Please contact the administrator.';
        alert(alertMessage);
        // 히스토리에서 파라미터 제거 (URL 정리)
        const newUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, newUrl);
    }

    // 결제금액 변경 시 노출 텍스트 변경
    $(document).on('change', 'select[name=chargeAmount]', function () {
        const $this = $(this);
        const curAmount = parseInt($this.val());
        $('p[data-field-name=disp_chargeAmount]').text(`${curAmount.toLocaleString()} won`)

    })

    document.getElementById('btn-payment')?.addEventListener('click', function () {
        const amountInput = document.getElementById('chargeAmount');
        const amount = amountInput ? parseInt(amountInput.value, 10) : 10000;

        const orderData = {
            shopOrderNo: 'ORDER_' + Date.now(),
            amount: amount,
            langFlag: "ENG",
            orderInfo: {
                goodsName: 'Recharge Student Lunch Card'
            },
            shopValueInfo: {
                value1: $('input[name=custNo]').data('value'),
                value2: "ENG",
                value3: amount
            }
        };

        fetch('/payment/kicc/transaction', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        })
            .then(res => res.json())
            .then(data => {
                if (data.data.authPageUrl) {
                    window.location.href = data.data.authPageUrl;
                } else {
                    alert("A system error has occurred. Please contact the administrator.");
                }
            })
            .catch(err => {
                alert("A system error has occurred. Please contact the administrator.");
            });
    });
})
