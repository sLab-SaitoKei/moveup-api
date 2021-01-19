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

    <!-- bootstrap-progressbar -->
    <link href="/static/admin/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="/static/admin/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <!-- Dropzone -->
    <link href="/static/admin/vendors/dropzone/dist/min/dropzone.min.css" rel="stylesheet">
    <!-- bootstrap-fileinput -->
    <link href="/static/admin/vendors/bootstrap-fileinput/css/fileinput.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
    <style>
        .krajee-default.file-preview-frame .kv-file-content{
            width: 135px;
            height: 125px;
        }
        .krajee-default.file-preview-frame .file-footer-caption{
            width: 135px;
        }
        .krajee-default.file-preview-frame .file-caption-info{
            width: 135px;
        }
        .krajee-default.file-preview-frame .file-size-info{
            width: 135px;
        }
        .kv-file-remove{
            display: none;
        }
        .file-drag-handle{
            display: none;
        }
        .file-footer-caption {
            margin-bottom: 0 !important;
        }
        .file-upload-indicator {
            display: none;
        }
        .validatorError {
            color: red;
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
                        <h3>TEAM2020管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>TEAM2020基本情報
                                    <small>新規登録</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                    <%--<div class=" form-group">--%>
                                        <%--<div class="col-md-12 col-sm-6 col-xs-12" style="margin-top: 20px;">--%>
                                            <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" style="text-align: right;margin-bottom: 20px;" for="">内容<span--%>
                                                    <%--class="required">*</span>--%>
                                            <%--</label>--%>
                                            <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                                <%--<form:form  modelAttribute="form" enctype="multipart/form-data" path="picUrlList" action="${pageContext.request.contextPath}/admin/freepaper/regist/" class="dropzone"></form:form>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <br/>
                                <br/>
                                <form:form modelAttribute="form" id="form" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/team2020/regist/"
                                           class="form-horizontal form-label-left">
                                <div class="col-md-8 col-sm-12 col-xs-12" style="padding-top: 90px;padding-left: 10px;">
                                    <div class="col-md-12" >

                                        <label class="control-label col-md-2 col-sm-3 col-xs-12" for=""  style="width: 150px;">名前<span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <form:input path="name" type="" id="name" name="" required="required"
                                                        class="form-control col-md-7 col-xs-12"/>
                                        </div>
                                        <label class="control-label col-md-2 col-sm-3 col-xs-12" for=""  style="width: 110px;">ふりかな<span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-6 col-xs-12"  style="padding-left: 31px;">
                                            <form:input path="pseudonym" type="" id="pseudonym" name="" required="required"
                                                        class="form-control col-md-7 col-xs-12"/>
                                        </div>
                                    </div>
                                    <div class="col-md-12" style="padding-top: 30px;"></div>

                                </div>
                                <div class="col-md-12" style="padding-top: 30px;"></div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="col-md-12 col-sm-12 col-xs-12">
                                        <label class="control-label col-md-2 col-sm-12 col-xs-12" style="width: 149px"  for="picFil">ファイル
                                            <span class="required">*</span></label>
                                        <div class="col-md-10 col-sm-7 col-xs-12 ">
                                            <div class="col-md-12 col-sm-3 col-xs-12">解像度: 1000 X 1500</div>
                                            <form:input type="file" id="picFil" path="picFil" required=""
                                                        class="form-control col-md-3 col-xs-12" multiple="multiple"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class=""></div>
                            <div class="form-group">
                                <div class="col-md-3"></div>
                                <div class="col-md-6">
                                    <a class="btn btn-primary" href="/admin/team2020/list/" style="width: 100px;">キャンセル</a>

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
<div id="team2020Loading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>

<!-- jQuery -->
<script src="/static/admin/vendors/jquery/dist/jquery.min.js"></script>
<!-- jquery validator -->
<script src="/static/admin/vendors/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
<script src="/static/admin/vendors/jquery-validation-1.17.0/dist/localization/messages_ja.min.js"></script>
<!-- Bootstrap -->
<script src="/static/admin/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
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

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
<!-- Dropzone.js -->
<script src="/static/admin/vendors/dropzone/dist/min/dropzone.min.js"></script>
<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min2.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>

<script>
    $('#picFil').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any',"minImageWidth":1000,"maxImageWidth":1000,"minImageHeight":1500,"maxImageHeight":1500
    })

    function onRegist() {
        $('#team2020Loading').show();
        return true;
    }

    var $userForm = $("#userForm");
    $userForm.validate({
        submitHandler: function(form) {
            $('#team2020Loading').show();
            $(form).submit();
        },
        errorClass : "validatorError",
        rules : {
            date :{
                required: true
            },
            name :{
                required: true,
                maxlength: 255
            },
            picFil :{
                required: true
            }
        }
    });
</script>
</body>
</html>