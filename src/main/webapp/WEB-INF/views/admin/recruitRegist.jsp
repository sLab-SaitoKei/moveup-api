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
                        <h3>求人管理新規</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>求人管理
                                    <small>新規</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                                <form:form modelAttribute="recruitForm" id="userForm" enctype="multipart/form-data"
                                           action="${pageContext.request.contextPath}/admin/recruit/regist/"
                                           class="form-horizontal form-label-left" method="post">
                                    <form:input type="hidden" path="picUrl"  id = "picUrl" value="${form.picUrl}"/>
                                    <form:input type="hidden" path="picUrl1" id = "picUrl1" value="${form.picUrl1}"/>
                                    <form:input type="hidden" path="picUrl2" id = "picUrl2" value="${form.picUrl2}"/>
                                    <form:input type="hidden" path="picUrl3" id = "picUrl3" value="${form.picUrl3}"/>
                                    <form:input type="hidden" path="picUrl4" id = "picUrl4" value="${form.picUrl4}"/>
                                    <form:input type="hidden" path="picUrl5" id = "picUrl5" value="${form.picUrl5}"/>
                                    <form:input type="hidden" path="picUrl6" id = "picUrl6" value="${form.picUrl6}"/>
                                    <form:input type="hidden" path="picUrl7" id = "picUrl7" value="${form.picUrl7}"/>
                                    <form:input type="hidden" path="picUrl8" id = "picUrl8" value="${form.picUrl8}"/>
                                    <form:input type="hidden" path="picUrl9" id = "picUrl9" value="${form.picUrl9}"/>
                                    <form:input type="hidden" path="picUrl10" id = "picUrl10" value="${form.picUrl10}"/>

                                    <div class="item form-group" id="shopRadio" >
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="">管理店舗：
                                        </label>
                                        <div class="radio" id="radio" onchange="search()">
                                            <label>
                                                <form:radiobutton path="adminShop"  value="1" checked="checked"
                                                                  id="optionsAdminShopRadios1" name="AS"/> 管理店指定なし
                                            </label>

                                            <label>
                                                <form:radiobutton path="adminShop" value="2"   id="optionsAdminShopRadios2"
                                                                  name="AS"/> 管理店UUID入力
                                            </label>
                                        </div>
                                    </div>
                                    <div class="item form-group"  style="display: none" id="searchId" >
                                        <label class="control-label col-md-3 col-sm-1 col-xs-12" for="">管理店UUID：
                                        </label>
                                        <div class="col-md-2 col-sm-1 col-xs-12">
                                            <form:input path="searchId"  name="" type="text" class="form-control col-md-7 col-xs-12"></form:input>
                                        </div>
                                    </div>
                                    <div class="item form-group"  id="mail" >
                                        <label class="control-label col-md-3 col-sm-1 col-xs-12" for="">メール：
                                        </label>
                                        <div class="col-md-2 col-sm-1 col-xs-12">
                                            <form:input path="mail"  name="" type="text" class="form-control col-md-7 col-xs-12"></form:input>
                                        </div>
                                    </div>
                                    <div class="ln_solid" id="solid" ></div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-5 col-xs-12" for="companyName">企業名 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                            <form:input type="text" id="companyName" path="companyName" name=""
                                                        required="required"
                                                        class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                        value="${recruitForm.companyName}"/>
                                        </div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12 "
                                               for="executive">担当者 <span
                                                class="required">*</span></label>
                                        <div class="col-md-2 col-sm-1 col-xs-12 ">
                                            <form:input type="text" name="executive" id="executive" path="executive" required="required"
                                                        class="form-control co2-md-1 col -sm-6 col-xs-12" value="${recruitForm.executive}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12"
                                               for="tel">電話<span class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="tel" path="tel"
                                                        required="required" class="form-control col-md-3 col-xs-12 "
                                                        value="${recruitForm.tel}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="title1">タイトル1
                                            <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input type="text" id="title1" path="title1" name=""
                                                        required="required" data-validate-minmax="10,100"
                                                        class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                        value="${recruitForm.title1}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="title2">タイトル2
                                            <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input type="text" id="title2" path="title2" name=""
                                                        required="required" data-validate-minmax="10,100"
                                                        class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                        value="${recruitForm.title2}"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="content">内容
                                            <span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input  id="content" path="content" name="" type="hidden"
                                                        required="required" data-validate-minmax="10,100"
                                                        class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                        value="${recruitForm.content}"/>
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

                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-1 col-xs-12"></div>
                                        <div class="col-md-3 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-6 col-xs-12">
                                                <label class=" col-md-6 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="picFile">一覧に表示する画像
                                                    <span class="required">*</span>
                                                </label>
                                                <label class="col-md-6 col-sm-3 col-xs-12" style="text-align: right">解像度: 550 X 350</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">

                                                <form:input type="file" id="picFile" name="picFile" path="picFile"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-6 col-xs-12">
                                                <label class=" col-md-6 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="picFile1">メイン画像
                                                    <span class="required">*</span>
                                                </label>
                                                <label class="col-md-6 col-sm-3 col-xs-12"style="text-align: right">解像度: 1100 X 400</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile1" name="picFile1" path="picFile1"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<div class="col-md-3 col-sm-1 col-xs-12"></div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像1
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile2" name="picFile2" path="picFile2"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像2
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile3" name="picFile3" path="picFile3"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像3
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile4" name="picFile4" path="picFile4"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像4
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile5" name="picFile5" path="picFile5"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像5
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile6" name="picFile6" path="picFile6"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像6
                                                    <span class="required"></span>
                                                </label>
                                                <label class="col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile7" name="picFile7" path="picFile7"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像7
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile8" name="picFile8" path="picFile8"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 col-sm-6 col-xs-12">
                                            <div class="col-md-12 col-sm-1 col-xs-12">
                                                <label class=" col-md-5 col-sm-1 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ像8
                                                    <span class="required"></span>
                                                </label>
                                                <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                            </div>
                                            <div class="col-md-12 col-sm-7 col-xs-12 ">
                                                <form:input type="file" id="picFile9" name="picFile9" path="picFile9"
                                                            class="form-control col-md-3 col-xs-12"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="item form-group" id="video" >
                                        <div class="radio  col-md-12 col-sm-3 col-xs-12" onchange="videoChose()">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-12"></label>
                                            <div class="col-md-5 col-sm-2 col-xs-12">
                                                <label>
                                                    <form:radiobutton path="" checked="" value="1"
                                                                      id="videoLeft" name="video"/> 動画
                                                </label>

                                                <label>
                                                    <form:radiobutton path="" value="2" id="videoRight"
                                                                      name="video"/> 画像
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-md-12 col-sm-6 col-xs-12">
                                            <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                            <div class="col-md-6 col-sm-6 col-xs-12" id="vRight" style="display: none">
                                                <div class="col-md-12 col-sm-6 col-xs-12" >
                                                    <label class=" col-md-6 col-sm-3 col-xs-12"  style="text-align: left;padding:0 5px;" for="">サブ画像9 <span
                                                            class="required"></span>
                                                    </label>
                                                    <label class=" col-md-6 col-sm-3 col-xs-12" style="text-align: right">解像度: 350 X 230</label>
                                                </div>
                                                <div class="col-md-12 col-sm-6 col-xs-12">
                                                    <form:input path="picFile10" id="picFile10"
                                                                class="form-control col-md-7 col-xs-12" name="picFile10"
                                                                required="" type="file"/>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-sm-6 col-xs-12" id="vLeft" style="display: none">
                                                <form:input path="videoUrl1" id="videoUrl" type="text"
                                                            class="form-control col-md-7 col-xs-12" name=""/>
                                                <p style="margin-top: 40px;" >YOUTUBE URL パラメータの設定</p>
                                                <p >音を消す自動再生する : ?rel=0&mute=1;autoplay=1</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="carerr">業種 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-9 col-xs-12">
                                            <form:select path="carerr" id="carerr" class="form-control" onchange="genre()" required="required">
                                                <form:option value="0">--</form:option>
                                                <form:options   items="${carerrList}" itemLabel="desc" itemValue="content" ></form:options>
                                            </form:select>
                                        </div>
                                        <label class="control-label col-md-1 col-sm-2 col-xs-12" for="recruitName">職業
                                            <span class="required">*</span>
                                        </label>
                                        <div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input type="text" id="recruitName" path="recruitName"
                                                        required="required" class="form-control col-md-3 col-xs-12 "
                                                        value="${recruitForm.recruitName}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="spot">拠点名 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="spot" path="spot"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.spot}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="address">勤務地 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="address" path="address" required="required"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.address}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="">エリア<span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-2 col-sm-1 col-xs-12">
                                            <form:select  path="area" class="form-control" required="required" >
                                                <form:option value="">--</form:option>
                                                <form:options items="${areaList}" itemLabel="desc" itemValue="content"></form:options>
                                            </form:select>
                                        </div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="station">駅 <span
                                                class="required">*</span>
                                        </label>
                                        <div class="col-md-3 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="station" path="station" required="required"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.station}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="workingTime">勤務時間
                                            <span class="required">*</span>
                                        </label>
                                        <div class='col-md-1 col-sm-1 col-xs-12'>
                                            <div class="form-group">
                                                <div class='input-group date' id='workingTimeStartDatepicker'>
                                                    <form:input id="workingTimeStart" path="workingTimeStart"
                                                                type='text' class="form-control"
                                                                value="${recruitForm.workingTimeStart}"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class='col-md-1 col-sm-1 col-xs-12'>
                                            <div class="form-group">
                                                <div class='input-group date' id='workingTimeEndDatepicker'>
                                                    <form:input id="workingTimeEnd" path="workingTimeEnd" type="text"
                                                                class="form-control"
                                                                value="${recruitForm.workingTimeEnd}"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-5 col-xs-12 ">
                                            <form:input type="text" id="workingTime" path="workingTime"
                                                        required="required" class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.workingTime}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="workDay">勤務日数 <span
                                                ></span>
                                        </label>
                                        <div class="col-md-2 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="workDay" path="workDay"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.executive}"/>
                                        </div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="acceptance">応募締め切り日 <span
                                                ></span>
                                        </label>
                                        <div class="col-md-3 col-sm-7 col-xs-12 ">
                                            <form:input type="text" id="acceptance" path="acceptance"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.acceptance}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">勤務時間 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="workPeriod " path="workPeriod"
                                                             items="${workPeriodMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">勤務期間 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="workTimeList" path="workTimeList"
                                                             items="${workTimeMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-1 col-xs-12" for="mode">雇用形態
                                        </label>
                                        <div class="col-md-6 col-sm-1 col-xs-12 form-group checkbox-list">
                                            <form:checkboxes id="mode" path="modeList"
                                                             items="${modeMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">経験・資格 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="capacityList" path="capacityList"
                                                             items="${capacityMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">働き方 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="workWayList" path="workWayList"
                                                             items="${workWayMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">給与の特徴 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="allowanceSpecialList" path="allowanceSpecialList"
                                                             items="${allowanceSpecialMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">職場環境 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="workEnvironmentList" path="workEnvironmentList"
                                                             items="${workEnvironmentMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">福利厚生 <span
                                                class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">
                                            <form:checkboxes id="treatmentList" path="treatmentList"
                                                             items="${treatmentNewMap}"></form:checkboxes>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="feature">特徽 <span--%>
                                                <%--class="required"></span>--%>
                                        <%--</label>--%>
                                        <%--<div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">--%>
                                            <%--<form:checkboxes id="feature" path="featureList"--%>
                                                             <%--items="${featureMap}"></form:checkboxes>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="detail">こだわり <span--%>
                                                <%--class="required"></span>--%>
                                        <%--</label>--%>
                                        <%--<div class="col-md-6 col-sm-9 col-xs-12 checkbox-list">--%>
                                            <%--<form:checkboxes id="detail" path="detailList"--%>
                                                             <%--items="${detailMap}"></form:checkboxes>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label class="control-label col-md-3 col-sm-1 col-xs-12" for="salary">給料 <span
                                                class="required">*</span>
                                        </label>
                                        </label>
                                        <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                            <form:input type="text" id="salary" path="salary" required="required"
                                                        class="form-control col-md-3 col-xs-12"
                                                        value="${recruitForm.salary}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="salaryFull">給与<span
                                                class="required">*</span>
                                        </label>
                                        <%--<div class="col-md-6 col-sm-7 col-xs-12 ">--%>
                                            <%--<form:textarea type="text" id="salaryFull" path="salaryFull" cssStyle="height: 120px"--%>
                                                        <%--required="required" class="form-control col-md-3 col-xs-12"--%>
                                                        <%--value="${recruitForm.salaryFull}"/>--%>
                                        <%--</div>--%>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input  id="salaryFull" path="salaryFull" name="" type="hidden"
                                                         required="required" data-validate-minmax="10,100"
                                                         class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                         value="${recruitForm.salaryFull}"/>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor3">
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
                                                <div id="editor3" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<div class="col-md-1 col-sm-1 col-xs-12"></div>--%>
                                        <%--<label class="control-label col-md-2 col-sm-2 col-xs-12"--%>
                                               <%--for="workingDate">勤務時間<span class="required"></span>--%>
                                        <%--</label>--%>
                                        <%--<div class="col-md-6 col-sm-7 col-xs-12 ">--%>
                                            <%--<form:textarea type="text" id="workingDate" path="workingDate" cssStyle="height: 120px"--%>
                                                        <%--required="required" class="form-control col-md-3 col-xs-12 "--%>
                                                        <%--value="${recruitForm.workingDate}"/>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12" for="capacity">資格 <span
                                                class="required">*</span>
                                        </label>
                                        <%--<div class="col-md-6 col-sm-7 col-xs-12 ">--%>
                                            <%--<form:textarea type="text" id="capacity" path="capacity" required="required"--%>
                                                        <%--class="form-control col-md-3 col-xs-12 " cssStyle="height: 120px"--%>
                                                        <%--value="${recruitForm.capacity}"/>--%>
                                        <%--</div>--%>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input  id="capacity" path="capacity" name="" type="hidden"
                                                         required="required" data-validate-minmax="10,100"
                                                         class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                         value="${recruitForm.capacity}"/>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor4">
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
                                                <div id="editor4" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12" for="vacation">休日・休暇
                                            <span class="required">*</span>
                                        </label>
                                        <%--<div class="col-md-6 col-sm-7 col-xs-12 ">--%>
                                            <%--<form:textarea type="text" id="vacation" path="vacation" required="required"--%>
                                                        <%--class="form-control col-md-3 col-xs-12 " cssStyle="height: 120px"--%>
                                                        <%--value="${recruitForm.vacation}"/>--%>
                                        <%--</div>--%>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input  id="vacation" path="vacation" name="" type="hidden"
                                                         required="required" data-validate-minmax="10,100"
                                                         class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                         value="${recruitForm.vacation}"/>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor5">
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
                                                <div id="editor5" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12" for="treatment">福利厚生
                                            <span class="required">*</span>
                                        </label>
                                        <%--<div class="col-md-6 col-sm-7 col-xs-12 ">--%>
                                            <%--&lt;%&ndash;<form:checkboxes id="treatmentList" path="treatmentList"&ndash;%&gt;--%>
                                                             <%--&lt;%&ndash;items="${treatmentMap}"></form:checkboxes>&ndash;%&gt;--%>

                                            <%--<form:textarea type="text" id="treatment" path="treatment" required="required"--%>
                                                        <%--class="form-control col-md-3 col-xs-12 " cssStyle="height: 120px"--%>
                                                        <%--value="${recruitForm.treatment}"/>--%>
                                        <%--</div>--%>
                                        <div class="col-md-6 col-sm-9 col-xs-12">
                                            <form:input  id="treatment" path="treatment" name="" type="hidden"
                                                         required="required" data-validate-minmax="10,100"
                                                         class="form-control co2-md-7 col -sm-6 col-xs-12"
                                                         value="${recruitForm.treatment}"/>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor6">
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
                                                <div id="editor6" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12" for="desc">仕事内容<span class="required">*</span>
                                        </label>
                                        <div class="col-md-6 col-sm-7 col-xs-12">
                                            <form:input id="desc" required="required" path="desc" name="desc" type="hidden"
                                                           class="form-control col-md-7 col-xs-12" style="height: 500px"
                                                           value="${recruitForm.desc}"></form:input>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor2">
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

                                                <div id="editor2" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-1 col-sm-1 col-xs-12"></div>
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12" for="workingDate">関連、系列グループ店舗
                                            <span class="required"></span>
                                        </label>
                                        <div class="col-md-6 col-sm-7 col-xs-12 ">
                                            <form:input name="textarea" id="workingDate" path="workingDate" required="required" type="hidden"
                                                        class="form-control col-md-3 col-xs-12 "
                                                        value="${recruitForm.workingDate}"/>
                                            <div class="x_content">
                                                <div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#editor7">
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
                                                <div id="editor7" class="editor-wrapper placeholderText" contenteditable="true"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="item form-group">
                                        <div>
                                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="publishStart">掲載開始日 </label>
                                            <div class="col-md-2 col-sm-6 col-xs-12">
                                                <form:input path="publishStart" type="date" id="publishStart" name="publishStart"
                                                            class="form-control col-md-7 col-xs-12"/>
                                            </div>
                                        </div>
                                        <div>
                                            <label class="control-label col-md-1 col-sm-3 col-xs-12" for="publishEnd">掲載終了日 </label>
                                            <div class="col-md-2 col-sm-6 col-xs-12">
                                                <form:input path="publishEnd" type="date" id="publishEnd" name="publishEnd"
                                                            class="form-control col-md-7 col-xs-12"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                            <a class="btn btn-primary" href="/admin/recruit/list/" style="width: 100px;">キャンセル</a>
                                            <button type="submit" class="btn btn-success" style="width: 100px;">登録
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
<div id="recruitLoading" style="display: none" class="loading">
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
<!-- bootstrap-datetimepicker -->
<script src="/static/admin/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>
<!--shop Scripts -->
<script src="/static/admin/build/js/shopRegist.js"></script>

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
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
<script>
    $('#workingTimeStartDatepicker').datetimepicker({
        format: 'HH:mm'
    });

    $('#workingTimeEndDatepicker').datetimepicker({
        format: 'HH:mm'
    });

    // $('#picFile').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":550,"maxImageWidth":550,"minImageHeight":350,"maxImageHeight":350});
    // $('#picFile1').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1100,"maxImageWidth":1100,"minImageHeight":400,"maxImageHeight":400});
    // $('#picFile2').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500});
    // $('#picFile3').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500});
    // $('#picFile4').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500});
    // $('#picFile5').fileinput({
    //     'language':'ja', ''showClose': false, 'showClose': false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500});

    $('#picFile').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showClose': false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":550,"maxImageWidth":550,"minImageHeight":350,"maxImageHeight":350
        <c:if test="${recruitForm.picUrl != null && recruitForm.picUrl != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile1').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showClose': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":1100,"maxImageWidth":1100,"minImageHeight":400,"maxImageHeight":400
        <c:if test="${recruitForm.picUrl1 != null && recruitForm.picUrl1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl1}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showClose': false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl2 != null && recruitForm.picUrl2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl2}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showClose': false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl3 != null && recruitForm.picUrl3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl3}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showClose': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl4 != null && recruitForm.picUrl4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl4}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl5 != null && recruitForm.picUrl5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl5}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile6').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl6 != null && recruitForm.picUrl6 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl6}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile7').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl7 != null && recruitForm.picUrl7 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl7}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile8').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl8 != null && recruitForm.picUrl8 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl8}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile9').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${recruitForm.picUrl9 != null && recruitForm.picUrl9 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl9}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile10').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove': false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":350,"maxImageWidth":230,"minImageHeight":350,"maxImageHeight":230
        <c:if test="${recruitForm.picUrl10 != null && recruitForm.picUrl10 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${recruitForm.picUrl10}' class='file-preview-image kv-preview-data' stype='max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    function onRegist() {
        $('#recruitLoading').show();
        return true;
    }

    jQuery.validator.addMethod("telNo",function(value,element) {
        var tel = /^[\d-]*$/;
        return this.optional(element) || (tel.test(value));
    }, $.validator.format("有効な電話番号を入力してください。"));

    var $userForm = $("#userForm");
    $userForm.validate({
        submitHandler: function(form) {
            $('#recruitLoading').show();
            $("#content").val($('#editor').html());
            $("#desc").val($('#editor2').html());
            $("#salaryFull").val($('#editor3').html());
            $("#capacity").val($('#editor4').html());
            $("#vacation").val($('#editor5').html());
            $("#treatment").val($('#editor6').html());
            $("#workingDate").val($('#editor7').html());
            $(form).submit();
        },
        errorClass : "validatorError",
        rules : {
            radio :{
                required: true
            },
            searchId :{
                required: true,
                // digits: true
            },
            mail :{
                required: true,
                email: true
            },
            shopId :{
                required: true,
                maxlength: 10,
                digits: true
            },
            companyName :{
                required: true,
                maxlength: 255
            },
            tel :{
                required: true,
                maxlength: 13,
                telNo: true
            },
            picFile:{
                required: true,
            },
            picFile1:{
                required: true,
            },
            title1 :{
                required: true,
                maxlength: 255
            },
            title2 :{
                required: true,
                maxlength: 255
            },
            videoUrl1 :{
                url: true,
                maxlength: 255
            },
            recruitName :{
                required: true,
                maxlength: 255
            },
            salary :{
                required: true,
                maxlength: 30
            },
            address :{
                required: true,
                maxlength: 255
            },
            station :{
                required: true,
                maxlength: 255
            },
            // carerr:{
            //     required: true,
            // },
            workingTimeStart :{
                required: true,
                required: true
            },
            workingTimeEnd :{
                required: true,
                required: true
            },
            workingTime :{
                required: true,
                maxlength: 60
            },
            salaryFull :{
                required: true,
                maxlength: 255
            },
            workingDate :{
                required: true,
                maxlength: 255
            },
            capacity :{
                required: true,
                maxlength: 255
            },
            vacation :{
                required: true,
                maxlength: 255
            },
            treatment :{
                required: true,
                maxlength: 255
            },
            desc :{
                required: true,
                maxlength: 2048
            }
        }
    });
    $('#editor').html($("#content").val());
    $('#editor2').html($("#desc").val());
    $('#editor3').html($("#salaryFull").val());
    $('#editor4').html($("#capacity").val());
    $('#editor5').html($("#vacation").val());
    $('#editor6').html($("#treatment").val());
    $("#editor7").html($('#workingDate').val())

</script>

<script>
    function initToolbarBootstrapBindings() {
        var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
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
