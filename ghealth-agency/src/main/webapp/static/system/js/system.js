$(function()
{
    $('select[data-value]').each(function()
    {
        $(this).val($(this).attr('data-value'));
    });
    
    $('[data-toggle="tooltip"]').tooltip();

    // 菜单
    $('.nav .menu').click(function(e)
    {
        e.preventDefault();
        var iframe = $('iframe');
        iframe.attr('src', $(this).attr('href'));
    });

    // 返回按钮
    $('.goback').click(function()
    {
        var url = $(this).attr('data-url');

        if (typeof (url) == "undefined")
        {
            window.history.go(-1);
        }
        else
        {
            window.location = url;
        }
    });

    // 必填项星号
    $('.required-label').each(function()
    {
        $(this).prepend('<span>*</span>')
    });

    $('.search-form button[type="reset"]').click(function()
    {
        $('.search-form input').attr('value', '');
        $('.search-form select').attr('value', '');
    });

    // 表格操作列动态列宽
    $('th.flexible-btns').each(function()
    {
        var table = $(this).parents('table');
        var td = table.find('td.flexible-btns').first();

        if (td.length > 0)
        {
            var count = td.find('a, button').length;

            if (count > 3)
            {
                $(this).addClass('flexible-btns-max');
            }
            else if (count < 2)
            {
                $(this).addClass('flexible-btns-min');
            }
            else
            {
                $(this).addClass('flexible-btns-' + count);
            }
        }

    });

    var checkboxs = $('.i-checks');

    if (checkboxs.length > 0)
    {
        checkboxs.iCheck({
            checkboxClass : "icheckbox_square-green",
            radioClass : "iradio_square-green"
        });
    }

    // 多选
    var chosens = $('select.chosen');

    if (chosens.length > 0)
    {
        chosens.chosen({
            disable_search_threshold : 10,
            no_results_text : '未检索到相关记录'
        });
    }

    // 分页
    $('#pageSizeSelect').change(function()
    {
        var form = $('.search-form');
        var holder = $(this).parents('.pagination-wrap').find('.pagination');
        var pageNo = parseInt(holder.attr('data-page-no') || 1);
        var pageSize = $(this).val();

        if (form.find('#pageNo').length == 0)
        {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0)
        {
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

    $('body').on('blur', '#gotoPageNo', function()
    {
        var val = $(this).val();
        var number = parseInt(val);

        if (isNaN(number) || number < 1)
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);
        }
    }).on('click', '#goto_button', function()
    {
        var form = $(this).parents('body').find('.search-form');
        
        if (form.find('#pageNo').length == 0)
        {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0)
        {
            form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
        }
        
        form.find('#pageNo').val($('#gotoPageNo').val());
        form.find('#pageSize').val(parseInt($('#pageSizeSelect').val() || 10));
        form.submit();
    }).on('click', '.clearClass', function(){
        $('.fileinput-upload-button').remove();
        $('.fileinput-upload-button').remove();
        $('.file-preview').remove();
        $('.fileinput-remove').remove();
    });;

    $('.pagination').each(function()
    {
        var form = $(this).parents('body').find('.search-form');
        var pageNo = parseInt($(this).attr('data-page-no') || 1);
        var pageSize = parseInt($('#pageSizeSelect').val() || 10);
        var count = parseInt($(this).attr('data-count') || 0);

        $(this).pagination(count, {
            items_per_page : pageSize,
            current_page : pageNo - 1,
            num_display_entries : 5,
            num_edge_entries : 2,
            prev_text : '上一页',
            next_text : '下一页',
            show_if_single_page : true,
            callback : function(pageIndex)
            {
                if (form.find('#pageNo').length == 0)
                {
                    form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
                }

                if (form.find('#pageSize').length == 0)
                {
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
    $('.layer-confirm').each(function()
    {
        var object = $(this);

        if (object.is("a"))
        {
            object.click(function(e)
            {
                e.preventDefault();

                var message = object.attr('data-confirm-message');
                var buttons = [ '确定', '取消' ];

                layerObject.confirm(message, {
                    icon : 3,
                    title : '操作确认',
                    btn : buttons,
                    shade : 'transparent'
                }, function(index)
                {
                    window.location = object.attr("href");
                    layerObject.close(index);
                }, function(index)
                {
                    layerObject.close(index);
                });
            });
        }
    });

    $('form input body').keydown(function()
    {
        if (window.event.keyCode == 13)
        {
            return true;
        }
    });


});