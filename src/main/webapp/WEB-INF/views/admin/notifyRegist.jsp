<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="images/favicon.ico" type="image/ico"/>

    <title>JapanMoveUpWestAdmin</title>

    <!-- Bootstrap -->
    <link href="/static/admin/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/static/admin/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="/static/admin/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="/static/admin/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- bootstrap-wysiwyg -->
    <link href="/static/admin//vendors/google-code-prettify/bin/prettify.min.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="/static/admin/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="/static/admin/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">

    <style>
        .validatorError {
            color: red;
        }
        .file-caption .form-control .kv-fileinput-caption .icon-visible{
            display: none;
        }
        .input-group-btn .input-group-append{
            text-align: right;
        }
        .btn .btn-primary .btn-file{
            float: right;
            border-radius: 3px;
        }
        .form-group .btn{
            margin-bottom: 0px;
        }
        .btn-file{
            float: right;
        }
        .input-group .form-control, .input-group-addon, .input-group-btn{

        }
        .table-condensed{
            text-align: center;
        }
    </style>
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <!-- sidebar menu -->
        <jsp:include page="sidebar.jsp"/>
        <!-- /sidebar menu -->

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <span style="color: silver">Admin管理者&nbsp;&nbsp;</span>岡山 太郎
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="javascript:;">Help</a></li>
                                <li><a href="login.html"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                            </ul>
                        </li>


                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3>ニュース管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>送信通知
                                    <small>新規登録</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                                <form:form modelAttribute="notifyForm" id="userForm"
                                           class="form-horizontal form-label-left" action="${pageContext.request.contextPath}/admin/notify/regist/">

                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">ユーザータイプ <span
                                            class="required">*</span></label>
                                    <div class="col-md-3 col-sm-6 col-xs-12">
                                        <div class="radio">
                                            <label>
                                                <form:radiobutton path="userType" checked="" value="1"
                                                                  id="optionsRadios1" name="sortScore"/> 管理者
                                            </label>
                                            <label>
                                                <form:radiobutton path="userType" value="2" id="optionsRadios2"
                                                                  name="sortScore"/> ユーザー
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">送信方式 <span
                                            class="">*</span>
                                    </label>
                                    <div class="col-md-3 col-sm-6 col-xs-12">
                                        <div class="radio">
                                            <label>
                                                <form:radiobutton path="push" value="1" onchange="onPushChenged(1)" checked="checked"/>おしらせ
                                            </label>
                                            <label>
                                                <form:radiobutton path="push" value="2" onchange="onPushChenged(2)"/>プッシュ
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">送信対象 <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <div class="radio">
                                            <label id="notify_all_member">
                                                <form:radiobutton path="sendType" value="1" onchange="onSendTypeChanged(1)"/> 全員
                                            </label>
                                            <label id="notify_app_member">
                                                <form:radiobutton path="sendType" value="3" onchange="onSendTypeChanged(3)"/> APPのみ
                                            </label>
                                            <label id="notify_app_member2" style="display: none;">
                                                <form:radiobutton path="sendType" value="3" onchange="onSendTypeChanged(3)"/> APP全員
                                            </label>
                                            <label>
                                                <form:radiobutton path="sendType" value="2" onchange="onSendTypeChanged(2)"/> 指定
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="item form-group" style="display: none;" id="user_id_container">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">ユーザーUUID <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-2 col-sm-6 col-xs-12">
                                        <form:input path="userList" id="userList" class="form-control col-md-7 col-xs-12"
                                                    required="" type="text"/>
                                    </div>
                                </div>

                                <div class="item form-group" style="display: none" id="push_time_container">

                                    <div class="item form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">ターゲットシステム <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-6 col-xs-12">
                                            <div class="radio">
                                                <label>
                                                    <input type="checkbox" value="1" name="plat" /> Android
                                                </label>
                                                <label>
                                                    <input type="checkbox" value="2" name="plat" /> IOS
                                                </label>
                                                <input type="hidden" id="platform" name="platform" />
                                            </div>
                                        </div>
                                    </div>

                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">PUSH時間 <span
                                            class=""></span>
                                    </label>
                                    <div class="col-md-2 col-sm-6 col-xs-12">
                                        <div class="form-group">
                                            <div class='input-group date' id='pushTimeDatepicker'>
                                                <form:input id="pushTime" path="pushTime"
                                                            type='text' class="form-control"
                                                            value="${recruitForm.pushTime}"/>
                                                <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="item form-group" style="display: none" id="item">
                                    <label class="control-label col-md-3 col-sm-1 col-xs-12" for="">アイテム<span
                                            class="required"></span>
                                    </label>
                                    <div class="col-md-2 col-sm-1 col-xs-12 " onclick="selectItem()" >
                                        <form:select  path="itemId" class="form-control required" id="itemId">
                                            <form:option value="0">--</form:option>
                                            <form:option value="10">ニュース</form:option>
                                            <form:option value="2">イベント</form:option>
                                            <form:option value="6">フリーペーパ</form:option>
                                            <form:option value="3">TV</form:option>
                                            <form:option value="5">商品</form:option>
                                            <form:option value="1">店舗</form:option>
                                            <form:option value="4">求人</form:option>
                                            <form:option value="11">プレス</form:option>
                                            <form:option value="12">コーポレートインフォ</form:option>
                                            <form:option value="13">その他</form:option>
                                        </form:select>
                                    </div>
                                    <div class="item form-group" id="itemUrl"  style="display: none">
                                        <label class="control-label col-md-2 col-sm-3 col-xs-12" for="">UUIDもしくはリンク先 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <form:input path="itemUrl" type="text" id="itemUrl" name="itemUrl" required="required"
                                                        class="form-control col-md-7 col-xs-12"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="item form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">標題 <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <form:input path="title" type="text" id="title" name="" required="required"
                                                    class="form-control col-md-7 col-xs-12"/>
                                    </div>
                                </div>
                                <div class="item form-group" id="notifyContent" >
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">内容 <span
                                            class="required"></span>
                                    </label>
                                    <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                        <%--<form:textarea path="content" id="content" required="required" name="content"--%>
                                                       <%--class="form-control col-md-7 col-xs-12" cssStyle="height: 300px;"></form:textarea>--%>
                                    <%--</div>--%>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <form:input path="content" id="contentEditor" class="form-control col-md-7 col-xs-12"
                                                    name="" required="required"  cssStyle="height: 200px;" type = "hidden"/>
                                        <div class="x_content">
                                            <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor">
                                                <div class="btn-group">
                                                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="fa fa-font"></i><b class="caret"></b></a>
                                                    <ul class="dropdown-menu">
                                                    </ul>
                                                </div>

                                                <div class="btn-group">
                                                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="fa fa-text-height"></i>&nbsp;<b class="caret"></b></a>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a data-edit="fontSize 5">
                                                                <p style="font-size:17px">Huge</p>
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a data-edit="fontSize 3">
                                                                <p style="font-size:14px">Normal</p>
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a data-edit="fontSize 1">
                                                                <p style="font-size:11px">Small</p>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>

                                                <div class="btn-group">
                                                    <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)" id="btn-bold"><i class="fa fa-bold" ></i></a>
                                                    <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="fa fa-italic"></i></a>
                                                    <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="fa fa-strikethrough"></i></a>
                                                    <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="fa fa-underline"></i></a>
                                                </div>

                                                <div class="btn-group">
                                                    <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="fa fa-list-ul"></i></a>
                                                    <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="fa fa-list-ol"></i></a>
                                                    <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="fa fa-dedent"></i></a>
                                                    <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="fa fa-indent"></i></a>
                                                </div>

                                                <div class="btn-group">
                                                    <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="fa fa-align-left"></i></a>
                                                    <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="fa fa-align-center"></i></a>
                                                    <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="fa fa-align-right"></i></a>
                                                    <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="fa fa-align-justify"></i></a>
                                                </div>

                                                <div class="btn-group">
                                                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i class="fa fa-link"></i></a>
                                                    <div class="dropdown-menu input-append">
                                                        <input class="span2" placeholder="URL" type="text" data-edit="createLink" />
                                                        <button class="btn" type="button">Add</button>
                                                    </div>
                                                    <a class="btn" data-edit="tel" title="Telephone"><i class="fa fa-phone"></i></a>
                                                    <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="fa fa-cut"></i></a>
                                                </div>

                                                    <%--<div class="btn-group">--%>
                                                    <%--<a class="btn" title="Insert picture (or just drag & drop)" id="pictureBtn"><i class="fa fa-picture-o"></i></a>--%>
                                                    <%--<input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage" />--%>
                                                    <%--</div>--%>

                                                <div class="btn-group">
                                                    <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="fa fa-undo"></i></a>
                                                    <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="fa fa-repeat"></i></a>
                                                </div>
                                            </div>
                                            <div id="editor" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                        </div>
                                    </div>
                                </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-3">
                                    <a class="btn btn-primary" href="/admin/notify/list/" style="width: 100px;">キャンセル</a>

                                    <button id="send" type="submit" class="btn btn-success" style="width: 100px;">登録
                                    </button>
                                </div>
                            </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /page content -->

    <!-- footer content -->
    <footer>
        <div class="pull-right">
            Japan Move Up West Admin
        </div>
        <div class="clearfix"></div>
    </footer>
    <!-- /footer content -->
