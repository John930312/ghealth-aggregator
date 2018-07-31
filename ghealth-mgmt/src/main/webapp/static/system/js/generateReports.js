function submitMyForm() {
    var ids = "";
    $(".check-instance:checkbox").each(function (i, v) {
        if ($(this).prop("checked")) {
            ids += $(this).val() + "-"
        }
    });
    if (ids != "") {
        layer.confirm('确定批量生成选择的订单么？', {
            btn: ['确定', '取消']
        }, function (index) {
            var loadIndex = layer.load();
            layer.close(index);
            report(loadIndex, ids);
        }, function (index) {
            layer.close(index);
        });
    }
    else {
        layer.alert('选项不符合条件', {
            title: "提示"
        });
    }
}

function report(loadindex, ids) {
    $.ajax({
        url: base + "/order/generateReport.jsp",
        type: "POST",
        data: {ids: ids},
        success: function (data) {
            if (null == data) {
                layer.alert("报告生成失败!", {title: "提示"});
                return;
            }
            ;
            var timer = window.setInterval(function () {
                $.ajax({
                    type: "GET",
                    url: base + "/order/getReportStatus.jsp?status=" + data,
                    success: function (data) {
                        if (data != null) {
                            window.clearInterval(timer);
                            if (data.status == 1) {
                                layer.msg('生成报告成功！', function () {
                                    parent.hideModel();
                                });
                            }
                            else if (data.status == 2) {
                                layer.alert("报告生成失败!", {title: "提示"});
                            }
                            ;
                            layer.close(loadindex);
                        }
                    },
                    error: function (data) {
                        layer.alert('some error', {
                            title: "提示"
                        });
                        layer.close(loadindex);
                    }
                });
            }, 5000);

        },
    });
}

function redirectBack() {
    parent.redirectBack();
}