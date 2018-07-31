$(function(){
    jQuery.validator.addMethod("decimal", function(value, element) {
        var decimal = /^-?\d+(\.\d{1,2})?$/;
        return this.optional(element) || (decimal.test(value));
    });

    /**
     * 验证手机号码
     */
    jQuery.validator.addMethod("isMobile",function(value, element) {
        var length = value.length;
        var mobile = /^1\d{10}|[*]{3}$/;
        return this.optional(element)|| mobile.test(value);
    }, "请正确填写您的手机号码");

    jQuery.validator.addMethod("han",function(value, element){

        var reg = /^[\u4E00-\u9FA5A-Za-z0-9_]{0,20}$/
        return this.optional(element) || (reg.test(value));
    }, "不允许特殊字符");

    jQuery.validator.addMethod("isLet", function(value, element) {
        var reg = /^[A-Za-z0-9\d]+$/;
        return this.optional(element) || reg.test($.trim(value));
    }, "请输入字母或数字");

    jQuery.validator.addMethod("isEmpty", function(value, element) {
        return this.optional(element) || ($.trim(value) != "");
    }, "不能为空串");

    // 电话号码验证
    jQuery.validator.addMethod("isTel", function(value, element) {
        var tel = /^\d{3,4}-?\d{7,8}$/; //电话号码格式010-12345678
        var mobile = /^1\d{10}$/;
        return this.optional(element) || (tel.test(value))|| mobile.test(value);
    }, "请正确填写您的电话号码");

    // 0-100比例
    jQuery.validator.addMethod("precent", function(value, element) {
        var reg = /^(\\d|[1-9]\\d|100)$/;
        return this.optional(element) || (reg.test(value));
    }, "请正确填写比例");

    jQuery.validator.addMethod("alnum",function(value, element){
        return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
    }, "只能包括英文字母和数字");


    jQuery.validator.addMethod("samplenumber",function(value, element){
        var reg = /^d+(.d+)?$/
        return this.optional(element) || (!reg.test(value));
    }, "请输入大于0的数字（包含小数）");

    jQuery.validator.addMethod("tennumber",function(value, element){

        var reg = /^[0-9a-zA-Z]{10}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入10位数字加字母");

    jQuery.validator.addMethod("cipher",function(value, element){

        var reg = /^[a-zA-Z0-9_]{6,16}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入6-16位密码");

    jQuery.validator.addMethod("decimals",function(value, element){

        var reg = /(?!^0\.0?0$)^[0-9][0-9]?(\.[0-9]{1,2})?$|^100$/
        return this.optional(element) || (reg.test(value));
    }, "请输入0-100的数，小数点后最多2位");

    jQuery.validator.addMethod("twentylimit",function(value, element){

        var reg = /^[0-9a-zA-Z]{1,20}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入20位数字加字母");

    jQuery.validator.addMethod("tenlimit",function(value, element){

        var reg = /^[a-zA-Z0-9]{1,10}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入10位以内纯数字或纯字母或数字加字母");

    jQuery.validator.addMethod("sixchinese",function(value, element){

        var reg = /^[\u4e00-\u9fa5]{1,6}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入6位以内汉字");

    jQuery.validator.addMethod("tenchinese",function(value, element){

        var reg = /^[\u4e00-\u9fa5]{1,10}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入10位以内汉字");

    jQuery.validator.addMethod("twentychinese",function(value, element){

        var reg = /^[\u4e00-\u9fa5]{1,20}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入20位以内汉字");

    jQuery.validator.addMethod("thirtychinese",function(value, element){

        var reg = /^[\u4e00-\u9fa5]{1,30}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入30位以内汉字");

    jQuery.validator.addMethod("fiftychinese",function(value, element){

        var reg = /^[\u4e00-\u9fa5]{1,50}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入50位以内汉字");

    jQuery.validator.addMethod("twohundred",function(value, element){

        var reg =  /^$|^\w{0,200}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入200位以内字符");

    jQuery.validator.addMethod("pricelimit",function(value, element){

        var reg = /^(?:[1-9]?[0-9]{0,4}(?:\.\d{1,2})?|100000(?:\.0{1,2})?)$/
        return this.optional(element) || (reg.test(value));
    }, "请输入0到100000的金额 小数点后2位");

    jQuery.validator.addMethod("twonumber",function(value, element){

        var reg = /^[a-zA-Z]{2}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入2位字母");

    jQuery.validator.addMethod("seven",function(value, element){

        var reg = /^.{1,7}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入7位以内");


    jQuery.validator.addMethod("sanshi",function(value, element){

        var reg = /^.{1,30}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入30位以内");


    jQuery.validator.addMethod("ten",function(value, element){

        var reg = /^.{1,10}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入10位字符");

    jQuery.validator.addMethod("twenty",function(value, element){

        var reg = /^[^\\]{0,20}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入20位字符");

    jQuery.validator.addMethod("thirty",function(value, element){

        var reg = /^$|^\w{0,30}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入30位字符");

    jQuery.validator.addMethod("fifty",function(value, element){

        var reg = /^[^\\]{0,50}$/
        return this.optional(element) || (reg.test(value));
    }, "请输入50位字符");

    jQuery.validator.addMethod("isMainSampleCode", function(value, element) {

        var ids = $(element).parent().parent().parent().find("input[name=samplePackageStatus]").val();

        var result = false;
        $.ajax({
            type: "POST",
            url: base+"/order/validateSampleCode.do",
            data: {
                id:ids,
                mainSampleCode:value
            },
            async:false,
            dataType: "json",
            success: function(data){
                result = data;
            },

        });

        return result;
    }, "样本编号重复");


});