</div>
</div>
<div id="newsLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>
<!-- jQuery -->
<script src="/static/admin/vendors/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/static/admin/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- jquery validator -->
<script src="/static/admin/vendors/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
<script src="/static/admin/vendors/jquery-validation-1.17.0/dist/localization/messages_ja.min.js"></script>
<!-- FastClick -->
<script src="/static/admin/vendors/fastclick/lib/fastclick.js"></script>
<!-- NProgress -->
<script src="/static/admin/vendors/nprogress/nprogress.js"></script>
<!-- Chart.js -->
<script src="/static/admin/vendors/Chart.js/dist/Chart.min.js"></script>
<!-- gauge.js -->
<script src="/static/admin/vendors/gauge.js/dist/gauge.min.js"></script>
<!-- bootstrap-progressbar -->
<script src="/static/admin/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- iCheck -->
<script src="/static/admin/vendors/iCheck/icheck.min.js"></script>
<!-- Skycons -->
<script src="/static/admin/vendors/skycons/skycons.js"></script>
<!-- Flot -->
<script src="/static/admin/vendors/Flot/jquery.flot.js"></script>
<script src="/static/admin/vendors/Flot/jquery.flot.pie.js"></script>
<script src="/static/admin/vendors/Flot/jquery.flot.time.js"></script>
<script src="/static/admin/vendors/Flot/jquery.flot.stack.js"></script>
<script src="/static/admin/vendors/Flot/jquery.flot.resize.js"></script>
<!-- Flot plugins -->
<script src="/static/admin/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
<script src="/static/admin/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
<script src="/static/admin/vendors/flot.curvedlines/curvedLines.js"></script>
<!-- DateJS -->
<script src="/static/admin/vendors/DateJS/build/date.js"></script>
<!-- JQVMap -->
<script src="/static/admin/vendors/jqvmap/dist/jquery.vmap.js"></script>
<script src="/static/admin/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
<script src="/static/admin/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="/static/admin/vendors/moment/min/moment.min.js"></script>
<script src="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- bootstrap-datetimepicker -->
<script src="/static/admin/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/admin/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="/static/admin/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="/static/admin/vendors/google-code-prettify/src/prettify.js"></script>
<script src="/static/admin/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
<script src="/static/admin/vendors/switchery/dist/switchery.min.js"></script>
<script src="/static/admin/vendors/parsleyjs/dist/parsley.min.js"></script>
<script src="/static/admin/vendors/starrr/dist/starrr.js"></script>
<script src="/static/admin/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
<!-- bootstrap-wysiwyg -->
<script src="/static/admin/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="/static/admin/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="/static/admin/vendors/google-code-prettify/src/prettify.js"></script>
<script src="/static/admin/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
<script src="/static/admin/vendors/switchery/dist/switchery.min.js"></script>
<script src="/static/admin/vendors/parsleyjs/dist/parsley.min.js"></script>
<script src="/static/admin/vendors/starrr/dist/starrr.js"></script>
<script src="/static/admin/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>

