<%--
  Created by IntelliJ IDEA.
  User: Wangyan
  Date: 2018/02/28
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <jsp:include page="sidebar.jsp" flush="true"/>
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
                        <h3>RISE管理</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>RISE <small>検索結果</small>
                            </h2>
                            <div class="clearfix"></div>
                        </div>

                        <div class="x_content">
                            <div class="col-md-9 col-sm-9 col-xs-12"></div>
                            <div class=" col-sm-1 col-sm-1 col-xs-12">
                                <a class="btn btn-round btn-success"
                                   href="${pageContext.request.contextPath}/admin/rise/insert/"
                                   style="width: 100px;"><span class="glyphicon glyphicon-plus"
                                                               aria-hidden="true"></span>新規</a>
                            </div>
                            <div>

                                <form:form modelAttribute="RiseRegistForm"
                                           class="form-horizontal form-label-left input_mask">
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">アーティスト名前</label>
                                    <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                        <input type="text" class="form-control has-feedback-left" id="inputSuccess2"
                                               placeholder="アーティスト名前">
                                        <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                                    </div>
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">RISEカテゴリ</label>
                                    <div class="col-md-1 col-sm-1 col-xs-12 form-group has-feedback">
                                        <select class="form-control has-feedback-left" style="width: 150px; -webkit-appearance: none; padding-left: 45px;">
                                            <option>ALL</option>
                                            <option>STREET</option>
                                            <option>ATHLETE</option>
                                            <option>ARTIST</option>
                                            <option>NEXTER</option>
                                            <option>GIRLS</option>
                                        </select>
                                        <span class="fa fa-star-half-o form-control-feedback left"
                                              aria-hidden="true"></span>
                                    </div>
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">事務所</label>
                                    <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                        <input type="text" class="form-control has-feedback-left" placeholder="事務所">
                                        <span class="fa fa-building-o form-control-feedback left"
                                              aria-hidden="true"></span>
                                    </div>
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">TOP表示</label>
                                    <div class="col-md-1 col-sm-1 col-xs-12 form-group has-feedback">
                                        <select class="form-control has-feedback-left" style="width: 150px; -webkit-appearance: none; padding-left: 45px;" >
                                            <option>ALL</option>
                                            <option>なし</option>
                                            <option>あり</option>
                                        </select>
                                        <span class="fa fa-eye form-control-feedback left" aria-hidden="true"></span>
                                    </div>
                                    <div class=" col-sm-1 col-sm-1 col-xs-12"></div>
                                    <div class=" col-sm-1 col-sm-1 col-xs-12">
                                        <a class="btn btn-round btn-info " style="width: 100px;"><span
                                                class="glyphicon glyphicon-search" aria-hidden="true"></span>検索</a>
                                    </div>
                                </form:form>
                            </div>
                            <div class="table-responsive">  <!-- todo LIKE -->
                                <table class="table table-striped jambo_table bulk_action">
                                    <thead>
                                    <tr class="headings">
                                        <th class="column-title">アーティスト名前</th>
                                        <th class="column-title">RISEカテゴリ</th>
                                        <th class="column-title">LIKE</th>
                                        <th class="column-title">事務所</th>
                                        <th class="column-title">TOP表示あり/なし</th>
                                        <th class="column-title" width="15%"></th>
                                        <th class="bulk-actions" colspan="7">
                                            <a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span
                                                    class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${riseList}" var="rise">
                                        <tr class="even pointer">
                                            <td class=" "><c:out value="${rise.name1}"/></td>
                                            <td class=" ">
                                                <c:if test="${rise.category==1}">
                                                    <button type="button" class="btn btn-primary" style="width: 80px;">
                                                        STREET
                                                    </button>
                                                </c:if>
                                                <c:if test="${rise.category==2}">
                                                    <button type="button" class="btn btn-danger" style="width: 80px;">
                                                        ATHLETE
                                                    </button>
                                                </c:if>
                                                <c:if test="${rise.category==3}">
                                                    <button type="button" class="btn btn-success" style="width: 80px;">
                                                        ARTIST
                                                    </button>
                                                </c:if>
                                                <c:if test="${rise.category==4}">
                                                    <button type="button" class="btn btn-dark" style="width: 80px;">
                                                        NEXTER
                                                    </button>
                                                </c:if>
                                                <c:if test="${rise.category==5}">
                                                    <button type="button" class="btn btn-info" style="width: 80px;">
                                                        GIRLS
                                                    </button>
                                                </c:if>
                                            </td>
                                            <td class=" ">121000210</td>
                                            <td class=" "><c:out value="${rise.office}"/></td>
                                            <td class=" ">
                                                <c:choose>
                                                    <c:when test="${rise.sortScore!=0}">
                                                        あり
                                                    </c:when>
                                                    <c:otherwise>
                                                        なし
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="">

                                                <button class="btn btn-primary btn-sm" path="id"
                                                        href="${pageContext.request.contextPath}/admin/rise/list/update/">
                                                    修正
                                                </button>
                                                <button class="btn btn-danger btn-sm">削除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                                <div class="col-md-9 col-sm-9 col-xs-12"></div>
                                <div class="col-md-3 col-sm-3 col-xs-12">
                                    <ul class="pagination">
                                        <li><a href="#">&laquo;</a></li>
                                        <li><a href="#">1</a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">&raquo;</a></li>
                                    </ul>
                                </div>
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

<!-- iCheck -->
<script src="/static/admin/vendors/iCheck/icheck.min.js"></script>
<!-- Datatables -->
<script src="/static/admin/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="/static/admin/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="/static/admin/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script src="/static/admin/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
<script src="/static/admin/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
<script src="/static/admin/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="/static/admin/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="/static/admin/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script src="/static/admin/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
<script src="/static/admin/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script src="/static/admin/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
<script src="/static/admin/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
<script src="/static/admin/vendors/jszip/dist/jszip.min.js"></script>
<script src="/static/admin/vendors/pdfmake/build/pdfmake.min.js"></script>
<script src="/static/admin/vendors/pdfmake/build/vfs_fonts.js"></script>

</body>
</html>

