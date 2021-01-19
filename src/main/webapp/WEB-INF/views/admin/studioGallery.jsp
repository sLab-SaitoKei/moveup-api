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
    <!-- PNotify -->
    <link href="/static/admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">

    <style type="text/css">
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
         .dark{
             display: none;
         }
         .alert-info{
             display: none;
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
                        <h3>GALLERY管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>GALLERY<small>検索結果</small> </h2>
                                <div class="clearfix"></div>
                            </div>
                            <br/>
                            <div class="x_content">
                                <form:form modelAttribute="studioGalleryForm"
                                           action="${pageContext.request.contextPath}/admin/studioGallery/edit/" method="post"
                                           enctype="multipart/form-data" class="form-horizontal form-label-left" onsubmit="return onUpdate()">
                                    <%--<form:input type="hidden" id="url1" path="url1" value="${studioGalleryForm.url1}"/>--%>
                                    <%--<form:input type="hidden" id="url2" path="url2" value="${studioGalleryForm.url2}"/>--%>
                                    <%--<form:input type="hidden" id="url3" path="url3" value="${studioGalleryForm.url3}"/>--%>
                                    <%--<form:input type="hidden" id="url4" path="url4" value="${studioGalleryForm.url4}"/>--%>
                                    <%--<form:input type="hidden" id="url5" path="url5" value="${studioGalleryForm.url5}"/>--%>
                                    <%--<form:input type="hidden" id="url6" path="url6" value="${studioGalleryForm.url6}"/>--%>
                                    <%--<form:input type="hidden" id="url7" path="url7" value="${studioGalleryForm.url7}"/>--%>
                                    <%--<form:input type="hidden" id="url8" path="url8" value="${studioGalleryForm.url8}"/>--%>
                                    <div class="col-md-12 col-sm-12 col-xs-12" align="center">
                                        <img src="/static/img/studio_gallery.png" style="height: auto;width: auto;">
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-top: 30px;">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic1-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像1
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url1" name="url1" path="url1"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile1" name="picFile1"
                                                                    path="picFile1"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic2-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像2
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url2" name="url2" path="url2"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile2" name="picFile2"
                                                                    path="picFile2"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-top: 30px;">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic3-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile3">画像3
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url3" name="url3" path="url3"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile3" name="picFile3"
                                                                    path="picFile3"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic4-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像4
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url4" name="url4" path="url4"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile4" name="picFile4"
                                                                    path="picFile4"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-top: 30px;">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic5-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像5
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url5" name="url5" path="url5"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile5" name="picFile5"
                                                                    path="picFile5"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic6-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像6
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url6" name="url6" path="url6"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile6" name="picFile6"
                                                                    path="picFile6"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-top: 30px;">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic7-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像7
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url7" name="url7" path="url7"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile7" name="picFile7"
                                                                    path="picFile7"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic8-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像8
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 275 X 205</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <span>リンク先</span>
                                                        <form:input type="text" id="url8" name="url8" path="url8"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div class="col-md-12 col-sm-3 col-xs-12 ">
                                                        <form:input type="file" id="picFile8" name="picFile8"
                                                                    path="picFile8"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12" style="padding-top: 50px;" align="center">
                                            <button type="submit" class="btn btn-success"
                                                    style="width: 200px;height: 40px;"><span
                                                    class="glyphicon glyphicon-ok" aria-hidden="true"></span>確定
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
<div id="topLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>

<!-- jQuery -->
<script src="/static/admin/vendors/jquery/dist/jquery.min.js"></script>
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
<!-- PNotify -->
<script src="/static/admin/vendors/pnotify/dist/pnotify.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.nonblock.js"></script>
<!-- bootstrap-datetimepicker -->
<script src="/static/admin/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>

<script>
    $('#picFile1').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url1 != null && studioGalleryForm.url1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url1}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url2 != null && studioGalleryForm.url2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url2}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url3 != null && studioGalleryForm.url3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url3}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url4 != null && studioGalleryForm.url4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url4}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url5 != null && studioGalleryForm.url5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url5}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile6').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url6 != null && studioGalleryForm.url6 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url6}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile7').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url7 != null && studioGalleryForm.url7 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url7}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile8').fileinput({
        'language':'ja', 'showClose': false, 'showRemove':true, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":900,"maxImageWidth":900,"minImageHeight":440,"maxImageHeight":440
        <c:if test="${studioGalleryForm.url8 != null && studioGalleryForm.url8 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${studioGalleryForm.url8}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });

    function onUpdate() {
        $('#topLoading').show();
        return true;
    }

    var errorCode = ${errorCode};

    if(errorCode == 1){
        new PNotify({
            title: 'エラーが発生しました',
            text: 'データの更新に失敗しました。',
            type: 'error',
            styling: 'bootstrap3'
        });
    }

</script>
<%--<script>--%>
    <%--$(function () {--%>
        <%--var value1 = ${topForm.type1};--%>
        <%--if (value1 == 1) {--%>
            <%--$("#pic1-1").show();--%>
            <%--$("#pic1-2").hide();--%>
        <%--} else {--%>
            <%--$("#pic1-2").show();--%>
            <%--$("#pic1-1").hide();--%>
        <%--}--%>
        <%--var value1 = ${topForm.type2};--%>
        <%--if (value1 == 1) {--%>
            <%--$("#pic2-1").show();--%>
            <%--$("#pic2-2").hide();--%>
        <%--} else {--%>
            <%--$("#pic2-2").show();--%>
            <%--$("#pic2-1").hide();--%>
        <%--}--%>
        <%--$("input[name='type1']").click(function () {--%>
            <%--if ($(this).val() == 1) {--%>
                <%--$("#pic1-1").show();--%>
                <%--$("#pic1-2").hide();--%>
            <%--} else {--%>
                <%--$("#pic1-2").show();--%>
                <%--$("#pic1-1").hide();--%>
            <%--}--%>
        <%--});--%>
        <%--$("input[name='type2']").click(function () {--%>
            <%--if ($(this).val() == 1) {--%>
                <%--$("#pic2-1").show();--%>
                <%--$("#pic2-2").hide();--%>
            <%--} else {--%>
                <%--$("#pic2-2").show();--%>
                <%--$("#pic2-1").hide();--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>


</body>
</html>