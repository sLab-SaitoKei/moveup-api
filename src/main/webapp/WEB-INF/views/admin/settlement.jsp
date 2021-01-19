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

        #settlementListTable_processing{
            font-family: 'Montserrat', sans-serif;
            background-color: rgb(80, 80, 80, 0.5);
            color: #000;
            position: fixed;
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            top: 0;
            left: 0;
            -webkit-transition: 2s ease-in-out;
            -moz-transition: 2s ease-in-out;
            -o-transition: 2s ease-in-out;
            -ms-transition: 2s ease-in-out;
            transition: 2s ease-in-out;
            z-index: 999;


        }
        #hahaha{
            position: absolute;
            top: 65%;
            font-size: 25px;
            text-transform: uppercase;
            letter-spacing: 3px;
            padding-left: 50%;
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
                        <h3>請求情報</h3>
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
                                <h2>請求情報
                                    <small>検索結果</small>
                                </h2>

                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-12 col-sm-12 col-xs-12"><p>検索条件：</p></div>


                                </p>
                                <form id="settlementForm" class="form-horizontal form-label-left input_mask">
                                    <div class="col-md-12 col-sm-9 col-xs-12">
                                        <div class="col-md-11 col-sm-9 col-xs-12">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">管理者の名前</label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <input type="text" class="form-control has-feedback-left" name="selCusName"
                                                       id="selCusName">
                                                <span class="fa fa-fire form-control-feedback left"
                                                      aria-hidden="true"></span>
                                            </div>

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">店舗名</label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <input type="text" class="form-control has-feedback-left" name="selShopName"
                                                       id="selShopName">
                                                <span class="fa fa-fire form-control-feedback left"
                                                      aria-hidden="true"></span>
                                            </div>

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">店舗のUUID</label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <input type="text" class="form-control has-feedback-left" id="selUUID" name="selUUID" >
                                                <span class="fa fa-fire form-control-feedback left"
                                                      aria-hidden="true"></span>
                                            </div>



                                            <div class="col-md-5 col-sm-1 col-xs-12 "></div>
                                        </div>
                                        <div class=" col-md-1 col-sm-1 col-xs-12">
                                            <button type="submit" class="btn btn-round btn-info "
                                                    style="width: 100px;"><span
                                                    class="glyphicon glyphicon-search" aria-hidden="true"></span>検索
                                            </button>
                                        </div>

                                        <div class="col-md-11 col-sm-9 col-xs-12">

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">日付</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group">
                                            <span class="fa fa-calendar form-control-feedback left"
                                                  aria-hidden="true"></span>
                                                <input type="text" class="form-control form_datetime has-feedback-left"
                                                       autocomplete="off" style="padding-right: 0;" id="month">
                                            </div>


                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">支払い状態</label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" name="selStatus" id="selStatus"
                                                        style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="0">全部</option>
                                                    <option value="1">未支払い</option>
                                                    <option value="2">支払完了</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left"
                                                      aria-hidden="true"></span>
                                            </div>
                                        </div>


                                    </div>

                                </form>
                                <div class="table-responsive col-md-12 col-sm-2 col-xs-12 form-group">

                                    <table id="settlementListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th class="column-title" style="width: 10%;">管理者の名前</th>
                                            <th class="column-title" style="width: 10%;">店舗名</th>
                                            <th class="column-title" style="width: 10%;">店舗のUUID</th>
                                            <th class="column-title" style="width: 10%;">日付</th>
                                            <th class="column-title" style="width: 10%;">月額の合計</th>
                                            <th class="column-title" style="width: 10%;">支払い状態</th>
                                            <th class="column-title" style="width: 3%;"></th>
                                            <th class="column-title" style="width: 3%;"></th>
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


    $('#month').datetimepicker({
        language: 'ja',
        format: 'yyyy-mm',
        minView: 3,
        autoclose: "true",
        startView: 3

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
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/settlement/list/',
            type: 'POST',
            data: function (data) {
                data.customerName=$("#selCusName").val();
                data.shopName=$("#selShopName").val();
                data.selStatus=$("#selStatus").val();
                data.mon = $("#month").val();
                data.shopUUID =$("#selUUID").val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[3, 'desc']],
        columns: [
            {data: "customerName", name: "login_name"},
            {data: "shopName", name: "sname"},
            {
                data: "shopUUID", name: "uuid"
            },
            {data: "month",  name: "yyyymm"},
            {data: "amountTotal", name: "amountTotal"},
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
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<a class="btn btn-primary" style="font-size: inherit;" href="javascript:void(0)" onclick="gotoUpdate('+"'"+full.shopUUID+"',"+"'"+full.month+"'"+')">詳細</a>';
                }
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<a class="btn btn-primary" style="font-size: inherit;" href="${pageContext.request.contextPath}/admin/quote/pdf/'+full.shopUUID+'/'+full.month+'/">ダウンロード</a>';
                }
            }
        ],
        columnDefs: [
            {
                targets: [5, 6],
                searchable: false,
                orderable: false
            }
        ],
        language: {
            "sEmptyTable":     "テーブルにデータがありません",
            "sInfo":           " _TOTAL_ 件中 _START_ から _END_ まで表示",
            "sInfoEmpty":      " 0 件中 0 から 0 まで表示",
            "sInfoFiltered":   "（全 _MAX_ 件より抽出）",
            "sInfoPostFix":    "",
            "sInfoThousands":  ",",
            "sLengthMenu":     "_MENU_ 件表示",
            "sLoadingRecords": "読み込み中...",
            "sProcessing":     "<div id='hahaha'>Processing...</div>",
            "sSearch":         "検索:",
            "sZeroRecords":    "一致するレコードがありません",
            "oPaginate": {
            "sFirst":    "先頭",
                "sLast":     "最終",
                "sNext":     "次",
                "sPrevious": "前"
        },
        "oAria": {
            "sSortAscending":  ": 列を昇順に並べ替えるにはアクティブにする",
                "sSortDescending": ": 列を降順に並べ替えるにはアクティブにする"
        }
    }
    });


    function searchData() {
        datatable.ajax.reload();

    }

    function gotoUpdate(uuid,month) {
        window.location.href = '${pageContext.request.contextPath}/admin/settlement/edit/' +uuid+ '/'+month+'/';
    }


</script>


</body>
</html>
