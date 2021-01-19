<%--
  Created by IntelliJ IDEA.
  User: xieyoujun
  Date: 2017/11/20
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<br>
<head>
    <meta charset="utf-8">
    <title>Penguin</title>

    <!-- Bootstrap core CSS -->
    <%--<link href="/bootstrap/css/bootstrap.css" rel="stylesheet">//TODO: imp static resources.--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<br>
<h1>This is a cookbook for Spring + iMybatis</h1>

----------------------<br>
<P>■一件取得</P>
http://localhost:8080/demo/1/
<h2>${demo.name}</h2>

----------------------<br>
<P>■全件取得</P>
http://localhost:8080/demo/</br>
<c:forEach items="${demos_all}" var="demo">
    <c:out value="${demo.id}:${demo.name}"></c:out>
    <a href="${pageContext.request.contextPath}/demo/update/${demo.id}/">編集</a>
    /
</c:forEach>
</br>

----------------------<br>

<P>■DTO取得</P>
http://localhost:8080/demo/dto/1/</br>
<h2>${demos_dto.name}</h2>

----------------------<br>
<P>■Form送信(追加の処理)</P>
http://localhost:8080/demo/insert/</br>

----------------------<br>
<P>■Form送信(削除の処理)</P>
http://localhost:8080/demo/delete/


</body>
</html>