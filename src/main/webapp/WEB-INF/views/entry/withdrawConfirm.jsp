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
<form:form modelAttribute="withdrawForm" action="${pageContext.request.contextPath}/withdraw/">
<section id="logo">
    <div class="container">
        <div class="row col-lg-12 col-xs-10" align="center">
            <img class="img-responsive" src="/static/img/logo.png">
            <p class="table_Title">会員退会フォーム</p>
        </div>
        <div align="center">
            <div class="info_row">
                <label class="info_Label">下記のメール本当に退会されますか？</label> </br> </br>
                <form:label path="mail">${withdrawForm.mail}</form:label>
                <div class="mail">
                    <form:hidden path="mail"/>
                </div>
            </div>
        </div>
        <div align="center">
            <button type="submit" class="confirmBtn" name="yes" value="yes" style="margin-right: 10px">退会します</button>
            <button type="submit" class="confirmBtn" name="no" value="no" style="margin-left: 10px">退会しません</button>
        </div>
    </div>
</section>

<section id="footer_mv">
    <div class="footer_font">Copyright JAPAN MOVE UP WEST All Rights Reserved.</div>
</section>
</form:form>
</by>
</html>