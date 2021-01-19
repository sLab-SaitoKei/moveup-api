<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%request.setAttribute("vEnter", "\\n");%>
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
    <link href="/static/admin/vendors/bootstrap-fileinput/css/fileinput2.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
</head>
<style>

    #status_check + label{
        background-color: #fffff;
        border: 1px solid #259656;
        box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05);
        padding: 10px;
        border-radius: 3px;
        display: inline-block;
        position: relative;
        vertical-align: middle;
    }
    #status_check:checked + label{
        background-color: #259656;
        border: 1px solid #259656;
        box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -12px 10px -12px rgba(0,0,0,0.05), inset 12px 10px -12px rgba(255,255,255,0.1);
        color: #99a1a7;
    }
    #status_check:checked + label:after{
        /*content: '\2714';*/
        /*font-size: 22px;*/
        /*position: absolute;*/
        /*top: -1px;*/
        /*left: 1px;*/
        /*color: #ffffff;*/
        content: '\00a0';
        display: inline-block;
        border: 4px solid #fff;
        border-top-width: 0;
        border-right-width: 0;
        width: 18px;
        height: 10px;
        -webkit-transform: rotate(-50deg);
        position: absolute;
        top:4px;
        left:1px;
    }
</style>
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
                        <h3>お問い合わせ管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>お問い合わせ基本情報

                                </h2>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br/>
                               <form:form modelAttribute="form" id="" enctype="multipart/form-data"
                                          action="${pageContext.request.contextPath}/admin/inquire/update/" class="form-horizontal form-label-left" method="post">

                                   <div class="item form-group">
                                       <form:input type="hidden" id="id" path="id" value="${form.id}"/>
                                   </div>
                                   <div class="item form-group" id="" >
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">日付
                                           <span class="required">:</span>
                                       </label>
                                       <div class="control-label col-md-1 col-sm-6 col-xs-12" style="text-align: left">
                                           <label><c:out value="${form.date}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">メールアドレス:
                                       </label>
                                       <div class="control-label col-md-2 col-sm-6 col-xs-12" style="text-align: left">
                                           <label><c:out value="${form.mail}"></c:out></label>
                                       </div>
                                       <label class="control-label col-md-1 col-sm-3 col-xs-12" for="">タイプ <span
                                               class="required">:</span>
                                       </label>
                                       <div class="control-label col-md-1 col-sm-6 col-xs-12"style="text-align: left">
                                           <label><c:out value="${form.typeStr}"></c:out></label>
                                       </div>
                                       <%--<label class="control-label col-md-1 col-sm-3 col-xs-12" for="">メールアドレス:--%>
                                       <%--</label>--%>
                                       <%--<div class="control-label col-md-2 col-sm-6 col-xs-12" style="text-align: left">--%>
                                           <%--<label><c:out value="${form.mail}"></c:out></label>--%>
                                       <%--</div>--%>
                                   </div>
                                   <div class="item form-group">
                                       <hr style="height:1px;border:none;border-top:1px dashed #ccc5bd; width: 55%"/>
                                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="">内容: <span
                                               class="required"></span>
                                       </label>
                                       <div class="control-label col-md-6 col-sm-6 col-xs-12" style="text-align: left">
                                           <label>
                                                    ${fn:replace(form.contents, vEnter,"<br>")}
                                           </label>
                                       </div>
                                   </div>
                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-6 col-md-offset-7">
                                            <label  style="width: 50px; margin-right: 20px;">
                                                <c:if test="${form.status ==0}">
                                                    <input type="checkbox" value="0" name="status" id="status_check"
                                                                   style="line-height: 25px;padding-top: 10px;display:none;"/>
                                                    <label for="status_check" onclick="inquire_status()" value="0" name="status"
                                                           style="line-height: 25px;padding-top: 10px;padding-right: 10px;margin-right: 5px;position:absolute;left:-15px;top: 8px;"></label><span style="position:absolute;left:15px;top: 10px;">対応済み</span>
                                                </c:if>
                                                <c:if test="${form.status ==1}">
                                                    <input type="checkbox"  value="1" name="status" id="status_check" checked="checked"
                                                                   style="line-height: 25px;padding-top: 10px;display:none;"/>
                                                    <label for="status_check" onclick="inquire_status()" value="1" name="status"
                                                           style="line-height: 25px;padding-top: 10px;padding-right: 10px;margin-right: 5px;position:absolute;left:-15px;top: 8px;"></label><span style="position:absolute;left:15px;top: 10px;">対応済み</span>
                                                </c:if>
                                            </label>
                                            <a class="btn btn-primary" href="/admin/inquire/list/" style="width: 100px;">キャンセル</a>
                                            <button id="send" type="submit" class="btn btn-success"
                                                    style="width: 100px;">登録
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
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min2.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>\
<!--shop Scripts -->
<script src="/static/admin/build/js/shopRegist.js"></script>
<script src="/static/admin/build/js/shopEdit.js"></script>

<script>

    function inquire_status() {
        var value =  document.getElementById("status_check").value;
        if(value == 0){
            document.getElementById("status_check").value = "1";
            // document.getElementById('status_check').style.display = 'none';
            // document.getElementById('status_uncheck').style.display = 'block';
            // $("#status").prop("checked",false);
        }else if(value == 1) {
            document.getElementById("status_check").value = "0";
            // document.getElementById('status_check').style.display = 'block';
            // document.getElementById('vLeft').style.display = 'none';
            // $("#status").prop("checked",true);
        }
    }

</script>

</body>
</html>