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

    <!-- select2 -->
    <link  href="/static/admin/vendors/select2/dist/css/select2.min.css" rel="stylesheet">

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

        .select2-container--default .select2-selection--multiple {
            border-radius:0;
            border: 1px solid #ccc;;
            width: 540px;
            height: 34px;
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
                        <h3>プレス管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>プレス基本情報

                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                               <form:form modelAttribute="form" id="userForm" enctype="multipart/form-data"
                                          action="${pageContext.request.contextPath}/admin/place/update/" class="form-horizontal form-label-left" method="post">
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-1 col-xs-12" for="">リンク：
                                       </label>
                                       <label class="control-label col-md-6 col-sm-1 col-xs-12" style="text-align: left;font-size: 14px;">
                                           <a  href="${pageContext.request.contextPath}/place/detail/${form.uuid}/">${pageContext.request.contextPath}/place/detail/${form.uuid}/</a>
                                       </label>
                                   </div>
                                   <div class="item form-group">
                                       <div class="col-md-2 col-sm-1 col-xs-12"></div>
                                       <label class="control-label col-md-1 col-sm-1 col-xs-12" for="">カテゴリ：
                                       </label>
                                       <div class="col-md-2 col-sm-1 col-xs-12 ">
                                       <label class="control-label " style="text-align: left">プレス</label>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <form:input type="hidden" id="id" path="id" value="${form.id}"/>
                                       <%--<form:input type="hidden" id="shopId" path="shopId" value="${form.shopId}"/>--%>
                                       <form:input type="hidden" id="shopListId" path="placeListId" value="${form.placeListId}"/>
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">名所 <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="name" id="name" value="${form.name}" class="form-control col-md-7 col-xs-12"
                                                       name="name" required="required" type="text"/>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-1 col-xs-12" for="">エリア
                                       </label>
                                       <div class="col-md-2 col-sm-1 col-xs-12 ">
                                           <form:select  path="area" class="form-control">
                                               <form:option value="0">--</form:option>
                                               <form:options   items="${areaList}" itemLabel="desc" itemValue="content"></form:options>
                                           </form:select>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="desc2" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">最寄駅
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="station" id="station" value="${form.station}"
                                                       class="form-control col-md-7 col-xs-12"
                                                       name="" required="" type="text"/>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">最寄駅徒歩時間
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="walk" id="walk" value="${form.walk}"
                                                       class="form-control col-md-7 col-xs-12"
                                                       name="" required="" type="text"/>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">観覧時間 <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="time" id="time" value="${form.time}" class="form-control col-md-7 col-xs-12"
                                                       name="time" required="required" type="text"/>
                                       </div>
                                       <%--<label class="control-label col-md-1 col-sm-3 col-xs-12" for="">定休日 <span--%>
                                               <%--class="required">*</span>--%>
                                       <%--</label>--%>
                                       <%--<div class="col-md-2 col-sm-6 col-xs-12">--%>
                                           <%--<form:input path="holiday" id="holiday" value="${form.holiday}"--%>
                                                       <%--class="form-control col-md-7 col-xs-12" name="holiday"--%>
                                                       <%--required="required" type="text"/>--%>
                                       <%--</div>--%>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">座標X <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="coordinate1" id="coordinate1" value="${form.coordinate1}" class="form-control col-md-7 col-xs-12"
                                                       name="coordinate1" required="required" type="text"/>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">座標Y <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <form:input path="coordinate2" id="coordinate2" value="${form.coordinate2}"
                                                       class="form-control col-md-7 col-xs-12" name="coordinate2"
                                                       required="required" type="text"/>
                                       </div>
                                   </div>

                                   <div class="item form-group" id="address" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">電話 <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <form:input path="tel" id="tel" value="${form.tel}"
                                                       class="form-control col-md-7 col-xs-12" name="tel"
                                                       required="required" type="text"/>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="desc6" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">アドレス<span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <form:input path="address" id="address" value="${form.address}"
                                                       class="form-control col-md-7 col-xs-12"
                                                       name="address" required="" type="text"/>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">定休日<span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <form:input path="holiday" id="holiday" value="${form.holiday}"
                                                       class="form-control col-md-7 col-xs-12"
                                                       name="" required="" type="text"/>
                                       </div>
                                       <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                       <div class="radio  col-md-12 col-sm-3 col-xs-12" onchange="restDay()">
                                           <label class="control-label col-md-3 col-sm-3 col-xs-12"></label>
                                           <div class="col-md-5 col-sm-2 col-xs-12">
                                               <label>
                                                   <form:radiobutton path="restRadio" checked="" value="1"
                                                                     id="RD" name="rest"/> 日付
                                               </label>

                                               <label>
                                                   <form:radiobutton path="restRadio" value="2" id="RW"
                                                                     name="rest"/> 曜日
                                               </label>
                                           </div>
                                           <div class="col-md-1 col-sm-2 col-xs-12">
                                               <a id="addItem" class="btn btn-round btn-warning " style="display: none;"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                                           </div>
                                       </div>
                                       <div class="item form-group col-md-12 col-sm-6 col-xs-12" style="display:none" id="restD">
                                           <label class="control-label col-md-4 col-sm-6 col-xs-12"><span
                                                   class="required"></span></label>
                                           <form:select path="restDates" id="restDate" class="form-control col-md-3 col-xs-12"
                                                        name="" required="" multiple="multiple" style="border-radius: 0;height: 34px; width:200px;display:none" >
                                               <option value="0">--</option>
                                               <form:options   items="${restDateList}" itemLabel="desc" itemValue="content"></form:options>
                                           </form:select>
                                       </div>

                                       <div class="item form-group col-md-12 col-sm-6 col-xs-12 " style="margin-top: 15px; display: none;" id="wk" >
                                           <c:forEach items="${restFormList}" var="rfl">
                                               <div class=" col-md-12 col-sm-6 col-xs-12 ">
                                                   <label class="control-label col-md-3 col-sm-6 col-xs-12"><span
                                                           class="required"></span></label>
                                                   <div class="col-md-2 col-sm-2 col-xs-12 form-group " id="restWeeks" name="restWeeks" >
                                                       <select path="restWeek" id="restWeek" class="form-control col-md-3 col-xs-12" data-value="${rfl.week}"
                                                               name="stock[${rfl.amountId}].week" required=""  style="border-radius: 0;height: 34px; " >
                                                           <option value="0">--</option>
                                                               <%--<options   items="${restWeekList}" itemLabel="desc" itemValue="content"></options>--%>
                                                           <option <c:if test='${rfl.week == 1}'>selected="selected"</c:if> value="1">第1週</option>
                                                           <option <c:if test='${rfl.week == 2}'>selected="selected"</c:if> value="2">第2週</option>
                                                           <option <c:if test='${rfl.week == 3}'>selected="selected"</c:if> value="3">第3週</option>
                                                           <option <c:if test='${rfl.week == 4}'>selected="selected"</c:if> value="4">第4週</option>
                                                       </select>
                                                   </div >
                                                   <label class="control-label col-md-1 col-sm-6 col-xs-12"><span
                                                           class="required"></span></label>
                                                   <div class="col-md-2 col-sm-2 col-xs-12 form-group " id="restWeekDays" name="restWeekDays">
                                                       <select path="restWeekDay" id="restWeekDay" class="form-control col-md-1 col-xs-12" data-value="${rfl.weekDay}"
                                                               name="stock[${rfl.amountId}].weekDay" required="" style="border-radius: 0;height: 34px; ">
                                                           <option value="0">--</option>
                                                               <%--<options   items="${restWeekDayList}" itemLabel="desc" itemValue="content"></options>--%>
                                                           <option <c:if test='${rfl.weekDay == 1}'>selected="selected"</c:if> value="1">月曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 2}'>selected="selected"</c:if> value="2">火曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 3}'>selected="selected"</c:if> value="3">水曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 4}'>selected="selected"</c:if> value="4">木曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 5}'>selected="selected"</c:if> value="5">金曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 6}'>selected="selected"</c:if> value="6">土曜日</option>
                                                           <option <c:if test='${rfl.weekDay == 7}'>selected="selected"</c:if> value="7">日曜日</option>
                                                       </select>
                                                   </div>
                                               </div>
                                           </c:forEach>
                                       </div>
                                       <div id="item" style="display: none"></div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">タイトル <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <form:input path="title" id="title" value="${form.title}" class="form-control col-md-7 col-xs-12"
                                                       name="title" required="required" type="text"/>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">抜粋 <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <form:textarea path="introduce" id="introduce" value="${form.introduce}" class="form-control col-md-7 col-xs-12" name="introduce"
                                                          required="required" type="text"/>
                                       </div>
                                   </div>
                                   <div class="item form-group">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">紹介文 <span
                                               class="required">*</span>
                                       </label>
                                       <div class="col-md-6 col-sm-6 col-xs-12" style="height: 350px;">
                                           <form:input path="desc" id="desc" value="${form.desc}" class="form-control col-md-7 col-xs-12"
                                                          name="desc" required="required" type = "hidden" style="height: 200px;"/>
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
                                   <%--<div class="item form-group"id="picFile6" >--%>
                                       <%--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="">360flic <span--%>
                                               <%--class="required">*</span>--%>
                                       <%--</label>--%>
                                       <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                           <%--<form:input path="picUrl6" id="picUrl6"--%>
                                                       <%--class="form-control col-md-7 col-xs-12" name="picUrl6"/>--%>
                                       <%--</div>--%>
                                   <%--</div>--%>
                                   <div class="item form-group">
                                       <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                       <div class="col-md-6 col-sm-6 col-xs-12">
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class="control-label col-md-6 col-sm-3 col-xs-12" style="text-align: left" for="">一覧に表示する画像 <span
                                                       class="">*</span>
                                               </label>
                                               <label class="control-label col-md-6 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">

                                               <form:input path="picFile" id="picFile"
                                                           class="form-control col-md-7 col-xs-12" name="picFile"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="">
                                       <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                       <div class="col-md-2 col-sm-6 col-xs-12" id="File" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12"  style="text-align: left;padding:0 5px;" for="">メイン画像 <span
                                                       class="required">*</span>
                                               </label>
                                               <label class="col-md-7 col-sm-3 col-xs-12">解像度: 750 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile1" id="picFile1"
                                                           class="form-control col-md-7 col-xs-12" name="picFile1"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div class="col-md-2 col-sm-6 col-xs-12" id="File1" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ画像1 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile2" id="picFile2"
                                                           class="form-control col-md-7 col-xs-12" name="picFile2"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div class="col-md-2 col-sm-6 col-xs-12" id="File2" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ画像2 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile3" id="picFile3"
                                                           class="form-control col-md-7 col-xs-12" name="picFile3"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="">
                                       <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                       <div class="col-md-2 col-sm-6 col-xs-12" id="File3">
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ画像3 <span
                                                       class="required"></span>
                                               </label>
                                               <label class="col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile4" id="picFile4"
                                                           class="form-control col-md-7 col-xs-12" name="picFile4"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div  class="col-md-2 col-sm-6 col-xs-12" id="File4" >
                                           <div class="col-md-12 col-sm-6 col-xs-12" >
                                               <label class=" col-md-5 col-sm-3 col-xs-12"  style="text-align: left;padding:0 5px;" for="">サブ画像4 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile5" id="picFile5"
                                                           class="form-control col-md-7 col-xs-12" name="picFile5"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div  class="col-md-2 col-sm-6 col-xs-12"  id="File5" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;"  for="">サブ画像5 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile7" id="picFile7"
                                                           class="form-control col-md-7 col-xs-12" name="picFile7"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="">
                                       <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                       <div class="col-md-2 col-sm-6 col-xs-12" id="File8" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;"  for="">サブ画像6 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile8" id="picFile8"
                                                           class="form-control col-md-7 col-xs-12" name="picFile8"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div  class="col-md-2 col-sm-6 col-xs-12" id="File9">
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class="control-label col-md-6 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;"  for="">サブ画像7 <span
                                                       class="required"></span>
                                               </label>
                                               <label class="control-label col-md-6 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile9" id="picFile9"
                                                           class="form-control col-md-7 col-xs-12" name="picFile9"
                                                           required="" type="file"/>
                                           </div>
                                       </div>
                                       <div  class="col-md-2 col-sm-6 col-xs-12" id="File10" >
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <label class=" col-md-5 col-sm-3 col-xs-12" style="text-align: left;padding:0 5px;" for="">サブ画像8 <span
                                                       class="required"></span>
                                               </label>
                                               <label class=" col-md-7 col-sm-3 col-xs-12">解像度: 500 X 500</label>
                                           </div>
                                           <div class="col-md-12 col-sm-6 col-xs-12">
                                               <form:input path="picFile10" id="picFile10"
                                                           class="form-control col-md-7 col-xs-12" name="picFile10"
                                                           required="" type="file"/>
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
                                                   <label class=" col-md-6 col-sm-3 col-xs-12">解像度: 350 X 230</label>
                                               </div>
                                               <div class="col-md-12 col-sm-6 col-xs-12">
                                                   <form:input path="picFile11" id="picFile11"
                                                               class="form-control col-md-7 col-xs-12" name="picFile11"
                                                               required="" type="file"/>
                                               </div>
                                           </div>
                                           <div class="col-md-6 col-sm-6 col-xs-12" id="vLeft" style="display: none">
                                               <form:input path="videoUrl" id="videoUrl" type="text"
                                                           class="form-control col-md-7 col-xs-12" name=""/>
                                               <p style="margin-top: 40px;" >YOUTUBE URL パラメータの設定</p>
                                               <p >音を消す自動再生する : ?rel=0&mute=1;autoplay=1</p>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="item form-group" id="picFile6" >
                                       <div class="radio  col-md-12 col-sm-3 col-xs-12" onchange="flicChose()">
                                           <label class="control-label col-md-3 col-sm-3 col-xs-12"></label>
                                           <div class="col-md-5 col-sm-2 col-xs-12">
                                               <label>
                                                   <form:radiobutton path="" checked="" value="1"
                                                                     id="flicLeft" name="flic"/> 360flic
                                               </label>

                                               <label>
                                                   <form:radiobutton path="" value="2" id="flicRight"
                                                                     name="flic"/> 画像
                                               </label>
                                           </div>
                                       </div>
                                       <div class="col-md-12 col-sm-6 col-xs-12">
                                           <div class="col-md-3 col-sm-6 col-xs-12"></div>
                                           <div class="col-md-6 col-sm-6 col-xs-12" id="360Right" style="display: none">
                                               <div class="col-md-12 col-sm-6 col-xs-12" >
                                                   <label class=" col-md-6 col-sm-3 col-xs-12"  style="text-align: left;padding:0 5px;" for="">サブ画像10 <span
                                                           class="required"></span>
                                                   </label>
                                                   <label class=" col-md-6 col-sm-3 col-xs-12" style="text-align: right">解像度: 350 X 230</label>
                                               </div>
                                               <div class="col-md-12 col-sm-6 col-xs-12">
                                                   <form:input path="picFile12" id="picFile12"
                                                               class="form-control col-md-7 col-xs-12" name="picFile12"
                                                               required="" type="file"/>
                                               </div>
                                           </div>
                                           <div class="col-md-6 col-sm-6 col-xs-12" id="360Left" style="display: none">
                                               <p ><form:input path="flicUrl" id="flicUrl" type="text"
                                                               class="form-control col-md-7 col-xs-12" name=""/></p>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="item form-group"  id="publishDate">
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="publishStart">掲載開始日 </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <input  type="date" id="publishStart" name="publishStart" value="${form.publishStart}"
                                                       class="form-control col-md-7 col-xs-12"/>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="publishEnd">掲載終了日 </label>
                                       <div class="col-md-2 col-sm-6 col-xs-12">
                                           <input  type="date" id="publishEnd" name="publishEnd"  value="${form.publishEnd}"
                                                       class="form-control col-md-7 col-xs-12"/>
                                       </div>
                                   </div>


                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-3 col-md-offset-7">
                                            <a class="btn btn-primary" href="/admin/place/list/" style="width: 100px;">キャンセル</a>
                                            <button id="send" type="submit" class="btn btn-success"
                                                    style="width: 100px;">登録
                                            </button>
                                        </div>
                                        <form:input type="hidden" path="picUrl"  id = "picUrl" value="${form.picUrl}"/>
                                        <form:input type="hidden" path="picUrl1" id = "picUrl1" value="${form.picUrl1}"/>
                                        <form:input type="hidden" path="picUrl2" id = "picUrl2" value="${form.picUrl2}"/>
                                        <form:input type="hidden" path="picUrl3" id = "picUrl3" value="${form.picUrl3}"/>
                                        <form:input type="hidden" path="picUrl4" id = "picUrl4" value="${form.picUrl4}"/>
                                        <form:input type="hidden" path="picUrl5" id = "picUrl5" value="${form.picUrl5}"/>
                                        <%--<form:input type="hidden" path="picUrl6" id = "picUrl6" value="${form.picUrl6}"/>--%>
                                        <form:input type="hidden" path="picUrl7" id = "picUrl7" value="${form.picUrl7}"/>
                                        <form:input type="hidden" path="picUrl8" id = "picUrl8" value="${form.picUrl8}"/>
                                        <form:input type="hidden" path="picUrl9" id = "picUrl9" value="${form.picUrl9}"/>
                                        <form:input type="hidden" path="picUrl10" id = "picUrl0" value="${form.picUrl10}"/>
                                        <form:input type="hidden" path="picUrl11" id = "picUrl11" value="${form.picUrl11}"/>
                                        <form:input type="hidden" path="picUrl12" id = "picUrl12" value="${form.picUrl12}"/>
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
<div id="placeLoading" style="display: none" class="loading">
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
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.js"></script>
<script src="/static/admin/vendors/bootstrap-fileinput/js/locales/ja.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
<!--shop Scripts -->
<script src="/static/admin/build/js/shopRegist.js"></script>
<%--<script src="/static/admin/build/js/shopEdit.js"></script>--%>
<script src="/static/admin/build/js/jieZaiTime.js"></script>
<!--select2 -->
<script src="/static/admin/vendors/select2/dist/js/select2.min.js"></script>
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