<script src="/static/admin/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
<script src="/static/admin/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
<script>
    $('#pushTimeDatepicker').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss'
    });

    $("#send").click(function(){
        if($("input[name='plat']:checked").length != 1){
            $("#platform").val('3');
        }else{
            $.each($('input[name="plat"]:checked'),function(){
                $("#platform").val($(this).val());
            });

        }
    })

    function onPushChenged(type) {
        if(type == 2){
            $('#notify_all_member').hide();
            $('#notify_app_member').hide();
            $('#notify_app_member2').show();
            $('#push_time_container').show();
            $('#item').show();
        }else{
            $('#notify_all_member').show();
            $('#notify_app_member').show();
            $('#notify_app_member2').hide();
            $('#push_time_container').hide();
            $('#item').hide();
        }
    }

    function onSendTypeChanged(type) {
        if(type == 2){
            $('#user_id_container').show();
            $('#userList').attr('required', 'required');
        }else{
            $('#userList').removeAttr('required');
            $('#user_id_container').hide();
        }
    }

    function onSendTypeChanged(type) {
        if(type == 2){
            $('#user_id_container').show();
            $('#userList').attr('required', 'required');
        }else{
            $('#userList').removeAttr('required');
            $('#user_id_container').hide();
        }
    }
</script>

<script>
    var userForm = $("#userForm");
    userForm.validate({
        submitHandler: function(form) {
            $("#contentEditor").val($('#editor').html());
            $('#newsLoading').show();
            $(form).submit();
        },
        errorClass : "validatorError",
        rules : {
            itemId :{
                required: true,
            },
            title :{
                required: true,
                maxlength: 1024
            },
            itemUrl :{
                required: true,
            },
            content :{
                required: true,
                maxlength: 2048
            }
        }
    });
    $('#editor').html($("#contentEditor").val());
</script>

<script>
    function selectItem() {
        // var value = document.getElementById('adminShop').value;
        var value =$('#itemId option:selected') .val();//
        if(value != 0){
            document.getElementById('itemUrl').style.display = 'block';
            document.getElementById('notifyContent').style.display = 'none';
        }else {
            document.getElementById('itemUrl').style.display = 'none';
            document.getElementById('notifyContent').style.display = 'block';
        }
    }
</script>
<script>
    function initToolbarBootstrapBindings() {
        var fonts = ['San Francisco','Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
                'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
                'Times New Roman', 'Verdana'],
            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
        $.each(fonts, function (idx, fontName) {
            fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
        });
    };
    initToolbarBootstrapBindings();
</script>
</body>
</html>