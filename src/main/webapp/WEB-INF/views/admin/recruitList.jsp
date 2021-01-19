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
                        <h3>求人管理</h3>
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
                                <h2>求人
                                    <small>検索結果</small>
                                </h2>

                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-12 col-sm-12 col-xs-12"><p>検索条件：</p></div>
                                <form id="userForm" class="form-horizontal form-label-left input_mask" style="margin-bottom: 20px" method="post">
                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="shopName">企業名</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="shopName" class="form-control">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="title">タイトル</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="title" class="form-control" data-rule-maxlength="30">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="recruitName">職業</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="recruitName" class="form-control" data-rule-maxlength="30">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="salary">給与</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="salary" class="form-control">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="address">勤務地</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="address" class="form-control">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12" for="station">駅</label>
                                            <div class="col-md-3 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="station" class="form-control">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">掲載開始&gt=</label>
                                            <div class="col-md-3 col-sm-3 col-xs-12 form-group">
                                                <span class="fa fa-calendar form-control-feedback left" aria-hidden="true"></span>
                                                <input type="date" class="form-control has-feedback-left" style="padding-right: 0;" id="publishStart">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">掲載終了&lt=</label>
                                            <div class="col-md-3 col-sm-3 col-xs-12 form-group">
                                                <span class="fa fa-calendar form-control-feedback left" aria-hidden="true"></span>
                                                <input type="date" class="form-control has-feedback-left" style="padding-right: 0;" id="publishEnd">
                                            </div>
                                        </div>
                                        <div class="col-md-2 text-right" style="padding-right: 15px">
                                            <div style="width: 100px;display: inline-block;">
                                            <a class="btn btn-round btn-success" style="width: 100px;" onclick="gotoRegist()">
                                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新規</a>
                                            <button type="submit" class="btn btn-round btn-info " style="width: 100px;margin-top: 5px">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>検索</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class=" col-sm-6 col-sm-6 col-xs-12 text-right">

                                        </div>
                                    </div>
                                </form>
                                <div class="table-responsive">
                                    <table id="recruitListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <th class="column-title" style="width: 10%;">企業名</th>
                                            <th class="column-title" style="width: 15%;">タイトル</th>
                                            <th class="column-title" style="width: 12%;">職業</th>
                                            <th class="column-title" style="width: 12%;">給与</th>
                                            <th class="column-title" style="width: 15%;">勤務地</th>
                                            <th class="column-title" style="width: 15%;">駅</th>
                                            <th class="column-title" style="width: 8%;">掲載開始</th>
                                            <th class="column-title" style="width: 8%;">掲載終了</th>
                                            <th class="column-title"></th>
                                            <th class="column-title"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
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
<div id="recruitLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>
<div class="modal fade delete-recruit-modal" tabindex="-1" role="dialog" aria-hidden="true">
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

    var datatable = $('#recruitListTable').DataTable({
        dom:'lrtip',
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/recruit/list/',
            type: 'POST',
            data: function (data) {
                data.companyName = $('#companyName').val();
                data.title = $('#title').val();
                data.recruitName = $('#recruitName').val();
                data.salary = $('#salary').val();
                data.address = $('#address').val();
                data.station = $('#station').val();
                data.publishStart = $('#publishStart').val();
                data.publishEnd = $('#publishEnd').val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[ 1, 'asc' ]],
        columns: [
            { data: "companyName", name: "rd.company_name"},
            { data: "title1", name: "rd.title1" },
            { data: "recruitName", name: "rd.recruit_name" },
            { data: "salary", name: "rd.salary" },
            { data: "address", name: "rd.address" },
            { data: "station", name: "rd.station" },
            {
                data: null,
                render: function (data, type, full, meta) {
                    return '<div>' + formatDate(full.publishStart) + '</div>';
                },
                name: "r.publish_start"
            },
            {
                data: null,
                render: function (data, type, full, meta) {
                    return '<div>' + formatDate(full.publishEnd) + '</div>';
                },
                name: "r.publish_end"
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<a class="btn btn-primary" style="font-size: inherit;" href="javascript:void(0)" onclick="gotoUpdate('+full.recruitId+')">詳細</a>';
                }
            },
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<button class="btn btn-danger" style="font-size: inherit;" onclick="gotoDelete('+full.recruitId+')">削除</button>';
                }
            }
        ],
        columnDefs: [
            {
                targets: [8,9],
                searchable: false,
                orderable: false
            }
        ],
        language: {url: "/static/admin/build/json/japanese.json"}
    });

    var currDeleteRecruitId = 0;

    function searchData() {
        datatable.ajax.reload();
    }

    function gotoDelete(id) {
        currDeleteRecruitId = id;
        $('.delete-recruit-modal').modal("show");
    }

    function gotoUpdate(id) {
        $('#recruitLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/recruit/update/' + id + '/';
    }

    function gotoRegist() {
        $('#recruitLoading').show();
        window.location.href =  '${pageContext.request.contextPath}/admin/recruit/regist/';
    }

    function confirmDeleteClick() {
        $('.delete-recruit-modal').modal("hide");
        $('#recruitLoading').show();
        var form = $("<form></form>");
        form.attr('action', '${pageContext.request.contextPath}/admin/recruit/delete/');
        form.attr('method', 'post');
        var input1 = $("<input type='hidden' name='id'/>");
        input1.attr('value', currDeleteRecruitId);
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
