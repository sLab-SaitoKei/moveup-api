<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <link rel="shortcut icon" type="image/x-icon" href="/static/img/fav.ico">

</head><!--/head-->


<body id="Login" style="padding-top: 30px">
<form:form modelAttribute="entryForm">
    <section id="logo">
        <div class="container">
            <div class="row col-lg-12 col-xs-10" align="center">
                <img class="img-responsive" src="/static/img/logo.png">
                <p class="table_Title">会員登録フォーム</p>
                <img class="img-responsive" src="/static/img/head_statue2.png">
            </div>

            <div class="row col-lg-12 col-xs-12" align="center">
                <table border="1" class="table_List">
                    <!-----------/first-------->
                    <tr>
                        <td class="table_Height  col-lg-4 col-xs-6">氏名（漢字）<span class="table_icon">必須</span><span
                                class="table_icon_small">*</span></td>
                        <td>
                            <!-----------/FamilName TextFiled-------->
                            <form:label path="firstName"
                                        class="defult_Label">${entryForm.firstName}&nbsp;${entryForm.secondName}</form:label>
                            <!-----------/GivenName TextFiled-------->
                            <div class="hide_frame">
                                <form:hidden path="firstName"/>
                                <form:hidden path="secondName"/>
                            </div>

                        </td>
                    </tr>
                    <!-----------/second-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">氏名（カナ）<span class="table_icon">必須</span><span
                                class="table_icon_small">*</span></td>
                        <td>

                            <!-----------/FamilName TextFiled-------->
                            <form:label path="firstName"
                                        class="defult_Label">${entryForm.firstNameKana}&nbsp;${entryForm.secondNameKana}</form:label>
                            <div class="hide_frame">
                                <form:hidden path="firstNameKana"/>
                                <form:hidden path="secondNameKana"/>
                            </div>
                        </td>
                    </tr>
                    <!-----------third-------->
                    <tr>
                        <td class="table_Height col-lg-4 col-xs-6">性别<span class="table_icon">必須</span><span
                                class="table_icon_small">*</span></td>
                        <td>
                            <div class="radio">
                                    <form:label path="genderText"
                                                cssClass="defult_sex_Label">${entryForm.genderText}</form:label>
                                    <form:hidden path="gender"/><br>
                            </div>
                         </td>
                     </tr>
            <!-----------forth-------->
            <tr>
                <td class="table_Height col-lg-4 col-xs-6">生年月日<span class="table_icon">必須</span><span
                        class="table_icon_small">*</span></td>
                <td>
                    <form:label path="birthyear" class="defult_work_Label">${entryForm.birthyear}</form:label>
                    <form:hidden path="birthyear"/>
                    <span class="select_Name" class="select_Name">年</span>
                    <form:label path="birthmonth" class="defult_time_Label">${entryForm.birthmonth}</form:label>
                    <form:hidden path="birthmonth"/>
                    <span class="select_Name">月</span>
                    <form:label path="birthday" class="defult_time_Label">${entryForm.birthday}</form:label>
                    <form:hidden path="birthday"/>
                    <span class="select_Name">日</span>
                </td>
            </tr>
            <!-----------fifth-------->
            <tr>
                <td class="table_Height col-lg-4 col-xs-6">職業<span class="table_icon">必須</span><span
                        class="table_icon_small">*</span></td>
                <td>
                    <form:label path="occupation" class="defult_work_Label">${entryForm.occupationText}</form:label>
                    <form:hidden path="occupation"/><br>
                </td>
            </tr>
            <!-----------sixth-------->
            <tr>
                <td class="table_Height col-lg-4 col-xs-6">居住エリア<span class="table_icon">必須</span><span
                        class="table_icon_small">*</span></td>
                <td>
                    <div class="form-inline" style="margin-top: 5px">
                        <span class="code_logo_Label">〒</span>
                        <form:label path="zipcode1" cssClass="code_Label">${entryForm.zipcode1}</form:label>
                        <form:hidden path="zipcode1"/>
                    </div>
                    <div class=" col-lg-12" style="margin-bottom: 5px">
                        <form:label path="address" cssClass="adress_Label">${entryForm.address}</form:label>
                        <form:hidden path="address"/>
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
                    <div class="col-lg-8">
                        <form:label path="mail" class="mail_Label">${entryForm.mail}</form:label>
                        <form:hidden path="mail"/><br>
                    </div>
                </td>
            </tr>
            <!-----------eighth-------->
            <tr>
                <td class="table_Height col-lg-4 col-xs-6">パスワード<span class="table_icon">必須</span>
                    <span class="table_icon_small">*</span></td>
                <td>
                    <div class="col-lg-8">
                        <form:label path="password" class="password_Label">${entryForm.password}</form:label>
                        <form:hidden path="password"/><br>
                    </div>
                </td>
            </tr>
            <!-----------ninth-------->
            <tr>
                <td class="table_Height col-lg-4 col-xs-6">ニックネーム<span class="table_icon"
                                                                       style="background-color: #00a7dd">任意</span></td>
                <td>
                    <div class="col-lg-8">
                        <form:label path="nickname" class="nickname_Label">${entryForm.nickname}</form:label>
                        <form:hidden path="nickname"/><br>
                    </div>
                </td>
            </tr>
            </table>
            </br>
            <%--<div class="col-lg-8" style="float: unset;font-weight: normal;">--%>
                <%--<form:label path="isEntry" class="isEntry_Label">${entryForm.isEntryText}</form:label>--%>
                <%--<form:hidden path="isEntry"/><br>--%>
            <%--</div>--%>

            <button type="submit" class="confirmBtn">応募する</button>
        </div>
        </div>
    </section>

    <section id="footer_mv">
        <div class="footer_font">Copyright JAPAN MOVE UP WEST All Rights Reserved.</div>
    </section>
</form:form>
</body>
</html>