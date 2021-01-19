<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1">
    <meta name="description" content="54 JAPAN MOVE UP WEST">

    <title>54 JAPAN MOVE UP WEST</title>
    <!-- core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/animate.min.css" rel="stylesheet">
    <link href="/static/css/owl.carousel.css" rel="stylesheet">
    <link href="/static/css/owl.transitions.css" rel="stylesheet">
    <link href="/static/css/main.css" rel="stylesheet">
    <link href="/static/css/loginTheme.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/static/js/ajaxzip3.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-113626104-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-113626104-1');

        $('.selectpicker').selectpicker({
            style: 'btn-info',
            size: 4
        });
    </script>

    <link rel="shortcut icon" type="image/x-icon" href="/static/img/fav.ico">
</head><!--/head-->


<body id="Login" style="padding-top: 30px">
<form:form modelAttribute="entryForm" action="${pageContext.request.contextPath}/entry/">
    <section id="logo">
        <div class="container">
            <div class="row col-lg-12 col-xs-10" align="center">
                <img class="img-responsive" src="/static/img/logo.png">
                <p class="table_Title">会員登録フォーム</p>
                <img class="img-responsive" src="/static/img/head_statue1.png">
            </div>

            <div class="row col-lg-12 col-xs-12" align="center">
                <table border="1" class="table_List">
                    <!-----------/first-------->
                    <tr>
                        <td class="table_Height  col-lg-4 col-xs-6">氏名（漢字）<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <div class="col-lg-4 col-xs-7">
                                <!-----------/FamilName TextFiled-------->
                                <form:input path="firstName" placeholder="姓" class="form-control form-control_phone" />
                            </div>
                            <div class="col-lg-4 col-xs-7 ">
                                <!-----------/GivenName TextFiled-------->
                                <form:input path="secondName" placeholder="名" class="form-control form-control_phone" />
                            </div>
                        </td>
                    </tr>
                    <!-----------/second-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">氏名（カナ）<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <div class="col-lg-4 col-xs-7">
                                <!-----------/FamilName TextFiled-------->
                                <form:input path="firstNameKana" placeholder="セイ" class="form-control form-control_phone" />
                            </div>
                            <div class="col-lg-4 col-xs-7 ">
                                <!-----------/GivenName TextFiled-------->
                                <form:input path="secondNameKana" placeholder="メイ" class="form-control form-control_phone" />
                            </div>
                        </td>
                    </tr>
                    <!-----------third-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">性别<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <div class="radio">
                                <label class="col-lg-3">
                                    <!-----------/Select Radios-------->
                                    <form:radiobutton path="gender" value="1" checked="checked"/>男性
                                </label>
                                <label class="col-lg-3">
                                    <form:radiobutton path="gender" value="2" />女性
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-----------forth-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">生年月日<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <form:select path="birthyear" items="${birthyearList}" itemLabel="name" itemValue="id" delimiter=" " cssClass="selectpicker select_year" data-width="52px" />
                            <span class="select_Name">年</span>

                            <form:select path="birthmonth" items="${birthmonthList}" itemLabel="name" itemValue="id" delimiter=" " cssClass="selectpicker select_Month" data-width="33px" />
                            <span class="select_Name">月</span>

                            <form:select path="birthday" items="${birthdayList}" itemLabel="name" itemValue="id" delimiter=" " cssClass="selectpicker select_Month select_Month_Small" data-width="33px" />
                            <span class="select_Name">日</span>

                        </td>
                    </tr>
                    <!-----------fifth-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">職業<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <form:select path="occupation" items="${occupationList}" itemLabel="name" itemValue="id" delimiter=" " cssClass="selectpicker select_Work" data-width="84px" />
                        </td>
                    </tr>
                    <!-----------sixth-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">居住エリア<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td>
                            <div class="form-inline" style="margin-top: 5px">
                                <span class="CodeBtn">〒</span>
                                <form:input path="zipcode1" name="zip11" class="form-control form-control_firstSmall" placeholder="1010001" style="width: 92px" onKeyUp="AjaxZip3.zip2addr(this,'','address','address');"/>
                            </div>
                            <div class="col-xs-6 col-lg-12" style="margin-bottom: 5px">
                                <form:input path="address" name="address" class="form-control form-control_lastSmall" placeholder="○○県△△市123 　☐☐マンション１０１" />
                            </div>
                        </td>
                    </tr>
                    <!-----------seventh-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-7">
                            <span class="phone_mail_Label">メール(ID)</span>
                            <span class="pc_mail_Label">メールアドレス(ID)</span>
                            <span class="table_icon1">必須</span>
                            <span class="table_icon_small">*</span></td>
                        <td>
                            <div class="col-lg-8 col-xs-6">
                                <form:input path="mail" class="form-control" />
                            </div>
                        </td>
                    </tr>
                    <!-----------eighth-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">パスワード<span class="table_icon">必須</span><span class="table_icon_small">*</span></td>
                        <td >
                            <div class="col-lg-8">
                                <form:input path="password" class="form-control"  placeholder="4 ~16桁の英数字"/>
                            </div>
                        </td>
                    </tr>
                    <!-----------ninth-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">ニックネーム<span class="table_icon" style="background-color: #00a7dd">任意</span></td>
                        <td >
                            <div class="col-lg-8">
                                <form:input path="nickname" class="form-control"  placeholder="16桁以下の英数字"/>
                            </div>
                        </td>
                    </tr>

                </table>
                <div class ="error">
                    <f:errors path="firstName" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="secondName" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="firstNameKana" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="secondNameKana" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="gender" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="occupation" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="zipcode1" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="address" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="mail" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="password" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="nickname" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <f:errors path="isTerms" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1" />
                    <div path="dup" style="color:#F23B26" class="waring_Label col-lg-offset-3 col-xs-offset-1">${entryForm.dup}</div>
                </div>

                <%--<div class="form-check" style="float: unset;font-weight: normal;">--%>
                    <%--<form:checkbox path="isEntry" label="" checked="checked"/>&nbsp;JAPAN MOVE UP WEST the 5th anniversary special event同時応募--%>
                <%--</div>--%>

                <div class="form-check" style="float: unset;font-weight: normal;">
                    <form:checkbox path="isTerms" label="" /><a href="/terms/">&nbsp;利用規約</a>&<a href="/privacy/">プライバシポリシー</a>に同意する
                </div>

                <button type="submit" class="confirmBtn" name="confirm" value="Confirm">入力内容を確認する</button>
            </div>
        </div>
    </section>

    <section id="footer_mv">
        <div class="footer_font">Copyright JAPAN MOVE UP WEST All Rights Reserved.</div>
    </section>
</form:form>
</body>
</html>