<script type="text/javascript">

    $(document).ready(function(){
        var item = "<div class='form-group col-md-12 col-sm-6 col-xs-12' style=''>"
            +"<label class='control-label col-md-3 col-sm-2 col-xs-12' > </label>"
            +"<div class='col-md-2 col-sm-2 col-xs-12 form-group '>"
            // +"<div class='col-md-3 col-sm-2 col-xs-12  '>"
            +"<select id='week' name='stock[0].week' class='form-control col-md-2 col-sm-2 col-xs-12' >"
            +"<option value='0'>--</option>"
            +"<option value='1'>第1週</option>"
            +"<option value='2'>第2週</option>"
            +"<option value='3'>第3週</option>"
            +"<option value='4'>第4週</option>"
            +"</select>"
            +"</div>"
            +"<label class='control-label col-md-1 col-sm-2 col-xs-12' > </label>"
            +"<div class='col-md-2 col-sm-2 col-xs-12  '>"
            +"<select id='weekDay' name='stock[0].weekDay' class='form-control col-md-2 col-sm-2 col-xs-12' >"
            +"<option value='0'>--</option>"
            +"<option value='1'>月曜日</option>"
            +"<option value='2'>火曜日</option>"
            +"<option value='3'>水曜日</option>"
            +"<option value='4'>木曜日</option>"
            +"<option value='5'>金曜日</option>"
            +"<option value='6'>土曜日</option>"
            +"<option value='7'>日曜日</option>"
            +"</select>"
            +"</div>"
            +"<label class='control-label col-md-1 col-sm-2 col-xs-12' > </label>"
            +"</div>"
            +"<hr/>"

        var n = 1;

        $("#addItem").click(function(){
            $("#item").append(item);

            var objWeek = $("#week");
            objWeek.each(function(i){
                this.id = "week"+n;
                this.name =  "stock["+n+"].week";
            });

            var objWeekDay = $("#weekDay");
            objWeekDay.each(function(i){
                this.id = "weekDay"+n;
                this.name =  "stock["+n+"].weekDay";
            });

            var objNONE = $("#unknown");
            objNONE.each(function(i){
                this.id = "unknown"+n;
                this.name = "stock["+n+"].unknown";
            });
            n = n+1;
        });

    });

