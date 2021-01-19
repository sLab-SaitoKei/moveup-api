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

    <style>
        .form-control {
            box-shadow:none;
        }

    </style>
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <!-- sidebar menu -->
        <jsp:include page="sidebar.jsp" />
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
                        <h3>購入履歴管理</h3>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                    </li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#">Settings 1</a>
                                            </li>
                                            <li><a href="#">Settings 2</a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <br />
                                <form:form modelAttribute="goodsPurchaseMailForm" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/goods/purchase/edit/" class="form-horizontal form-label-left" method="post">
                                    <form:input type="hidden" id="serialNumber" path="serialNumber" value="${goodsPurchaseMailForm.serialNumber}"/>
                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >番号： </label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-3 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.serialNumber}"/></span>
                                            </div>
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >日時： </label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-3 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.date}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >【ご注文内容】</label>
                                        </div>
                                    </div>

                                    <br/>
                                    <c:forEach items="${goodsPurchaseMailForm.goodsInfoForms}" var="goods">

                                        <div class="form-group">
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >注文数： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.quantity}"/>点</span>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >商品名： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.goodsName}"/></span>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >カラー： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.color}"/></span>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >サイズ： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.size}"/></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >小計： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.price}"/>円</span>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-2 col-xs-12">
                                                <label class="control-label col-md-2 col-sm-2 col-xs-12" >消費税： </label>
                                                <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                    <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goods.tax}"/>円</span>
                                                </div>
                                            </div>
                                        </div>

                                        <br/>
                                    </c:forEach>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >送料： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.deliveryExpense}"/>円</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >決算方法： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.purchaseType}"/>払い</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >合計金額： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.totalPrice}"/>円</span>
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >【お客様ご住所】</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" ></label>
                                            <div class="col-md-10 col-sm-12 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.name}"/> 様</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >ご住所 </label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;">〒  <c:out value="${goodsPurchaseMailForm.zipcode}"/></span>
                                            </div>
                                            <div class="col-md-8 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.address}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >電話番号： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.tel}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >メールアドレス： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.mail}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >【配送先ご住所】</label>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" ></label>
                                            <div class="col-md-10 col-sm-12 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.name2}"/> 様</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >ご住所 </label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;">〒  <c:out value="${goodsPurchaseMailForm.zipcode2}"/></span>
                                            </div>
                                            <div class="col-md-8 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.address2}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >電話番号： </label>
                                            <div class="col-md-10 col-sm-2 col-xs-12 form-group has-feedback">
                                                <span class="form-control col-md-12 col-xs-12" style="border: none;"><c:out value="${goodsPurchaseMailForm.tel2}"/></span>
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="form-group">
                                        <div class="col-md-12 col-sm-2 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-1 col-xs-12">購入状態<span class="required">*</span></label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <form:select path="status" class="form-control">
                                                    <c:if test="${goodsPurchaseMailForm.status==1}">
                                                        <form:option value="1">支払完了</form:option>
                                                        <form:option value="2">未支払い</form:option>
                                                        <form:option value="3">出荷が完了</form:option>
                                                        <form:option value="4">返品申請</form:option>
                                                        <form:option value="5">取引完了</form:option>
                                                    </c:if>
                                                    <c:if test="${goodsPurchaseMailForm.status==2}">
                                                        <form:option value="2">未支払い</form:option>
                                                        <form:option value="1">支払完了</form:option>
                                                        <form:option value="3">出荷が完了</form:option>
                                                        <form:option value="4">返品申請</form:option>
                                                        <form:option value="5">取引完了</form:option>
                                                    </c:if>
                                                    <c:if test="${goodsPurchaseMailForm.status==3}">
                                                        <form:option value="3">出荷が完了</form:option>
                                                        <form:option value="4">返品申請</form:option>
                                                        <form:option value="1">支払完了</form:option>
                                                        <form:option value="2">未支払い</form:option>
                                                        <form:option value="5">取引完了</form:option>
                                                    </c:if>
                                                    <c:if test="${goodsPurchaseMailForm.status==4}">
                                                        <form:option value="4">返品申請</form:option>
                                                        <form:option value="1">支払完了</form:option>
                                                        <form:option value="2">未支払い</form:option>
                                                        <form:option value="3">出荷が完了</form:option>
                                                        <form:option value="5">取引完了</form:option>
                                                    </c:if>
                                                    <c:if test="${goodsPurchaseMailForm.status==5}">
                                                        <form:option value="5">取引完了</form:option>
                                                        <form:option value="1">支払完了</form:option>
                                                        <form:option value="2">未支払い</form:option>
                                                        <form:option value="3">出荷が完了</form:option>
                                                        <form:option value="4">返品申請</form:option>
                                                    </c:if>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="ln_solid"></div>
                                    <div class="form-group">
                                        <div class="col-md-2 col-sm-6 col-xs-12"></div>
                                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                            <a href="${pageContext.request.contextPath}/admin/goods/purchase/list/" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>キャンセル</a>
                                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>登録</button>
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
<!-- bootstrap-progressbar -->
<script src="/static/admin/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- iCheck -->
<script src="/static/admin/vendors/iCheck/icheck.min.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="/static/admin/vendors/moment/min/moment.min.js"></script>
<script src="/static/admin/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- bootstrap-wysiwyg -->
<script src="/static/admin/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="/static/admin/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="/static/admin/vendors/google-code-prettify/src/prettify.js"></script>
<!-- jQuery Tags Input -->
<script src="/static/admin/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
<!-- Switchery -->
<script src="/static/admin/vendors/switchery/dist/switchery.min.js"></script>
<!-- Select2 -->
<script src="/static/admin/vendors/select2/dist/js/select2.full.min.js"></script>
<!-- Parsley -->
<script src="/static/admin/vendors/parsleyjs/dist/parsley.min.js"></script>
<!-- Autosize -->
<script src="/static/admin/vendors/autosize/dist/autosize.min.js"></script>
<!-- jQuery autocomplete -->
<script src="/static/admin/vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
<!-- starrr -->
<script src="/static/admin/vendors/starrr/dist/starrr.js"></script>
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
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

