<%--
  Created by IntelliJ IDEA.
  User: Wangyan
  Date: 2018/03/01
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

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
    <link href="/static/admin/vendors/google-code-prettify/bin/prettify.min.css" rel="stylesheet">
    <!-- Select2 -->
    <link href="/static/admin/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
    <!-- Switchery -->
    <link href="/static/admin/vendors/switchery/dist/switchery.min.css" rel="stylesheet">
    <!-- starrr -->
    <link href="/static/admin/vendors/starrr/dist/starrr.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
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
                        <h3>RISE新規</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>RISE管理
                                    <small>新規</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                                <form:form modelAttribute="riseRegistForm"
                                           action="${pageContext.request.contextPath}/rise/insert/" id="demo-form2"
                                           class="form-horizontal form-label-left">

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">アーティスト名 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="name1"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-user form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>

                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">出生名</label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="name2"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-user-md form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">生年月日 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input type="date" path="birthday"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-calendar form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>

                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">出身地
                                        </label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="hometown"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-home form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">事務所 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="office"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-building form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>

                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">肩書 <span
                                                class="required">*</span></label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="honor"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-graduation-cap form-control-feedback left"
                                                  aria-hidden="true" style="width: 40px"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">TOPに表示 <span
                                                class="required">*</span>
                                        </label>

                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="sortScore"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-eye form-control-feedback left" aria-hidden="true"
                                                  style="width: 40px"></span>
                                        </div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">RISEカテゴリ <span
                                                class="required">*</span></label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input path="category"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-star-half-o form-control-feedback left"
                                                  aria-hidden="true" style="width: 40px"></span>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">動画 Url<span
                                                class="required"></span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 form-group has-feedback">
                                            <form:input path="videoUrl1"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                            <span class="fa fa-video-camera form-control-feedback left"
                                                  aria-hidden="true" style="width: 40px"></span>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">topタイトル <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="topTitle"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">top画像<span
                                                class="required"></span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="topPicUrl"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">アバター画像 <span
                                                class="required"></span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="topPicUrl4"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">紹介詳細タイトル - 1 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="title1"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">紹介詳細 - 1 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12">
                                            <form:input path="desc1"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">画像 - 1<span
                                                class="required">*</span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="picUrl1"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">紹介詳細タイトル - 2 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="title2"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">紹介詳細 - 2 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12">
                                            <form:input path="desc2"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">画像 - 2<span
                                                class="required">*</span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="picUrl2"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">紹介詳細タイトル - 3 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="title3"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">紹介詳細 - 3 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-7 col-sm-7 col-xs-12">
                                            <form:input path="desc3"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">画像 - 3<span
                                                class="required">*</span></label>
                                        <div class="col-md-7 col-sm-7 col-xs-12 ">
                                            <form:input path="picUrl3"
                                                        class="form-control col-md-3 col-xs-12 has-feedback-left"/>
                                        </div>
                                    </div>

                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                            <button class="btn btn-primary" type="button"><span
                                                    class="glyphicon glyphicon-remove" aria-hidden="true"></span>キャンセル
                                            </button>
                                            <button type="submit" class="btn btn-success"><span
                                                    class="glyphicon glyphicon-ok" aria-hidden="true"></span>登録
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

<!-- jQuery -->
<script src="/static/admin/vendors/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/static/admin/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="/static/admin/vendors/fastclick/lib/fastclick.js"></script>
<!-- NProgress -->
<script src="/static/admin/vendors/nprogress/nprogress.js"></script>
<!-- bootstrap-progressbar -->
<script src="/static/admin/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- iCheck -->
<script src="/static/admin/vendors/iCheck/icheck.min.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="/static/admin/vendors/moment/min/moment.min.js"></script>
<script src="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- bootstrap-wysiwyg -->
<script src="/static/admin/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="/static/admin/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="/static/admin/vendors/google-code-prettify/src/prettify.js"></script>
<!-- jQuery Tags Input -->
<script src="/static/admin/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
<!-- Switchery -->
<script src="/static/admin/vendors/switchery/dist/switchery.min.js"></script>
<!-- Select2 -->
<script src="/static/admin/vendors/select2/dist/js/select2.full.min.js"></script>
<!-- Parsley -->
<script src="/static/admin/vendors/parsleyjs/dist/parsley.min.js"></script>
<!-- Autosize -->
<script src="/static/admin/vendors/autosize/dist/autosize.min.js"></script>
<!-- jQuery autocomplete -->
<script src="/static/admin/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
<!-- starrr -->
<script src="/static/admin/vendors/starrr/dist/starrr.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>

</body>
</html>
