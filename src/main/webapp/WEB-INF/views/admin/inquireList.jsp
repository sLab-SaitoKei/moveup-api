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
    <!-- Datatables -->
    <link href="/static/admin/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="/static/admin/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="/static/admin/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="/static/admin/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="/static/admin/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
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
                        <h3>お問い合わせ管理</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>お問い合わせ
                                    <small>検索結果</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">
                                <div class="col-md-2 col-sm-1 col-xs-12"><p>検索条件：</p></div>
                                <!-- todo detatime -->
                                <form class="form-horizontal form-label-left input_mask" style="margin-bottom: 20px">
                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">日付</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group" >
                                                <span class="fa fa-calendar form-control-feedback left" aria-hidden="true"></span>
                                                <input type="date" id="date" class="form-control has-feedback-left" style="padding-right: 0;" value="">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" >タイプ</label>
                                            <div class="col-md- col-sm-2 col-xs-12 form-group has-feedback" style="width: 282px;">
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                                <select type="select" id="type" class="form-control" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="">全部</option>
                                                    <option value="1">ユーザー</option>
                                                    <option value="2">協力店</option>
                                                </select>
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">メール</label>
                                            <div class="col-md-3 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="mail">
                                            </div>
                                        </div>
                                        <div class="col-md-2 text-right" style="padding-right: 15px">
                                            <div style="width: 100px;display: inline-block;">
                                                <a class="btn btn-round btn-info " style="width: 100px;margin-top: 5px" onclick="searchData()">
                                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>検索</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <%--<div class=" col-sm-1 col-sm-1 col-xs-12">--%>
                                    <%--<a class="btn btn-round btn-success"  onclick="gotoRegist()" style="width: 100px;"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新規</a>--%>
                                <%--</div>--%>
                                <%--<form:form modelAttribute="shop" >--%>
                                <div class="table-responsive">
                                    <table id="inquireListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th class="column-title">日付</th>
                                            <th class="column-title">メルアドレス </th>
                                            <th class="column-title">タイプ </th>
                                            <th class="column-title">内容 </th>
                                            <th class="column-title" width="73px"></th>
                                            <th class="column-title" width="73px"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <div class="col-md-9 col-sm-9 col-xs-12"></div>
                                    <div class="col-md-3 col-sm-3 col-xs-12">
                                    </div>
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
<div id="Loading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>
<div class="modal fade delete-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span--%>
                        <%--aria-hidden="true">×</span>--%>
                <%--</button>--%>
                <%--<h4 class="modal-title" id="myModalLabel2">Delete</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--<h4>この情報を削除しますか？</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>--%>
                <%--<button type="button" class="btn btn-primary" onclick="confirmDeleteClick()">削除</button>--%>
            <%--</div>--%>

        </div>
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
<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>
<script>
    var datatable = $('#inquireListTable').DataTable({
        dom:'lrtip',
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/inquire/list/',
            type: 'POST',
            data: function (data) {
                data.date = $('#date').val();
                data.type = $('#type').val();
                data.mail = $('#mail').val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[ 1, 'asc' ]],
        order: [[ 0, 'desc' ]],
        columns: [
            { data: "date", name: "create_datetime"},
            { data: "mail", name: "mail" },
            { data: "typeStr", name: "type" },
            { data: "content", name: "contents" , orderable: false},
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    if( full.status==1){
                        return '<a class="btn btn-success" style="font-size: inherit;" href="javascript:void(0)" >対応済み</a>';
                    }
                    return "";
                },
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<a class="btn btn-primary" style="font-size: inherit;float: right;" href="javascript:void(0)" onclick="gotoUpdate('+full.id+')">詳細</a>';
                }
            },
            // {
            //     data: null,
            //     orderable: false,
            //     render: function (data, type, full, meta) {
            //         return '<button class="btn btn-danger" style="font-size: inherit;float: right;" onclick="gotoDelete('+full.id+')">削除</button>';
            //     }
            // }
        ],
        columnDefs: [
            {
                // targets: [3,4],
                // searchable: false,
                // orderable: false
            }
        ],
        language: {url: "/static/admin/build/json/japanese.json"}
    });

    var currDeleteId = 0;

    function searchData() {
        datatable.ajax.reload();
    }

    function gotoUpdate(id) {
        $('#recruitLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/inquire/detail/' + id + '/';
    }

    function gotoRegist() {
        $('#recruitLoading').show();
        window.location.href =  '${pageContext.request.contextPath}/admin/inquire/regist/';
    }

    function gotoDelete(id) {
        currDeleteId = id;
        $('.delete-modal').modal("show");
    }
    function confirmDeleteClick() {
        $('.delete-modal').modal("hide");
        $('#Loading').show();
        var form = $("<form></form>");
        form.attr('action', '${pageContext.request.contextPath}/admin/inquire/delete/');
        form.attr('method', 'post');
        var input1 = $("<input type='hidden' name='id'/>");
        input1.attr('value', currDeleteId);
        form.append(input1);
        form.appendTo("body");
        form.css('display', 'none');
        form.submit();
    }
</script>
</body>
</html>
