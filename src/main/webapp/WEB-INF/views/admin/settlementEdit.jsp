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

    <link href="/static/admin/vendors/bootstrap-datetimepicker-master/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"
          rel='stylesheet' type='text/css'>

    <style type="text/css">
        .dropdown-menu {
            width: 218px;
            align-content: center;
        }
        #settlementListTable_info{
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
                        <%--<h3>${shopUUID}</h3>--%>
                    </div>
                    <div class="title_right">
                        <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                            <div class="input-group">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <%--<h2>${shopUUID}--%>
                                    <%--<small>検索結果</small>--%>
                                <%--</h2>--%>

                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <%--<div class="col-md-12 col-sm-12 col-xs-12"><p>検索条件：</p></div>--%>


                                </p>
                                <form id="settlementForm" class="form-horizontal form-label-left input_mask">
                                    <div class="col-md-12 col-sm-9 col-xs-12">
                                        <div class="col-md-8 col-sm-9 col-xs-12">
                                            <label class="control-label col-md-2 col-sm-1 col-xs-12">管理者の名前:</label>
                                            <label class="control-label col-md-2 col-sm-1 col-xs-12" style="text-align: left" id="customerName"></label>
                                            <div class="col-md-2 col-sm-9 col-xs-12"></div>
                                            <label class="control-label col-md-2 col-sm-1 col-xs-12">店舗名:</label>
                                            <label class="control-label col-md-3 col-sm-1 col-xs-12" style="text-align: left" id="shopName"></label>

                                        </div>
                                        <label class="control-label col-md-1 col-sm-1 col-xs-12">日付</label>
                                        <div class="col-md-2 col-sm-2 col-xs-12 form-group">
                                            <span class="fa fa-calendar form-control-feedback left"
                                                  aria-hidden="true"></span>
                                            <form:input path="mon" value="" type="text" class="form-control form_datetime has-feedback-left"
                                                        autocomplete="off" style="padding-right: 0;" id="mon" />
                                        </div>
                                        <div class=" col-md-1 col-sm-1 col-xs-12">
                                            <button type="submit" class="btn btn-round btn-info "
                                                    style="width: 100px;"><span
                                                    class="glyphicon glyphicon-search" aria-hidden="true"></span>検索
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <div class="table-responsive col-md-12 col-sm-2 col-xs-12 form-group">

                                    <table id="settlementListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th class="column-title" style="width: 13%;">管理者の名前</th>
                                            <th class="column-title" style="width: 8%;">店舗名</th>
                                            <th class="column-title" style="width: 8%;">店舗のUUID</th>
                                            <th class="column-title" style="width: 8%;">TYPE</th>
                                            <th class="column-title" style="width: 8%;">消費金額</th>
                                            <th class="column-title" style="width: 8%;">支払い状態</th>
                                            <th class="column-title" style="width: 8%;">支払い方式</th>
                                            <th class="column-title" style="width: 10%;">title</th>
                                            <th class="column-title" style="width: 20%;">content</th>
                                            <th class="column-title" style="width: 10%;">日付を作成</th>
                                        </tr>
                                        </thead>
                                    </table>
                                    <div class="col-md-9 col-sm-9 col-xs-12"></div>
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
<div class="modal fade delete-quote-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">Delete</h4>
            </div>
            <div class="modal-body">
                <h4>Are you sure delete this object?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="confirmDeleteClick()">Confirm</button>
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

<script type="text/javascript"
        src="/static/admin/vendors/bootstrap-datetimepicker-master/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
        src="/static/admin/vendors/bootstrap-datetimepicker-master/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.ja.js"></script>

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>

<script>


    $('#mon').datetimepicker({
        language: 'ja',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: "true",
        startView: 3,
        todayBtn : true
    });

    var $settlementForm = $("#settlementForm");
    $settlementForm.validate({
        submitHandler: function (form) {
            searchData();
        },
        errorClass: "validatorError",
        rules: {}
    });


    var datatable = $('#settlementListTable').DataTable({
        dom: 'lrtip',
        padding:false,
        processing: true,
        bLengthChange: true,
        bPaginate: false,
        bSort: false,
        ajax: {
            url: '/admin/settlement/edit/',
            type: 'POST',
            data: function (data) {
                data.mon = $("#mon").val();
                data.shopUUID ="${shopUUID}";
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[3, 'desc']],
        columns: [
            {data: "customerName",orderable: false, name: "login_name"},
            {data: "shopName", orderable: false,name: "name"},
            {
                data: "shopUUID", orderable: false,name: "uuid"
            },
            {data: "type", orderable: false,
                render: function (data, type, full, meta) {
                    $("#customerName").html(full.customerName);
                    $("#shopName").html(full.shopName);
                    if (data == 1) {
                        return 'クーポン';
                    } else if (data == 2) {
                        return 'スコア';
                    } else if (data == 3) {
                        return '広告';
                    } else if (data == 4) {
                        return '毎月の費用';
                    } else if (data == 5) {
                        return '子屋';
                    }
                    return '';
                },name: "type"},
            {data: "amount", name: "amount"},
            {
                data: "status",
                render: function (data, type, full, meta) {
                    if (data == 0) {
                        return '未支払い';
                    } else if (data == 1) {
                        return '支払完了';
                    }
                    return '';
                },
                name: "status"
            },
            {
                data: "method",
                render: function (data, type, full, meta) {
                    if (data == 1) {
                        return 'クレジットカード';
                    } else if (data == 2) {
                        return '銀行口座';
                    }else if (data == 3) {
                        return '銀行振込';
                    }
                    return '';
                },
                name: "method"
            },
            {data: "title", orderable: false, name: "title"},
            {data: "content", orderable: false, name: "content"},
            {data: "updateDatetime",
                render: function (data, type, full, meta) {
                    return '<div>' + formatDate(data) + '</div>';
                }, name: "updateDatetime"}

        ],
        columnDefs: [
            {
                targets: [5, 6],
                searchable: false,
                orderable: false
            }
        ],
        language: {url: "/static/admin/build/json/japanese.json"}
    });


    function searchData() {
        datatable.ajax.reload();
    }

    function gotoUpdate(uuid,month) {
        $('#newsLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/settlement/edit/' + uuid + '/'+month+'/';
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
            if(year == "1970" && month== "01" && day == "01"){
            }else{
                rtn = year + "-" + month.substring(month.length - 2,month.length) + "-" + day.substring(day.length - 2,day.length);
            }
            return rtn;
        }
    }

</script>


</body>
</html>
