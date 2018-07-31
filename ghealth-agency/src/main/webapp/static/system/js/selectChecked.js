$(function () {

    $('body').on('click', '.check-controller', function () {
        if ($(this).is(":checked")) {
            $(".check-instance:enabled").prop("checked", true);
        }
        else {
            $(".check-instance").prop("checked", false);
        }
    }).on('click', '.check-instance', function () {
        if (!$(this).is(":checked")) {
            $(".check-controller").prop("checked", false);
        }
        else {
            var chknum = $(".check-instance").size();//选项总个数
            var chk = $('input[type=checkbox]:checked').length;//选中总数
            if (chknum == chk) {
                $(".check-controller").prop("checked", true);
            } else {
                $(".check-controller").prop("checked", false);
            }
        }
    }).on('click', '#subForm', function () {
        $("#myModal").modal('hide');
        $("#uploadForm").submit();
    });

});






