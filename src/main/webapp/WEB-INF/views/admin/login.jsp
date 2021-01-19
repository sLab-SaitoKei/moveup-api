<%--
  Created by IntelliJ IDEA.
  User: Wangyan
  Date: 2018/03/23
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    <!-- Animate.css -->
    <link href="/static/admin/vendors/animate.css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <img src="/static/img/logo.png" width="100%">
                <form:form modelAttribute="loginForm" action="${pageContext.request.contextPath}/admin/login/" method="post">
                    <h1>Login Form</h1>
                    <p><code>${error}</code></p>
                    <div>
                        <form:input path="loginName" class="form-control" placeholder="Username"  value="${loginForm.loginName}"/>
                    </div>
                    <div>
                        <form:password path="password" class="form-control" placeholder="Password" value="${loginForm.password}" />
                    </div>
                    <div>
                        <button type="submit" class="btn btn-default submit"    >Log in</button>
                        <%--<a class="reset_pass" href="#">Lost your password?</a>--%>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <%--<p class="change_link">New to site?--%>
                            <%--<a href="#signup" class="to_register"> Create Account </a>--%>
                        <%--</p>--%>

                        <%--<div class="clearfix"></div>--%>
                        <%--<br />--%>

                        <div>

                            <p>©2018 All Rights Reserved. Japan Move Up West. </p>
                            <p>Privacy and Terms</p>
                        </div>
                    </div>
                </form:form>
            </section>
        </div>

        <div id="register" class="animate form registration_form">
            <section class="login_content">
                <form>
                    <h1>Create Account</h1>
                    <div>
                        <input type="text" class="form-control" placeholder="Username" required="" />
                    </div>
                    <div>
                        <input type="email" class="form-control" placeholder="Email" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" placeholder="Password" required="" />
                    </div>
                    <div>
                        <a class="btn btn-default submit" href="index.html">Submit</a>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <p class="change_link">Already a member ?
                            <a href="#signin" class="to_register"> Log in </a>
                        </p>

                        <div class="clearfix"></div>
                        <br />

                        <div>
                            <h1><i class="fa fa-paw"></i> Gentelella Alela!</h1>
                            <p>©2016 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and Terms</p>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>
</body>
</html>

