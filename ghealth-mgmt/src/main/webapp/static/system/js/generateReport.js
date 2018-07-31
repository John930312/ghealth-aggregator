function showReportModel() {
    layer.confirm('确定要生成报告么？', {
        btn: ['确定', '取消']
    }, function (index, layero) {
        var loadIndex = layer.load();
        layer.close(index);
        $("h1").text("报告正在生成中。。。");
        report(loadIndex);
    }, function (index) {
        layer.close(index);
    });
}

function report(loadindex) {
    $.ajax({
        url: base + "/order/report/generate.jsp",
        type: "POST",
        data: {id: $("#orderId").val()},
        success: function (data) {
            if (null == data || "" == data) {
                setError();
                layer.close(loadindex);
                return;
            };
            var timer = window.setInterval(function () {
                $.ajax({
                    type: "GET",
                    url: base + "/order/getReportStatus.jsp?status=" + data,
                    success: function (data) {

                        if (null != data) {
                            window.clearInterval(timer);
                            if (data.status == 1) {
                                $("h1").text("生成报告成功！！！");
                                $(".generate").text("重新生成报告！");
                                var pdfFileUrl = data.pdfFileUrl.replace(/\\/g, '%2F');
                                var wordFileUrl = data.wordFileUrl.replace(/\\/g, '%2F');
                                $(".pdf").attr("href", base + "/order/download.jsp?url=" + pdfFileUrl);
                                $(".doc").attr("href", base + "/order/download.jsp?url=" + wordFileUrl);
                                $(".download").show();
                            }
                            else if (data.status == 2) {
                                $("h1").text("生成报告失败！！！");
                                $(".generate").text("重新生成报告！");
                            }
                            ;
                            layer.close(loadindex);
                        }
                    },
                    error: function (data) {
                        layer.close(loadindex);
                        setError();
                        $("h1").css("color", "red");
                    }
                });
            }, 5000);
        },
        error:function(data)
        {
            setError();
        }
    });
};

function redirectBack() {
    parent.redirectBack();
};

function setError()
{
    $("h1").text("生成报告失败！！！");
    $(".generate").text("重新生成报告！");
}