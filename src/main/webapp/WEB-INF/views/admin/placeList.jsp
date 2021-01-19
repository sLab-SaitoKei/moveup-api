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
    <link href="/static/admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">

    <style>
        .validatorError {
            color: red;
        }
        .dark{
            display: none;
        }
        .alert-info{
            display: none;
        }
    </style>
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
                        <h3>プレイス管理</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>プレイス
                                    <small>検索結果</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">

                                <div class="col-md-2 col-sm-1 col-xs-12"><p>検索条件： </p></div>
                                <!-- todo detatime -->

                                <div class="col-md-9 col-sm-9 col-xs-12"></div>
                                <div class=" col-sm-1 col-sm-1 col-xs-12" >
                                    <a class="btn btn-round btn-success"  onclick="gotoRegist()" style="width: 100px;"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新規</a>
                                </div>
                                </p>
                                <form id="userForm" class="form-horizontal form-label-left input_mask" method="post">
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">名所</label>
                                    <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                        <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                        <input type="text" class="form-control has-feedback-left" id="name" data-rule-maxlength="20">
                                    </div>
                                    <label class="control-label col-md-1 col-sm-1 col-xs-12">エリア</label>
                                    <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                        <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                        <input type="text" class="form-control has-feedback-left" id="location" data-rule-maxlength="20">
                                    </div>
                                    <div class=" col-sm-1 col-sm-1 col-xs-12" style="float: right;">
                                        <button type="submit" class="btn btn-round btn-info " style="width: 100px;"><span
                                                class="glyphicon glyphicon-search" aria-hidden="true"></span>検索</button>
                                    </div>
                                </form>
                                <%--<form:form modelAttribute="shop" >--%>
                                <div class="table-responsive">
                                    <table id="placeListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th class="column-title">名所 </th>
                                            <th class="column-title">エリア </th>
                                            <th class="column-title">電話 </th>
                                            <th class="column-title">アドレス</th>
                                            <th class="column-title" style="width: 11%;">掲載開始</th>
                                            <th class="column-title" style="width: 11%;">掲載終了</th>
                                            <th class="column-title" width="73px"></th>
                                            <th class="column-title" width="73px"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <div class="col-md-9 col-sm-9 col-xs-12"></div>
                                    <div class="col-md-3 col-sm-3 col-xs-12">
                                        <%--<ul class="pagination">--%>
                                            <%--<li><a href="#">&laquo;</a></li>--%>
                                            <%--<li><a href="#">1</a></li>--%>
                                            <%--<li><a href="#">2</a></li>--%>
                                            <%--<li><a href="#">3</a></li>--%>
                                            <%--<li><a href="#">4</a></li>--%>
                                            <%--<li><a href="#">5</a></li>--%>
                                            <%--<li><a href="#">&raquo;</a></li>--%>
                                        <%--</ul>--%>
                                    </div>
                                </div>
                                <%--</form:form>--%>
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

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">Delete</h4>
            </div>
            <div class="modal-body">
                <h4>この情報を削除しますか？</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
                <button type="button" class="btn btn-primary" onclick="confirmDeleteClick()">削除</button>
            </div>

        </div>
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
<!-- PNotify -->
<script src="/static/admin/vendors/pnotify/dist/pnotify.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.nonblock.js"></script>
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
    var $userForm = $("#userForm");
    $userForm.validate({
        submitHandler: function(form) {
            searchData();
        },
        errorClass : "validatorError"
    });

    var datatable = $('#placeListTable').DataTable({
        dom:'lrtip',
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/place/list/',
            type: 'POST',
            data: function (data) {
                data.name = $('#name').val();
                data.location = $('#location').val();
                data.tel = $('#tel').val();
                data.address = $('#address').val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[ 1, 'asc' ]],
        columns: [
            { data: "name", name: "name", orderable: false},
            { data: "location", name: "location" },
            { data: "tel", name: "tel" },
            { data: "address", name: "address" },
            {
                data:"publishStart",
                name:"publishStart",
                render:function(data,type,full,meta){
                    return formatDate(data);
                },orderable: true
            },
            {
                data:"publishEnd",
                name:"publishEnd",
                render:function(data,type,full,meta){
                    return formatDate(data);
                },orderable: true
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<a class="btn btn-primary" style="font-size: inherit;float: right;" href="javascript:void(0)" onclick="gotoUpdate('+full.placeListId+')">詳細</a>';
                }
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<button class="btn btn-danger" style="font-size: inherit;float: right;" onclick="gotoDelete('+full.placeListId+')">削除</button>';
                }
            }
        ],
        columnDefs: [
            {
                targets: [4,5],
                searchable: false,
                orderable: false
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
        window.location.href = '${pageContext.request.contextPath}/admin/place/edit/' + id + '/';
    }

    function gotoRegist() {
        $('#recruitLoading').show();
        window.location.href =  '${pageContext.request.contextPath}/admin/place/regist/';
    }

    function gotoDelete(id) {
        currDeleteId = id;
        $('.delete-modal').modal("show");
    }
    function confirmDeleteClick() {
        $('.delete-modal').modal("hide");
        $('#Loading').show();
        var form = $("<form></form>");
        form.attr('action', '${pageContext.request.contextPath}/admin/place/delete/');
        form.attr('method', 'post');
        var input1 = $("<input type='hidden' name='id'/>");
        input1.attr('value', currDeleteId);
        form.append(input1);
        form.appendTo("body");
        form.css('display', 'none');
        form.submit();
    }

    function formatDate(timestamp) {
        var rtn = "";
        if(timestamp == null){
            return rtn;
        }else{
            var date = new Date(timestamp);
            var year = date.getFullYear();
            var month = "0" + (date.getMonth() + 1);
            var day = "0" + date.getDate();
            if(year == "1970" && month== "01" && day == "00"){
                rtn = year + "-" + month.substring(month.length - 2,month.length) + "-" + day.substring(day.length - 2,day.length);
            }else{
                rtn = year + "-" + month.substring(month.length - 2,month.length) + "-" + day.substring(day.length - 2,day.length);
            }
            return rtn;
        }
    }

    var errorCode = ${errorCode};

    if(errorCode == 1){
        new PNotify({
            title: 'エラーが発生しました',
            text: 'データの更新に失敗しました。',
            type: 'error',
            styling: 'bootstrap3'
        });
    }
</script>
</body>
</html>
