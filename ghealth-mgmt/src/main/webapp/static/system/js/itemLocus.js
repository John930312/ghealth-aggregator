function changeByselectVal(sex_value) {
    // 1 男 2 女
    var weman_ = $(".weman");
    var man_ = $(".man");
    if (sex_value == 1) {

        hideDiv(weman_);
        man_.show();
        $(".man button").attr('disabled', true);
    }
    else if (sex_value == 2) {
        hideDiv(man_);
        weman_.show();
        $(".weman button").attr('disabled', true);
    }
    else {
        $(".man button").attr('disabled', false);
        $(".weman button").attr('disabled', false);
        man_.show();
        weman_.show();
    }
}
function hideDiv(obj)
{
    obj.hide();
    obj.find("input").each(function (i, v) {
        $(v).removeAttr("required");
        $(v).val("");
    });
}

function sycVal(obj, target, source) {
    var s1 = "#" + source + "_1";
    var s2 = "#" + source + "_2";
    var s3 = "#" + source + "_3";

    var t1 = "#" + target + "_1";
    var t2 = "#" + target + "_2";
    var t3 = "#" + target + "_3";
    var flag = true;
    $(obj).parents(".col-sm-3").find("input").each(function (i, v) {
        var input_value = $(v).val();
        if (null == input_value || "" == input_value) {
            layer.alert('还没有赋值完。。。', {
                title: "提示"
            });
            flag = false;
            return false;
        }
    });
    if (flag) {
        $(s1).val($(t1).val());
        $(s2).val($(t2).val());
        $(s3).val($(t3).val());
    }
}

function subForm(obj) {
    var arr = [];
    var input_1 = $(obj).parents("fieldset").find(".data-obj").find(".input_1");
    arr.push(assigning(input_1));
    var input_2 = $(obj).parents("fieldset").find(".data-obj").find(".input_2");
    arr.push(assigning(input_2));
    var input_3 = $(obj).parents("fieldset").find(".data-obj").find(".input_3");
    arr.push(assigning(input_3));
    var jsonString = JSON.stringify(arr);
    $("input[name='influenceFactors']").val(jsonString + "");

    var testingItem_ = $("#testingItem").magicSuggest().getSelection()[0];
    if (undefined == testingItem_) {
        layer.alert('请选择位检测项目', {
            title: "提示"
        });
        return false;
    }
    $('input[name="testingItemId"]').val(testingItem_.id)
    var locus_ = $("#locus").magicSuggest().getSelection()[0];
    if (undefined == locus_) {
        layer.alert('请选择位点片段', {
            title: "提示"
        });
        return false;
    }
    $('input[name="locusId"]').val(locus_.id)
    var check = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
    var flag = true;
    $(".riskVal").children(":visible").find("input").each(function (i, v) {
        if (!check.test($(v).val()) || $(v).val() == 0) {
            layer.alert('输入数据有误！', {
                title: "提示"
            });
            flag = false;
            return false;
        }   
    });
    if (flag) {
        $('form').submit();
    }
}

function assigning(obj) {
    var obj_ = {};
    obj_.genetype = $(obj[0]).val();
    obj_.male_factor = Number($(obj[1]).val()).toFixed(5);
    console.log(obj_.male_factor)
    obj_.female_factor = Number($(obj[2]).val()).toFixed(5);
    return obj_;
}

function setVal(arr) {
    $(".gene").find('input').each(function (i, v) {
        $(v).val(arr[i].genetype);
    });
    $(".man").find('input').each(function (i, v) {
        $(v).val(arr[i].maleFactor);
    });
    $(".weman").find('input').each(function (i, v) {
        $(v).val(arr[i].femaleFactor);
    });

    function delInputVal(obj) {
        obj.find("input").each(function (i, v) {
            $(v).val("");
        });
    }
}