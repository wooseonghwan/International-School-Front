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