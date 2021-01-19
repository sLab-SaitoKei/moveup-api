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
    <!-- bootstrap-fileinput -->
    <link href="/static/admin/vendors/bootstrap-fileinput/css/fileinput2.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
    <style>
        .file-preview-frame {
            float: none !important;
        }
        .kv-file-content {
            width: auto !important;
        }
        .file-footer-caption {
            margin-bottom: 0 !important;
        }
        .kv-file-remove {
            display: none;
        }
        .file-drag-handle {
            display: none;
        }
        .file-upload-indicator {
            display: none;
        }
        .validatorError {
            color: red;
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
                        <h3>顧客情報確認</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>

                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                               <form:form modelAttribute="roleFrom" id="" enctype="multipart/form-data"
                                          action="${pageContext.request.contextPath}/admin/role/update/" class="form-horizontal form-label-left" method="post">
                                   <form:input type="hidden" id="id" path="id" value="${roleFrom.role}"/>
                                   <form:input type="hidden" id="id" path="role" value="${roleFrom.role}"/>

                                   <div class="item form-group" id="" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">表示名<span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:input path="showName" id="showName" value="${roleFrom.showName}" class="form-control col-md-7 col-xs-12"--%>
                                                       <%--name="name" required="required" type="text"/>--%>
                                           <label class="form-control"style="border: none;"><c:out value="${roleFrom.showName}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">権限 <span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:select path="" id="privilege" value="${roleFrom.privilege}"--%>
                                                        <%--class="form-control col-md-7 col-xs-12" name="privilege"--%>
                                                        <%--required="required" type="text">--%>
                                               <%--<form:option value="0">権限なし</form:option>--%>
                                               <%--<form:option value="1">読み込み</form:option>--%>
                                               <%--<form:option value="2">書き込み</form:option>--%>
                                           <%--</form:select>--%>
                                           <c:if test="${roleFrom.privilege == 0}">
                                               <label class="form-control"style="border: none;">店舗</label>
                                           </c:if>
                                           <c:if test="${roleFrom.privilege == 1}">
                                               <label class="form-control"style="border: none;">読み込み</label>
                                           </c:if>
                                           <c:if test="${roleFrom.privilege == 2}">
                                               <label class="form-control"style="border: none;">書き込み</label>
                                           </c:if>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">ログイン名<span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:input path="loginName" id="loginName" value="${roleFrom.loginName}"--%>
                                                       <%--class="form-control col-md-7 col-xs-12"--%>
                                                       <%--name="" required="" type="text"/>--%>
                                           <label class="form-control"style="border: none;"><c:out value="${roleFrom.loginName}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">顧客タイプ <span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:select path="customerType" id="customerType" value="${roleFrom.customerType}" class="form-control col-md-7 col-xs-12"--%>
                                                        <%--name="customerType" required="" type="">--%>
                                               <%--<form:option value="0">--</form:option>--%>
                                               <%--<form:option value="1">店舗</form:option>--%>
                                               <%--<form:option value="2">ライズ</form:option>--%>
                                           <%--</form:select>--%>
                                           <c:if test="${roleFrom.customerType == 1}">
                                               <label class="form-control"style="border: none;">店舗</label>
                                           </c:if>
                                           <c:if test="${roleFrom.customerType == 2}">
                                               <label class="form-control"style="border: none;">ライズ</label>
                                           </c:if>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">パースワード:
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:input path="password" id="password"--%>
                                                       <%--class="form-control col-md-7 col-xs-12"--%>
                                                       <%--name="" required="" type="text"/>--%>
                                           <label class="form-control"style="border: none;"><c:out value="${roleFrom.password}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">店舗タイプ <span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:select path="fcType" id="fcType" value="${roleFrom.fcType}"--%>
                                                        <%--class="form-control col-md-7 col-xs-12" name="fcType"--%>
                                                        <%--required="" type="text">--%>
                                               <%--<form:option value="0">--</form:option>--%>
                                               <%--<form:option value="1">管理店</form:option>--%>
                                               <%--<form:option value="2">支店</form:option>--%>
                                           <%--</form:select>--%>
                                           <c:if test="${roleFrom.fcType == 1}">
                                               <label class="form-control"style="border: none;">管理店</label>
                                           </c:if>
                                           <c:if test="${roleFrom.fcType == 2}">
                                               <label class="form-control"style="border: none;">支店</label>
                                           </c:if>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">メールアドレス:
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:input path="mail" id="mail" value="${roleFrom.mail}"--%>
                                                       <%--class="form-control col-md-7 col-xs-12"--%>
                                                       <%--name="" required="" type="text"/>--%>
                                               <label class="form-control"style="border: none;"><c:out value="${roleFrom.mail}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">管理店ID <span
                                               class="required">:</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <%--<form:input path="fcAdminId" id="fcAdminId" value="${roleFrom.fcAdminId}"--%>
                                                       <%--class="form-control col-md-7 col-xs-12" name="fcAdminId"--%>
                                                       <%--required="" type="text"/>--%>
                                           <label class="form-control"style="border: none;"><c:out value="${roleFrom.fcAdminId}"></c:out></label>
                                       </div>
                                   </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-7">
                                    <a class="btn btn-primary" href="/admin/" style="width: 100px;">確認</a>
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
<div id="corporateLoading" style="display: none" class="loading">
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
<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min2.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>\
<!--shop Scripts -->
<script src="/static/admin/build/js/shopRegist.js"></script>
<script src="/static/admin/build/js/shopEdit.js"></script>
<script>
    $('#picFile1').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true, "minImageWidth":750,"maxImageWidth":750,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl1 != null && form.picUrl1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl1}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl2 != null && form.picUrl2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl2}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl3 != null && form.picUrl3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl3}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl4 != null && form.picUrl4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl4}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl5 != null && form.picUrl5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl5}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile6').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":360,"maxImageWidth":360,"minImageHeight":230,"maxImageHeight":230
        <c:if test="${form.picUrl6 != null && form.picUrl6 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl6}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });

    function onUpdate() {
        $('#corporateLoading').show();
        return true;
    }


    jQuery.validator.addMethod("stringMaxLength",function(value,element,param) {
        var length = value.length;
        for(var i=0; i< value.length; i++){
            if(value.charCodeAt(i) > 127){
                length++;
            }
        }
        return this.optional(element) || (length <= param);
    }, $.validator.format("{0}バット以内で入力してください。"));

    jQuery.validator.addMethod("telNo",function(value,element) {
        var tel = /^[\d-]*$/;
        return this.optional(element) || (tel.test(value));
    }, $.validator.format("有効な電話番号を入力してください。"));

    var $userForm = $("#userForm");
    $userForm.validate({
        submitHandler: function(form) {
            $('#corporateLoading').show();
            $(form).submit();
        },
        errorClass : "validatorError",
        rules : {
            name :{
                required: true,
                maxlength: 30
            },
            title :{
                required: true,
                maxlength: 30
            },
            excerpt :{
                required: true,
                maxlength: 255
            },
            videoUrl1 :{
                required: true,
                maxlength: 255,
                url: true
            },
            tel :{
                required: true,
                maxlength: 12,
                telNo: true
            },
            address :{
                required: true,
                maxlength: 255
            },
            desc1 :{
                required: true,
                maxlength: 1024
            },
            desc2 :{
                maxlength: 255
            },
            desc3 :{
                maxlength: 255
            },
            desc4 :{
                maxlength: 255
            },
            desc5 :{
                maxlength: 255
            },
            desc6 :{
                maxlength: 255
            },
            desc7 :{
                maxlength: 255
            },
            desc8 :{
                maxlength: 255
            },
            desc9 :{
                maxlength: 255
            },
            desc10 :{
                maxlength: 255
            },
            desc11 :{
                maxlength: 255
            }
        }
    });
</script>
</body>
</html>