$(function () {
    $(".fa-edit").parent('a').css('background-color', 'rgb(26, 167, 96)');
    $(".fa-edit").parent('a').css('border-color', 'rgb(26, 167, 96)');
    $(".fa-trash").parent('a').css('background-color', '#eb4f38');
    $(".fa-trash").parent('a').css('border-color', '#eb4f38');

    $('select[data-value]').each(function () {
        $(this).val($(this).attr('data-value'));
    });

    $('[data-toggle="tooltip"]').tooltip();

    // 菜单
    $('.nav .menu').click(function (e) {
        e.preventDefault();
        var iframe = $('iframe');
        iframe.attr('src', $(this).attr('href'));
    });

    // 返回按钮
    $('.goback').click(function () {
        var url = $(this).attr('data-url');

        if (typeof (url) == "undefined") {
            window.history.go(-1);
        }
        else {
            window.location = url;
        }
    });

    // 必填项星号
    $('.required-label').each(function () {
        $(this).prepend('<span>*</span>')
    });

    $('.search-form button[type="reset"]').click(function () {
        $('.search-form input').attr('value', '');
        $('.search-form select').attr('value', '');
    });

    // 表格操作列动态列宽
    $('th.flexible-btns').each(function () {
        var table = $(this).parents('table');
        var td = table.find('td.flexible-btns').first();

        if (td.length > 0) {
            var count = td.find('a, button').length;

            if (count > 3) {
                $(this).addClass('flexible-btns-max');
            }
            else if (count < 2) {
                $(this).addClass('flexible-btns-min');
            }
            else {
                $(this).addClass('flexible-btns-' + count);
            }
        }

    });

    var checkboxs = $('.i-checks');

    if (checkboxs.length > 0) {
        checkboxs.iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green"
        });
    }

    // 多选
    var chosens = $('select.chosen');

    if (chosens.length > 0) {
        chosens.chosen({
            disable_search_threshold: 10,
            no_results_text: '未检索到相关记录'
        });
    }

    // 分页
    $('#pageSizeSelect').change(function () {
        var form = $('.search-form');
        var holder = $(this).parents('.pagination-wrap').find('.pagination');
        var pageNo = parseInt(holder.attr('data-page-no') || 1);
        var pageSize = $(this).val();

        if (form.find('#pageNo').length == 0) {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0) {
            form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
        }

        form.find('#pageNo').val(pageNo);
        form.find('#pageSize').val(pageSize);
        form.submit();
    });

    //回车刷新页面
    $("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $(".search-form").submit();
        }
    });

    $('body').on('blur', '#gotoPageNo', function () {
        var val = $(this).val();
        var number = parseInt(val);

        if (isNaN(number) || number < 1) {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else {
            $(this).val(number);
            $(this).attr('data-history-value', number);
        }
    }).on('click', '#goto_button', function () {
        var form = $(this).parents('body').find('.search-form');

        if (form.find('#pageNo').length == 0) {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0) {
            form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
        }

        form.find('#pageNo').val($('#gotoPageNo').val());
        form.find('#pageSize').val(parseInt($('#pageSizeSelect').val() || 10));
        form.submit();
    }).on('click', '.clearClass', function () {
        $('.fileinput-upload-button').remove();
        $('.fileinput-upload-button').remove();
        $('.file-preview').remove();
        $('.fileinput-remove').remove();
    });

    $('.pagination').each(function () {
        var form = $(this).parents('body').find('.search-form');
        var pageNo = parseInt($(this).attr('data-page-no') || 1);
        var pageSize = parseInt($('#pageSizeSelect').val() || 10);
        var count = parseInt($(this).attr('data-count') || 0);

        $(this).pagination(count, {
            items_per_page: pageSize,
            current_page: pageNo - 1,
            num_display_entries: 5,
            num_edge_entries: 2,
            prev_text: '上一页',
            next_text: '下一页',
            show_if_single_page: true,
            callback: function (pageIndex) {
                if (form.find('#pageNo').length == 0) {
                    form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
                }

                if (form.find('#pageSize').length == 0) {
                    form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
                }

                form.find('#pageNo').val(pageIndex + 1);
                form.find('#pageSize').val(pageSize);
                form.submit();
            }
        });
    });

    var layerObject = parent.parent.layer;

    // 操作确认
    $('.layer-confirm').each(function () {
        var object = $(this);

        if (object.is("a")) {
            object.click(function (e) {
                e.preventDefault();

                var message = object.attr('data-confirm-message');
                var buttons = ['确定', '取消'];

                layerObject.confirm(message, {
                    icon: 3,
                    title: '操作确认',
                    btn: buttons,
                    shade: 'transparent'
                }, function (index) {
                    window.location = object.attr("href");
                    layerObject.close(index);
                }, function (index) {
                    layerObject.close(index);
                });
            });
        }
    });

    $('form input body').keydown(function () {
        if (window.event.keyCode == 13) {
            return true;
        }
    });

});


function canDelete(id, delete_url, redirect_url) {
    layer.confirm('确定要删除该数据么？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        $.post(delete_url, {
            id: id
        }, function (flag) {
            if (flag) {
                window.location.href = redirect_url;
            } else {
                layer.alert("该数据有关联关系，不能删除", {
                    title: "提示"
                });
            }
        });
    }, function () {
        layer.close();
    });
}

function showTip(content, title) {
    parent.layer.alert(content, {title: title});
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
        "H+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


function setIsEnabled(id, enabled, setEnabled_url, redirect_url) {
    var confirm_val;
    if (enabled) {
        confirm_val = "确定要启用该数据么";
    }
    else {
        confirm_val = "确定要禁用该数据么";
    }
    layer.confirm(confirm_val, {
        btn: ['确认', '取消'] //按钮
    }, function () {
        $.post(setEnabled_url, {
            id: id, enabled: enabled
        }, function (flag) {
            if (flag) {
                window.location.href = redirect_url;
            } else {
                layer.alert("该数据有关联关系，不能禁用", {
                    title: "提示"
                });
            }
        });
    }, function () {
        layer.close();
    });

}

function validateUpload(data) {
    var excelFileName = $('#uploadData').val();
    var formatStr = '';
    var index = excelFileName.lastIndexOf('.');
    if (excelFileName.length == 0) {
        parent.layer.alert('请选择需要上传的文件', {title: "提示"});
        return false;
    } else if (index > 0) {

        formatStr = excelFileName.substring(index);
        if (!(".doc" == formatStr || ".docx" == formatStr || ".pdf" == formatStr)) {

            parent.layer.alert('请上传word或者pdf文件', {title: "提示"});
            return false;
        }
    }
    return true;
}

