(function ($) {
    var BarcodeApplyFormHandler = function (base) {
        this.base = base;
        this.form = $('#barcode_form');
    };

    BarcodeApplyFormHandler.prototype.initValidate = function () {

    };

    BarcodeApplyFormHandler.prototype.initListeners = function () {
        var setting = {
            rules: {
                areaCode: {
                    required: true,
                    maxlength: 3,
                    PositiveInteger: true
                },
                count: {
                    required: true,
                    PositiveInteger: true
                }
            },
            messages: {
                areaCode: {
                    required: '区号不能为空',
                    maxlength: '区号长度不能超过三位'
                },
                count: {
                    required: '生产个数不能为空'
                }
            }
        };

        var validation = this.form.validate(setting);

        var handler = this;
        var base = handler.base;
        $("#create").click(function () {
            if (validation.form()) {
                var areaCode = $("#areaCode").val();
                var freeOfCharge = $("#freeOfCharge").val();
                var count = $("#count").val();
                $.ajax({
                    url: base + '/barcode/create.jsp',
                    data: {prefixCode: areaCode, isFree: freeOfCharge, count: count},
                    async: false,
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        $("#beginNum").val(data);
                        $("#beginNum").valid();
                        //成品生成条形码，选择成品，添加生成个数，再选择成品，需要更新
                        var printCount = $("#count").val();
                        if (printCount != "" && printCount > 0) {
                            updatePreview();
                        }
                    }
                });
            }
        });

        $("#print").click(function () {
            var areaCode = $("#areaCode").val() + $("#freeOfCharge").val();
            var beginNum = $("#beginNum").val();
            var buildCount = $("#count").val();
            var numWidth = $("#numWidth").val() - 0;
            if (buildCount > 0) {
                var LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'), base);
                LODOP.PRINT_INITA(0, 0, 800, 1600, "条码打印");

                for (var i = 0; i < buildCount; i++) {
                    var barcode = areaCode + formatNumber(beginNum - 0 + i, numWidth);
                    for(var j = 0; j < 4; j ++)
                    {
                        LODOP.SET_PRINT_STYLE("FontName", "Consolas");
                        LODOP.SET_PRINT_PAGESIZE(1, "32mm", "20mm", "A4R");
                        LODOP.ADD_PRINT_BARCODE(16, 16, "28mm", "14mm", "128A", barcode);
                        LODOP.SET_PRINT_STYLEA("All", "ShowBarText", 0);
                        LODOP.SET_PRINT_STYLEA("All", "LetterSpacing", 3);

                        LODOP.SET_PRINT_STYLEA("All", "FontSize", 6);

                        LODOP.NewPage();
                    }
                }
                LODOP.PREVIEW();
            }

            /*
             $("#beginNum,#productCode,#buildCount").val('');
             $("div#codePreview p").each(function(index){
                    $(this).text("");
            });*/
        });


        var formatNumber = function (number, width) {
            var numLen = number.toString().length;
            var result = "";
            for (var i = numLen; i < width; i++) {
                result += "0";
            }

            result += number;
            return result;
        };

        var updatePreview = function () {
            //var areaCode = ($("#areaCode").val().length == 3 ? ($("#areaCode").val() + "0") : $("#areaCode").val())+$("#freeOfCharge").val();
            var areaCode = $("#areaCode").val() + $("#freeOfCharge").val();
            var beginNum = $("#beginNum").val();
            var buildCount = $("#count").val();
            var numWidth = $("#numWidth").val() - 0;
            var codes = [];
            if ((buildCount - 0) > 5) {
                codes.push(areaCode + formatNumber(beginNum - 0, numWidth));
                codes.push(areaCode + formatNumber(beginNum - 0 + 1, numWidth));
                codes.push(areaCode + formatNumber(beginNum - 0 + 2, numWidth));
                codes.push("...");
                codes.push(areaCode + formatNumber((beginNum - 0) + (buildCount - 0) - 2, numWidth));
                codes.push(areaCode + formatNumber((beginNum - 0) + (buildCount - 0) - 1, numWidth));
            }
            else {
                for (var i = beginNum; i < ((beginNum - 0) + (buildCount - 0)); i++) {
                    codes.push(areaCode + formatNumber(i, numWidth));
                }
            }

            $("div#codePreview p").each(function (index) {
                if ((beginNum != "") && (numWidth <= 10 || "")) {
                    $(this).text(codes[index] || "");
                } else {
                    $(this).text("");
                }

            });
        };

    };

    $.init = function (base) {
        $.validator.addMethod("PositiveInteger", function (value) {
            var j = /^[0-9]*[1-9][0-9]*$/;
            return j.exec(value) ? true : false;
        }, "请输入大于0的正整数");
        var instance = new BarcodeApplyFormHandler(base);
        instance.initListeners();
        instance.initValidate();
    };
})(jQuery);