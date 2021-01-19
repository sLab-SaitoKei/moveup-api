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
    <link href="/static/admin/vendors/bootstrap-fileinput/css/fileinput.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">

    <!-- PNotify -->
    <link href="/static/admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">

    <style type="text/css">
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
                        <h3>TOP管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>TOP <small>検索結果</small> </h2>
                                <div class="clearfix"></div>
                            </div>
                            <br/>
                            <div class="x_content">
                                <form:form modelAttribute="topForm"
                                           action="${pageContext.request.contextPath}/admin/top/edit/" method="post"
                                           enctype="multipart/form-data" class="form-horizontal form-label-left" onsubmit="return onUpdate()">
                                    <form:input type="hidden" id="picUrl1" path="picUrl1" value="${topForm.picUrl1}"/>
                                    <form:input type="hidden" id="picUrl2" path="picUrl2" value="${topForm.picUrl2}"/>
                                    <form:input type="hidden" id="url3" path="url3" value="${topForm.url3}"/>
                                    <form:input type="hidden" id="url4" path="url4" value="${topForm.url4}"/>
                                    <form:input type="hidden" id="url5" path="url5" value="${topForm.url5}"/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" align="center">
                                        <img src="/static/img/top_admin.png" style="height: auto;width: auto;">
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-top: 30px;">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-4 col-sm-6 col-xs-12">
                                            <div id="pic1-1">
                                                <div class="item form-group">
                                                    <label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像1
                                                        <span class="required"></span></label>
                                                    <div class="col-md-3 col-sm-3 col-xs-12">解像度: 1158 X 535</div>
                                                    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <div class="col-md-4 col-sm-3 col-xs-12">
                                                            <form:select path="itemType1" id="itemType" class="form-control col-md-3 col-xs-12"
                                                                         name="" required="" cssStyle="margin-top: 18px;">
                                                                <form:option name="itemType1" value="0">--</form:option>
                                                                <form:option name="itemType1" value="1">LOGIN</form:option>
                                                                <form:option name="itemType1" value="2">NEWS</form:option>
                                                                <form:option name="itemType1" value="3">EVENT</form:option>
                                                                <form:option name="itemType1" value="4">FREEPAPER</form:option>
                                                                <form:option name="itemType1" value="6">SHOP</form:option>
                                                                <form:option name="itemType1" value="7">PLACE</form:option>
                                                                <form:option name="itemType1" value="9">RECRUIT</form:option>
                                                                <form:option name="itemType1" value="8">CORPORATE INFO</form:option>
                                                                <form:option name="itemType1" value="10">GOODS</form:option>
                                                                <form:option name="itemType1" value="11">TV</form:option>
                                                                <form:option name="itemType1" value="12">JMUW</form:option>
                                                                <form:option name="itemType1" value="13">JMUW WEB</form:option>
                                                                <form:option name="itemType1" value="14">その他</form:option>
                                                            </form:select>
                                                        </div>
                                                        <div class="col-md-8 col-sm-3 col-xs-12">
                                                            <span>UUIDもしくはリンク先</span>
                                                            <form:input type="text" id="linkUrl1" name="linkUrl1" path="linkUrl1"
                                                                        class="form-control col-md-3 col-xs-12"/>
                                                        </div>
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
                                            <div >
                                                <div class="item form-group">
                                                    <div class="col-md-12 col-sm-12 col-xs-12">
                                                        <label class="col-md-2 col-sm-2 col-xs-12" for="picFile1">画像2
                                                            <span class="required"></span></label>
                                                        <label class="control-label col-md-2 col-sm-3 col-xs-12"
                                                               style="margin-top:-8px;">タイプ </label>

                                                        <div class="col-md-4 col-sm-2 col-xs-12"
                                                             style="margin-top: -7px;">
                                                            <div class="radio">
                                                                <label>
                                                                    <form:radiobutton path="type2"
                                                                                      value="1" id="optionsRadios1"
                                                                                      name="Radios1"/> 画像
                                                                </label>
                                                                <label>
                                                                    <form:radiobutton path="type2" value="2"
                                                                                      id="optionsRadios2"
                                                                                      name="Radios1"/> 動画
                                                                </label>
                                                            </div>
                                                        </div>

                                                        <%--<div class="col-md-4 col-sm-2 col-xs-12"--%>
                                                             <%--style="margin-top: -7px;">--%>
                                                            <%--<div class="radio">--%>
                                                                <%--<label>--%>
                                                                    <%--<form:radiobutton path="type2"--%>
                                                                                      <%--value="1" id="optionsRadios1"--%>
                                                                                      <%--name="Radios1"/> 画像--%>
                                                                <%--</label>--%>
                                                                <%--<label>--%>
                                                                    <%--<form:radiobutton path="type2" checked="checked"--%>
                                                                                      <%--value="2" id="optionsRadios2"--%>
                                                                                      <%--name="Radios1"/> 動画--%>
                                                                <%--</label>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                        <div class="col-md-4 col-sm-3 col-xs-12">解像度: 1158 X 535</div>
                                                    </div>
                                                    <div id ="pic2-1-1" class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                        <div class="col-md-4 col-sm-3 col-xs-12">
                                                            <form:select path="itemType2" id="itemType" class="form-control col-md-3 col-xs-12"
                                                                         name="" required="" cssStyle="margin-top: 18px;">
                                                                <form:option name="itemType1" value="0">--</form:option>
                                                                <form:option name="itemType1" value="1">LOGIN</form:option>
                                                                <form:option name="itemType1" value="2">NEWS</form:option>
                                                                <form:option name="itemType1" value="3">EVENT</form:option>
                                                                <form:option name="itemType1" value="4">FREEPAPER</form:option>
                                                                <form:option name="itemType1" value="6">SHOP</form:option>
                                                                <form:option name="itemType1" value="7">PLACE</form:option>
                                                                <form:option name="itemType1" value="9">RECRUIT</form:option>
                                                                <form:option name="itemType1" value="8">CORPORATE INFO</form:option>
                                                                <form:option name="itemType1" value="10">GOODS</form:option>
                                                                <form:option name="itemType1" value="11">TV</form:option>
                                                                <form:option name="itemType1" value="12">JMUW</form:option>
                                                                <form:option name="itemType1" value="13">JMUW WEB</form:option>
                                                                <form:option name="itemType1" value="14">その他</form:option>
                                                            </form:select>
                                                        </div>
                                                        <div class="col-md-8 col-sm-3 col-xs-12">
                                                            <span>UUIDもしくはリンク先</span>
                                                            <form:input type="text" id="linkUrl2" name="linkUrl2" path="linkUrl2"
                                                                        class="form-control col-md-3 col-xs-12"/>
                                                        </div>
                                                    </div>
                                                    <div id="pic2-1" class="col-md-12 col-sm-3 col-xs-12 " style="">
                                                        <form:input type="file" id="picFile2" name="picFile2"
                                                                    path="picFile2"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                    </div>
                                                    <div id="pic2-2" class="col-md-12 col-sm-7 col-xs-12 " style="display: none">
                                                        <span>動画URL</span>
                                                        <form:input type="text" id="videoUrl2" name="videoUrl2"
                                                                    path="videoUrl2"
                                                                    class="form-control col-md-3 col-xs-12"/>
                                                        <div style="padding-top: 50px;">
                                                            <p>YOUTUBE URL パラメータの設定</p>
                                                            <p>音を消す自動再生する : ?rel=0&mute=1;autoplay=1</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <%--<div id="pic2-2">--%>
                                                <%--<div class="item form-group">--%>
                                                    <%--<label class="col-md-9 col-sm-9 col-xs-12" for="picFile1">画像2--%>
                                                        <%--<span class="required"></span></label>--%>
                                                    <%--<label class="control-label col-md-2 col-sm-3 col-xs-12"--%>
                                                           <%--style="margin-top:-8px;">タイプ </label>--%>
                                                    <%--<div class="col-md-4 col-sm-2 col-xs-12"--%>
                                                         <%--style="margin-top: -7px;">--%>
                                                        <%--<div class="radio">--%>
                                                            <%--<label>--%>
                                                                <%--<form:radiobutton path="type2" value="1"--%>
                                                                                  <%--id="optionsRadios1"--%>
                                                                                  <%--name="Radios1"/> 画像--%>
                                                            <%--</label>--%>
                                                            <%--<label>--%>
                                                                <%--<form:radiobutton path="type2" checked="checked"--%>
                                                                                  <%--value="2" id="optionsRadios2"--%>
                                                                                  <%--name="Radios1"/> 動画--%>
                                                            <%--</label>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-md-3 col-sm-3 col-xs-12">解像度: 900 X 440</div>--%>
                                                    <%--<div class="col-md-12 col-sm-7 col-xs-12 ">--%>
                                                        <%--<span>動画URL</span>--%>
                                                        <%--<form:input type="text" id="videoUrl2" name="videoUrl2"--%>
                                                                    <%--path="videoUrl2"--%>
                                                                    <%--class="form-control col-md-3 col-xs-12"/>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="col-md-12 col-sm-12 col-xs-12 " style="padding-top: 30px;">
                                        <div class="col-md-4 col-sm-4 col-xs-12 ">
                                            <label class="col-md-9 col-sm-2 col-xs-12" for="picFile1">画像3 <span
                                                    class="required"></span></label>
                                            <div class="col-md-3 col-sm-3 col-xs-12">解像度: 1158 X 535</div>
                                            <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                <div class="col-md-4 col-sm-3 col-xs-12">
                                                    <form:select path="itemType3" id="itemType" class="form-control col-md-3 col-xs-12"
                                                                 name="" required="" cssStyle="margin-top: 18px;">
                                                        <form:option name="itemType1" value="0">--</form:option>
                                                        <form:option name="itemType1" value="1">LOGIN</form:option>
                                                        <form:option name="itemType1" value="2">NEWS</form:option>
                                                        <form:option name="itemType1" value="3">EVENT</form:option>
                                                        <form:option name="itemType1" value="4">FREEPAPER</form:option>
                                                        <form:option name="itemType1" value="6">SHOP</form:option>
                                                        <form:option name="itemType1" value="7">PLACE</form:option>
                                                        <form:option name="itemType1" value="9">RECRUIT</form:option>
                                                        <form:option name="itemType1" value="8">CORPORATE INFO</form:option>
                                                        <form:option name="itemType1" value="10">GOODS</form:option>
                                                        <form:option name="itemType1" value="11">TV</form:option>
                                                        <form:option name="itemType1" value="12">JMUW</form:option>
                                                        <form:option name="itemType1" value="13">JMUW WEB</form:option>
                                                        <form:option name="itemType1" value="14">その他</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-8 col-sm-3 col-xs-12">
                                                    <span>UUIDもしくはリンク先</span>
                                                    <form:input type="text" id="linkUrl3" name="linkUrl3" path="linkUrl3"
                                                                class="form-control col-md-3 col-xs-12"/>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile3" name="picFile3" path="picFile3"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-4 col-xs-12 ">
                                            <label class="col-md-9 col-sm-2 col-xs-12" for="picFile1">画像4 <span
                                                    class="required"></span></label>
                                            <div class="col-md-3 col-sm-3 col-xs-12">解像度:1158 X 535</div>
                                            <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                <div class="col-md-4 col-sm-3 col-xs-12">
                                                    <form:select path="itemType4" id="itemType" class="form-control col-md-3 col-xs-12"
                                                                 name="" required="" cssStyle="margin-top: 18px;">
                                                        <form:option name="itemType1" value="0">--</form:option>
                                                        <form:option name="itemType1" value="1">LOGIN</form:option>
                                                        <form:option name="itemType1" value="2">NEWS</form:option>
                                                        <form:option name="itemType1" value="3">EVENT</form:option>
                                                        <form:option name="itemType1" value="4">FREEPAPER</form:option>
                                                        <form:option name="itemType1" value="6">SHOP</form:option>
                                                        <form:option name="itemType1" value="7">PLACE</form:option>
                                                        <form:option name="itemType1" value="9">RECRUIT</form:option>
                                                        <form:option name="itemType1" value="8">CORPORATE INFO</form:option>
                                                        <form:option name="itemType1" value="10">GOODS</form:option>
                                                        <form:option name="itemType1" value="11">TV</form:option>
                                                        <form:option name="itemType1" value="12">JMUW</form:option>
                                                        <form:option name="itemType1" value="13">JMUW WEB</form:option>
                                                        <form:option name="itemType1" value="14">その他</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-8 col-sm-3 col-xs-12">
                                                    <span>UUIDもしくはリンク先</span>
                                                    <form:input type="text" id="linkUrl4" name="linkUrl4" path="linkUrl4"
                                                                class="form-control col-md-3 col-xs-12"/>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile4" name="picFile4" path="picFile4"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-4 col-xs-12 ">
                                            <label class="col-md-9 col-sm-2 col-xs-12" for="picFile1">画像5 <span
                                                    class="required"></span></label>
                                            <div class="col-md-3 col-sm-3 col-xs-12">解像度: 1158 X 535</div>
                                            <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px">
                                                <div class="col-md-4 col-sm-3 col-xs-12">
                                                    <form:select path="itemType5" id="itemType" class="form-control col-md-3 col-xs-12"
                                                                 name="" required="" cssStyle="margin-top: 18px;">
                                                        <form:option name="itemType1" value="0">--</form:option>
                                                        <form:option name="itemType1" value="1">LOGIN</form:option>
                                                        <form:option name="itemType1" value="2">NEWS</form:option>
                                                        <form:option name="itemType1" value="3">EVENT</form:option>
                                                        <form:option name="itemType1" value="4">FREEPAPER</form:option>
                                                        <form:option name="itemType1" value="6">SHOP</form:option>
                                                        <form:option name="itemType1" value="7">PLACE</form:option>
                                                        <form:option name="itemType1" value="9">RECRUIT</form:option>
                                                        <form:option name="itemType1" value="8">CORPORATE INFO</form:option>
                                                        <form:option name="itemType1" value="10">GOODS</form:option>
                                                        <form:option name="itemType1" value="11">TV</form:option>
                                                        <form:option name="itemType1" value="12">JMUW</form:option>
                                                        <form:option name="itemType1" value="13">JMUW WEB</form:option>
                                                        <form:option name="itemType1" value="14">その他</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-8 col-sm-3 col-xs-12">
                                                    <span>UUIDもしくはリンク先</span>
                                                    <form:input type="text" id="linkUrl5" name="linkUrl5" path="linkUrl5"
                                                                class="form-control col-md-3 col-xs-12"/>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile5" name="picFile5" path="picFile5"
                                                            class="form-control col-md-3 col-xs-12"/>
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
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>