</script>

<script>
    $('#picFile').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl != null && form.picUrl != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile1').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":750,"maxImageWidth":750,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl1 != null && form.picUrl1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl1}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl2 != null && form.picUrl2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl2}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl3 != null && form.picUrl3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl3}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove':false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl4 != null && form.picUrl4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl4}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl5 != null && form.picUrl5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl5}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    <%--$('#picFile6').fileinput({--%>
        <%--'language':'ja', 'showClose': true, 'showRemove':false, 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":360,"maxImageWidth":360,"minImageHeight":230,"maxImageHeight":230--%>
        <%--<c:if test="${form.flicUrl != null && form.flicUrl != ''}">--%>
        <%--, 'uploadUrl': null, 'initialPreview': [--%>
            <%--"<img src='${form.flicUrl}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"--%>
        <%--]--%>
        <%--</c:if>--%>
    <%--});--%>
    $('#picFile7').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl7 != null && form.picUrl7 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl7}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile8').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl8!= null && form.picUrl8 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl8}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile9').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false, 'dropZoneTitle':'','showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl9!= null && form.picUrl9 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl9}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile10').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":500,"maxImageWidth":500,"minImageHeight":500,"maxImageHeight":500
        <c:if test="${form.picUrl10!= null && form.picUrl10 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl10}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile11').fileinput({
        'language':'ja', 'showClose': true, 'showCaption':false,'showRemove':false,'dropZoneTitle':'', 'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":350,"maxImageWidth":350,"minImageHeight":230,"maxImageHeight":230
        <c:if test="${form.picUrl11!= null && form.picUrl11 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl11}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile12').fileinput({
        'language':'ja', 'showClose': true,'showCaption':false, 'showRemove':false, 'dropZoneTitle':'',    'showUpload': false, 'previewFileType': 'any', 'autoReplace': true,"minImageWidth":350,"maxImageWidth":350,"minImageHeight":230,"maxImageHeight":230
        <c:if test="${form.picUrl12!= null && form.picUrl12 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${form.picUrl12}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });

    $('#restDate').select2({
        tags: true,
        maximumSelectionLength: 30  //
    });

    function onUpdate() {
        $('#placeLoading').show();
        return true;
    }

    jQuery.validator.addMethod("telNo",function(value,element) {
        var tel = /^[\d-]*$/;
        return this.optional(element) || (tel.test(value));
    }, $.validator.format("有効な電話番号を入力してください。"));


    jQuery.validator.addMethod("afterPoint",function(value, element){
        var returnVal = false;
        var input=value;
        var ArrMen= input.split(".");
        if(ArrMen.length==2){
            if(ArrMen[1].length>9){
                returnVal = false;
                return false;
            }else{
                returnVal = true;
            }

            if(ArrMen[0].replace("-","").length>3){
                returnVal = false;
                return false;
            }else{
                returnVal = true;
            }
        }else if(ArrMen.length==1){
            input+=""
            if(input.replace("-","").length>3){
                returnVal = false;
                return false;
            }else{
                returnVal = true;
            }

        }
        return returnVal;
    },"有効な座標を入力してください。");



    var $userForm = $("#userForm");
    $userForm.validate({
        submitHandler: function(form) {
            $("#desc").val($('#editor').html());
            $('#placeLoading').show();
            $(form).submit();
        },
        errorClass : "validatorError",
        rules : {
            name :{
                required: true,
                maxlength: 20
            },
            station :{
                required: true,
                maxlength: 255
            },
            walk :{
                required: true,
                maxlength: 255
            },
            time :{
                required: true,
                maxlength: 255
            },
            holiday :{
                required: true,
                maxlength: 255
            },
            introduce :{
                required: true,
                maxlength: 80
            },
            tel :{
                required: true,
                maxlength: 13,
                telNo: true
            },
            address :{
                required: true,
                maxlength: 255
            },
            title :{
                required: true,
                maxlength: 255
            },
            // picFile:{
            //     required: true,
            // },
            // picFile1:{
            //     required: true,
            // },
            introduce :{
                required: true,
                maxlength: 80
            },
            desc :{
                required: true,
                maxlength: 1024
            },
            videoUrl1 :{
                required: true,
                maxlength: 255,
                url: true
            },
            coordinate1 :{
                required: true ,
                maxlength: 255,
                // number:true ,
                // afterPoint:$('#coordinate1').val()
            },
            coordinate2 :{
                required: true ,
                maxlength: 255,
                // number:true ,
                // afterPoint:$('#coordinate2').val()
            },
            // publishStart:{
            //     required:true,
            //     date:true,
            //     dateCompare:[$('#publishStart'),$('#publishEnd')]
            // },
            // publishEnd:{
            //     required:true,
            //     date:true,
            //     dateCompare:[$('#publishStart'),$('#publishEnd')]
            // }

        },
        errorPlacement:function(error,element) {
            if (element.attr("name") == "publishStart" || element.attr("name") == "publishEnd"){
                error.insertAfter("#publishEnd");
            } else{
                error.insertAfter(element);
            }

        }
    });
    $('#editor').html($("#desc").val());

    function remove1() {
        document.getElementById('picUrl').value= "";
    }
    function remove2() {
        document.getElementById('picUrl1').value= "";
    }
    function remove3() {
        document.getElementById('picUrl2').value= "";
    }
    function remove4() {
        document.getElementById('picUrl3').value= "";
    }
    function remove5() {
        document.getElementById('picUrl4').value= "";
    }
    function remove6() {
        document.getElementById('picUrl5').value= "";
    }

    // document.getElementById('picUrl6').value= null;

    function remove7() {
        document.getElementById('picUrl7').value= "";
    }
    function remove8() {
        document.getElementById('picUrl8').value= "";
    }
    function remove9() {
        document.getElementById('picUrl9').value= "";
    }
    function remove10() {
        document.getElementById('picUrl10').value= "";
    }
    function remove11() {
        document.getElementById('picUrl11').value= "";
    }
    function remove12() {
        document.getElementById('picUrl12').value= "";
    }
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