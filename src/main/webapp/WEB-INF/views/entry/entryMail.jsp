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
<form:form modelAttribute="mailForm" action="${pageContext.request.contextPath}/mail/">
    <section id="logo">
        <div class="container">
            <div class="row col-lg-12 col-xs-10" align="center">
                <img class="img-responsive" src="/static/img/logo.png">
                <p class="table_Title">会員登録フォーム</p>
            </div>

            <div class="row col-lg-12 col-xs-12" align="center" style="height: 600px">
                <table border="1" class="table_List" >

                    <!-----------seventh-------->
                    <tr>
                        <td class="table_Height col-lg-5 col-xs-7">
                            <span class="phone_mail_Label">メール(ID)</span>
                            <span class="pc_mail_Label">メールアドレス(ID)</span>
                        <td>
                            <div class="col-lg-7 col-xs-6">
                                <form:input path="mail" cssClass="form-control-mail"/>
                            </div>
                        </td>
                    </tr>

                </table>
                <div class ="error">
                    <f:errors path="mail" element="div" cssStyle="color:#F23B26" class="waring_Label col-lg-offset-4 col-xs-offset-1" />
                    <div path="dup" style="color:#F23B26" class="waring_Label col-lg-offset-4 col-xs-offset-1">${mailForm.dup}</div>
                </div>
                <button type="submit" class="confirmBtn">登録情報を入力する</button>
            </div>
        </div>
    </section>

    <section id="footer_mv">
        <div class="footer_font">Copyright JAPAN MOVE UP WEST All Rights Reserved.</div>
    </section>
</form:form>
</by>
</html>