<script>
    $('#picFile1').fileinput({
        'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1158,"maxImageWidth":1158,"minImageHeight":535,"maxImageHeight":535
        <c:if test="${topForm.picUrl1 != null && topForm.picUrl1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${topForm.picUrl1}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        //800*370
        'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1158,"maxImageWidth":1158,"minImageHeight":535,"maxImageHeight":535
        <c:if test="${topForm.picUrl2 != null && topForm.picUrl2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${topForm.picUrl2}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1158,"maxImageWidth":1158,"minImageHeight":535,"maxImageHeight":535
        <c:if test="${topForm.url3 != null && topForm.url3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${topForm.url3}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1158,"maxImageWidth":1158,"minImageHeight":535,"maxImageHeight":535
        <c:if test="${topForm.url4 != null && topForm.url4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${topForm.url4}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1158,"maxImageWidth":1158,"minImageHeight":535,"maxImageHeight":535
        <c:if test="${topForm.url5 != null && topForm.url5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${topForm.url5}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });

    function onUpdate() {
        $('#topLoading').show();
        return true;
    }

    function remove1() {
        document.getElementById('picUrl1').value= "";
    }
    function remove2() {
        document.getElementById('picUrl2').value= "";
    }
    function remove3() {
        document.getElementById('url3').value= "";
    }
    function remove4() {
        document.getElementById('url4').value= "";
    }
    function remove5() {
        document.getElementById('url5').value= "";
    }

</script>
<script>
    $(function () {
        <%--var value1 = ${topForm.type1};--%>
        <%--if (value1 == 1) {--%>
            <%--$("#pic1-1").show();--%>
            <%--$("#pic1-2").hide();--%>
        <%--} else {--%>
            <%--$("#pic1-2").show();--%>
            <%--$("#pic1-1").hide();--%>
        <%--}--%>
        var value1 = ${topForm.type2};
        if (value1 == 1) {
            $("#pic2-1").show();
            $("#pic2-2").hide();
            $("#pic2-1-1").show();
        } else {
            $("#pic2-2").show();
            $("#pic2-1").hide();
            $("#pic2-1-1").hide();
        }
        $("input[name='type1']").click(function () {
            if ($(this).val() == 1) {
                $("#pic1-1").show();
                $("#pic1-2").hide();
            } else {
                $("#pic1-2").show();
                $("#pic1-1").hide();
            }
        });
        $("input[name='type2']").click(function () {
            if ($(this).val() == 1) {
                $("#pic2-1").show();
                $("#pic2-2").hide();
                $("#pic2-1-1").show();
            } else {
                $("#pic2-2").show();
                $("#pic2-1").hide();
                $("#pic2-1-1").hide();
            }
        });
    });
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
</body>
</html>