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
    <link href="/static/admin/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="/static/admin/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">

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
                        <h3>通知管理</h3>
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
                                <h2>通知管理
                                    <small>通知検索結果</small>
                                </h2>

                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <div class="col-md-12 col-sm-12 col-xs-12"><p>検索条件：</p></div>
                                <form id="userForm" class="form-horizontal form-label-left input_mask" style="margin-bottom: 20px" method="post">
                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="notifyType">通知種類</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="notifyType" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="9">全部</option>
                                                    <option value="1">お知らせ</option>
                                                    <option value="2">プッシュ通知</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="notifiedDevice">通知デバイス</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="notifiedDevice" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="9">全部</option>
                                                    <option value="1">iOS</option>
                                                    <option value="2">Android</option>
                                                    <option value="3">ALL</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="pushStatus">送信ステータス</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="pushStatus" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="9">全部</option>
                                                    <option value="1">プッシュ未送信</option>
                                                    <option value="2">プッシュ送信ずみ</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>

                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="userType">ユーザータイプ</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="userType" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="9">全部</option>
                                                    <option value="1">管理者</option>
                                                    <option value="2">ユーザー</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="uuid">UUID</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <input type="text" id="uuid" class="form-control">
                                            </div>
                                            <label class="control-label col-md-2 col-sm-2 col-xs-12" for="notifyStatus">開封ステータス</label>
                                            <div class="col-md-2 col-sm-2 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="notifyStatus" style="-webkit-appearance: none; padding-left: 45px;">
                                                    <option value="9">全部</option>
                                                    <option value="1">未開封</option>
                                                    <option value="2">開封ずみ</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>

                                            <label class="control-label col-md-2 col-sm-3 col-xs-12" for="createDatetime">作成時間</label>
                                            <div class="col-md-2 col-sm-3 col-xs-12 form-group has-feedback">
                                                <input type="text" class="form-control has-feedback-left" style="padding-right: 0;" id="createDatetime">
                                                <span class="fa fa-calendar form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>
                                        <div class="col-md-2 text-right" style="padding-right: 15px">
                                            <div style="width: 100px;display: inline-block;">
                                                <a class="btn btn-round btn-success" style="width: 100px;" onclick="gotoRegist()">
                                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新規</a>
                                                <button class="btn btn-round btn-info " style="width: 100px;margin-top: 5px">
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
                                    <table id="notifyListTable" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                        <tr class="headings">
                                            <%--<th class="column-title" style="width: 20px;">通知種類</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">通知デバイス</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">送信ステータス</th>--%>
                                            <%--<th class="column-title" style="width: 20px; ">標題</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">内容</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">作成時間</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">ユーザ種類</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">UUID</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">ユーザ名</th>--%>
                                            <%--<th class="column-title" style="width: 20px;">開封ステータス</th>--%>
                                            <%--<th class="column-title" style="width: 20px;"></th>--%>

                                            <th class="column-title" style="width: 7%;">通知種類</th>
                                            <th class="column-title" style="width: 8%;">通知デバイス</th>
                                            <th class="column-title" style="width: 10%;">送信ステータス</th>
                                            <th class="column-title" style="width: 10%; ">標題</th>
                                            <th class="column-title" style="width: 20%;">内容</th>
                                            <th class="column-title" style="width: 10%;">作成時間</th>
                                            <th class="column-title" style="width: 8%;">ユーザ種類</th>
                                            <th class="column-title" style="width: 5%;">UUID</th>
                                            <th class="column-title" style="width: 7%;">ユーザ名</th>
                                            <th class="column-title" style="width: 12%;">開封ステータス</th>
                                            <th class="column-title" style="width: 3%;"></th>
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
<div id="notifyLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>
<div class="modal fade delete-notify-modal" tabindex="-1" role="dialog" aria-hidden="true">
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
<!-- bootstrap-datetimepicker -->
<script src="/static/admin/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<!-- PNotify -->
<script src="/static/admin/vendors/pnotify/dist/pnotify.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="/static/admin/vendors/pnotify/dist/pnotify.nonblock.js"></script>

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

    $('#createDatetime').datetimepicker({
        format: 'YYYY-MM-DD'
    });

    var datatable = $('#notifyListTable').DataTable({
        dom:'lrtip',
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/notify/list/',
            type: 'POST',
            data: function (data) {
                data.notifyType = $('#notifyType').val();
                data.notifiedDevice = $('#notifiedDevice').val();
                data.createDatetime = $('#createDatetime').val();
                data.pushStatus = $('#pushStatus').val();
                data.userType = $('#userType').val();
                data.uuid = $('#uuid').val();
                data.notifyStatus = $('#notifyStatus').val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[ 5, 'desc' ]],
        columns: [
            { data: "strNotifyType", name: "n.notify_type"},
            { data: "strNotifiedDevice", name: "n.notified_device"},
            { data: "strPushStatus", name: "n.push_status"},
            { data: "title",
                render:function(data,type,full,meta){
                    return strChange(data,26);
                },
                name: "title", orderable: false},
            // { data: "content", name: "content", orderable: false,width:"20px",},
            {
                data: null,
                    render: function (data, type, full, meta) {
                    return '<div style="width:300px;text-overflow:ellipsis;"><xmp style="font-family: Helvetica Neue,Roboto,Arial,Droid Sans,sans-serif;font-size: 13px;font-weight: 400;margin:0;">'+strChange(full.content,40) + '</xmp></div>';
                },
                name: "content"
            },
            {
                data: null,
                render: function (data, type, full, meta) {
                    return '<div>' + formatDate(full.createDatetime) + '</div>';
                },
                name: "n.create_datetime"
            },
            { data: "strUserType", name: "n.user_type"},
            { data: "uuid", name: "uuid", orderable: false },
            { data: "strName",
                render:function(data,type,full,meta){
                    return strChange(data,6);
                },
                name: null, orderable: false },
            { data: "strNotifyStatus", name: "n.notify_status"},
            {
                data: null,
                orderable: false,
                render: function (data, type, full, meta) {
                    return '<button class="btn btn-danger" style="font-size: inherit;" onclick="gotoDelete('+full.id+')">削除</button>';
                }
            }
        ],
        columnDefs: [
            {
                targets: [10],
                searchable: false,
                orderable: false
            }
        ],
        language: {url: "/static/admin/build/json/japanese.json"}
    });

    var currDeleteNotifyId = 0;

    function searchData() {
        datatable.ajax.reload();
    }

    function gotoDelete(id) {
        currDeleteNotifyId = id;
        $('.delete-notify-modal').modal("show");
    }

    function gotoUpdate(id) {
        $('#notifyLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/notify/update/' + id + '/';
    }

    function gotoRegist() {
        $('#recruitLoading').show();
        window.location.href =  '${pageContext.request.contextPath}/admin/notify/regist/';
    }

    function confirmDeleteClick() {
        $('.delete-notify-modal').modal("hide");
        $('#notifyLoading').show();
        var form = $("<form></form>");
        form.attr('action', '${pageContext.request.contextPath}/admin/notify/delete/');
        form.attr('method', 'post');
        var input1 = $("<input type='hidden' name='id'/>");
        input1.attr('value', currDeleteNotifyId);
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
            var hour = date.getHours();
            var day = "0" + date.getDate();
            var minute = "0" + date.getMinutes();
            var second = "0" + date.getSeconds();
            if(year == "1970" && month== "01" && day == "00"){
            }else{
                rtn = year + "-" + month.substring(month.length - 2,month.length) + "-" + day.substring(day.length - 2,day.length)
                    + " " +hour+":"+ minute.substring(minute.length - 2,minute.length) + ":"  + second.substring(second.length - 2,second.length);
            }
            return rtn;
        }
    }

    function strChange(str,strSize){
        if(str == null){
            return "";
        }

        if(str.length>strSize){
            return str.substring(0,strSize)+"...";
        }else{
            return str;
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
