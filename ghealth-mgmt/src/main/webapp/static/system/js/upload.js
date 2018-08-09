var accessid;
var host;
var policyBase64;
var signature;
var callbackbody;
var expire = 0;
var orderId;
var orderCode;
var objectKey;
var uploadFileName;

function set_upload_param(up, filename, ret) {

    new_multipart_params = {
        'key': objectKey,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid,
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'callback': callbackbody,
        'x:orderid':orderId,
        'x:objectkey':objectKey,
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

function get_signature() {
    // 可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000;
    if (expire < now + 3) {
        var serverUrl = base + '/oss/getSign';
        $.ajax({
            type: 'post',
            url: serverUrl,
            data: {id: orderId,code:orderCode,fileName:uploadFileName},
            dataType: 'json',
            async: false,
            success: function (result) {
                host = result.host;
                policyBase64 = result.policy;
                accessid = result.accessid;
                signature = result.signature;
                expire = parseInt(result.expire);
                callbackbody = result.callback;
                objectKey = result.objectKey;
            }
        });
        return true;
    }
    return false;
};

var uploader = new plupload.Uploader({
    runtimes: 'html5,flash,silverlight,html4',
    browse_button: 'selectfiles',
    container: document.getElementById('container'),
    flash_swf_url: 'lib/plupload-2.1.2/js/Moxie.swf',
    silverlight_xap_url: 'lib/plupload-2.1.2/js/Moxie.xap',
    url: 'http://oss.aliyuncs.com',

    init: {
        PostInit: function () {
            document.getElementById('ossfile').innerHTML = '';
            document.getElementById('postfiles').onclick = function (obj) {
                orderId = $(this).attr("data-id");
                orderCode = $(this).attr("data-code");
                set_upload_param(uploader, '', false);
                return false;
            };
        },

        FilesAdded: function (up, files) {
            if(uploader.files.length>1) { // 最多上传3张图
                alert('只能上传一个文件!');
                return;
            }
            plupload.each(files, function (file) {
                uploadFileName = file.name
                document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                    + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                    + '</div>';
            });
        },

        BeforeUpload: function (up, file) {
            get_signature();
            set_upload_param(up, file.name, true);
        },

        UploadProgress: function (up, file) {
            var d = document.getElementById(file.id);
            d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
            var progBar = prog.getElementsByTagName('div')[0]
            progBar.style.width = 2 * file.percent + 'px';
            progBar.setAttribute('aria-valuenow', file.percent);
        },

        FileUploaded: function (up, file, info) {
            if (info.status == 200) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success! file name = ' + uploadFileName;
            }
            else {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
              window.location.href = "http://gh.hsgene.com/order/list.jsp";
        },

        Error: function (up, err) {
            document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
        }
    }
});

uploader.init();
