$(document).ready(function () {
    $('button[data-role=btn-search-form]').click(function () {
        $('form[name=searchForm]').submit();
    })
})