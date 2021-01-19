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
    <%--<link href="/static/admin/vendors/tableExport.jquery.plugin-master/bootstrap-table.min.css"　rel="stylesheet"  />--%>
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
                        <h3>応募管理</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>応募
                                    <small>検索結果</small>
                                </h2>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">
                                <div class="col-md-2 col-sm-1 col-xs-12"><p>検索条件：</p></div>
                                <div class="col-md-9 col-sm-9 col-xs-12"></div>
                                <form:form  modelAttribute="form" class="form-horizontal form-label-left input_mask" id="userForm" method="post">
                                    <input type="hidden"   id = "orderStatement" value="${form.orderStatement}"/>
                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">名前</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="name"
                                                >
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">UUID</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="uuid"
                                                >
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">性別</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="gender"
                                                        >
                                                    <option value="9">ALL</option>
                                                    <option value="1">男性</option>
                                                    <option value="2">女性</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">職業</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <select class="form-control has-feedback-left" id="careerCode"
                                                        data-rule-digits="true" data-rule-maxlength="4">
                                                    <option value="0">職業を選択</option>
                                                    <option value="1">中学生</option>
                                                    <option value="2">高校生</option>
                                                    <option value="3">専門学生</option>
                                                    <option value="4">大学生</option>
                                                    <option value="5">公務員</option>
                                                    <option value="6">自営業</option>
                                                    <option value="7">会社役員</option>
                                                    <option value="8">会社員</option>
                                                    <option value="9">派遣社員</option>
                                                    <option value="10">契約社員</option>
                                                    <option value="11">専業主婦</option>
                                                    <option value="12">専業主夫</option>
                                                    <option value="13">パート・アルバイト</option>
                                                    <option value="14">その他</option>
                                                </select>
                                                <span class="fa fa-bars form-control-feedback left" aria-hidden="true"></span>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">年齢(From)</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="ageFrom"
                                                       data-rule-digits="true" data-rule-maxlength="4">
                                            </div>

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">年齢(To)</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left"  id="ageTo"
                                                       data-rule-digits="true" data-rule-maxlength="4" >
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">日付(From)</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="date" class="form-control has-feedback-left" id="dateFrom" style="padding-right: 0;"
                                                       data-rule-digits="" data-rule-maxlength="">
                                            </div>

                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">日付(To)</label>
                                            <div class="col-md-2 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="date" class="form-control has-feedback-left"  id="dateTo" style="padding-right: 0;"
                                                       data-rule-digits="" data-rule-maxlength="" >
                                            </div>
                                        </div>
                                        <div class=" col-sm-1 col-sm-1 col-xs-12" style="float: right;">
                                            <a class="btn btn-round btn-success " style=" text-align: center;line-height: 20px;"
                                               onclick="getNowFormatDate();" >
                                                <span class="" aria-hidden="true"></span>ダウンロード</a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-10">
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">メール</label>
                                            <div class="col-md-3 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="mail">
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">アドレス</label>
                                            <div class="col-md-3 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="address"
                                                >
                                            </div>
                                            <label class="control-label col-md-1 col-sm-1 col-xs-12">タイトル</label>
                                            <div class="col-md-3 col-sm-1 col-xs-12 form-group has-feedback">
                                                <span class="fa fa-fire form-control-feedback left" aria-hidden="true"></span>
                                                <input type="text" class="form-control has-feedback-left" id="title"
                                                      >
                                            </div>
                                        </div>
                                        <div class=" col-sm-1 col-sm-1 col-xs-12" style="float: right;">
                                            <a class="btn btn-round btn-info " style="line-height: 20px;width: 110px;" onclick="searchData()"><span
                                                    class="glyphicon glyphicon-search" aria-hidden="true"></span>検索</a>
                                        </div>
                                    </div>
                                </form:form>

                                <div class="table-responsive">
                                    <table id="entryListTable" data-show-export="true" class="table table-striped jambo_table bulk_action">
                                        <thead>
                                            <tr class="headings">
                                                <th class="column-title" style="width: 10%;">名前</th>
                                                <th class="column-title" style="width: 10%;">UUID</th>
                                                <th class="column-title" style="width: 10%;">日付</th>
                                                <th class="column-title" style="width: 5%;">性別</th>
                                                <th class="column-title" style="width: 5%;">年齢</th>
                                                <th class="column-title" style="width: 7%;">職業</th>
                                                <th class="column-title" style="width: 10%;">メール</th>
                                                <th class="column-title" style="width: 20%;">アドレス</th>
                                                <th class="column-title" style="width: 20%;">タイトル</th>
                                                <th class="column-title" style="width: 3%;"></th>
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
<div id="entryLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
</div>
<div class="modal fade delete-entry-modal" tabindex="-1" role="dialog" aria-hidden="true">
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
<%--<script src="/static/admin/vendors/tableExport.jquery.plugin-master/tableExport.js"></script>--%>
<%--<script src="/static/admin/vendors/tableExport.jquery.plugin-master/bootstrap-table-export.js"></script>--%>
<%--<script src="/static/admin/vendors/tableExport.jquery.plugin-master/bootstrap-table.min.js"></script>--%>

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

    var datatable = $('#entryListTable').DataTable({
        dom:'lrtip',
        aLengthMenu:[[10, 25, 50, 100, 200,500,1000,-1], ["10", "25", "50", "100", "200", "500","1000","ALL"]],
        processing: true,
        exportDataType:'all',
        serverSide: true,
        ajax: {
            url: '/admin/entry/list/',
            type: 'POST',
            data: function (data) {
                var str= $('#name').val();
                var arr=str.split(" ");
                data.firstName = arr[0];
                data.secondName = arr[1];
                data.mail = $('#mail').val();
                data.title = $('#title').val();
                data.address = $('#address').val();
                data.uuid = $('#uuid').val();
                data.age=$('#age').val();
                data.gender = $('#gender').val();
                data.dateFrom=$('#dateFrom').val();
                data.dateTo=$('#dateTo').val();
                data.ageFrom=$('#ageFrom').val();
                data.ageTo=$('#ageTo').val();
                data.career=$('#careerCode').val();
                return JSON.stringify(data);
            },
            contentType: "application/json;charset=UTF-8",
            dataType: "json"
        },
        order: [[ 2, 'desc' ]],
        // aoColumnDefs: [
        //     { "bSearchable": false, "aTargets": [ 5 ] },
        //     { "bVisible": false, "aTargets": [ 6 ] }
        // ],
        columns: [
            { data: "name", name: "name" ,orderable: false},
            { data: "uuid", name: "uuid" ,orderable: false},
            { data: "date", name: "create_datetime" },
            {
                data: "gender",
                render: function (data, type, full, meta) {
                    if(data==1){
                        return '<div>男性</div>';
                    }
                    if(data==2){
                        return '<div>女性</div>';
                    }
                    if(data==0){
                        return '<div>不明</div>';
                    }
                    return '<div></div>';
                },
                name: "gender"
            },
            { data: "age", name: "age" },
            { data: "careerStr", name: "career" },
            { data: "mail", name: "p.mail" },
            { data: "address", name: "" ,},
            { data: "title", name: "title" ,orderable: false},
            {
                data: "entryId",
                orderable: false,
                render: function (data, type, full, meta) {
                    if(full.entryType==1 || full.entryType==2){
                        return '<a class="btn btn-primary" style="font-size: inherit;" href="javascript:void(0)" onclick="gotoNews('+full.entryId+')">応募元</a>';
                    }
                    if(full.entryType==3){
                        return '<a class="btn btn-primary" style="font-size: inherit;" href="javascript:void(0)" onclick="gotoEvent('+full.entryId+')">応募元</a>';
                    }
                }
            },
            // {
            //     data: null,
            //     orderable: false,
            //     render: function (data, type, full, meta) {
            //         return '<a class="btn btn-primary" style="font-size: inherit;" href="javascript:void(0)" onclick="gotoUpdate('+full.id+')">詳細</a>';
            //     }
            // },
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
                // targets: [1,3,4],
                // searchable: false,
                // orderable: false
            }
        ],
        language: {url: "/static/admin/build/json/japanese.json"}
    });

    var currDeleteEntryId = 0;

    function searchData() {
        datatable.ajax.reload();
    }

    function gotoDelete(id) {
        currDeleteEntryId = id;
        $('.delete-entry-modal').modal("show");
    }

    function gotoUpdate(id) {
        $('#entryLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/entry/detail/' + id + '/';
    }

    function gotoRegist() {
        $('#entryLoading').show();
        window.location.href =  '${pageContext.request.contextPath}/admin/entry/regist/';
    }

    function gotoNews(entryId) {
        $('#entryLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/news/edit/' + entryId + '/';
    }

    function gotoEvent(entryId) {
        $('#entryLoading').show();
        window.location.href = '${pageContext.request.contextPath}/admin/event/edit/' + entryId + '/';
    }

    function confirmDeleteClick() {
        $('.delete-entry-modal').modal("hide");
        $('#entryLoading').show();
        var form = $("<form></form>");
        form.attr('action', '${pageContext.request.contextPath}/admin/entry/delete/');
        form.attr('method', 'post');
        var input1 = $("<input type='hidden' name='id'/>");
        input1.attr('value', currDeleteEntryId);
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

    function getNowFormatDate() {
        // var date = new Date();
        // var seperator1 = "-";
        // var year = date.getFullYear();
        // var month = date.getMonth() + 1;
        // var strDate = date.getDate();
        // if (month >= 1 && month <= 9) {
        //     month = "0" + month;
        // }
        // if (strDate >= 0 && strDate <= 9) {
        //     strDate = "0" + strDate;
        // }
        // var currentdate = year + seperator1 + month + seperator1 + strDate;
        // // return currentdate;
        //
        // $('#entryListTable').tableExport({
        //     type: 'csv',
        //     escape: 'false',
        //     exportDataType:'all',
        //     fileName: '応募結果'+currentdate
        // })

        var form = $("<form ></form>");
        var input1 = $("<input type='hidden' name='mail'/>");
        var input2 = $("<input type='hidden' name='title'/>");
        var input3 = $("<input type='hidden' name='address'/>");
        var input4 = $("<input type='hidden' name='uuid'/>");

        var input6 = $("<input type='hidden' name='gender'/>");
        var input7 = $("<input type='hidden' name='dateFrom'/>");
        var input8 = $("<input type='hidden' name='dateTo'/>");
        var input9 = $("<input type='hidden' name='ageFrom'/>");
        var input10 = $("<input type='hidden' name='ageTo'/>");
        var input11 = $("<input type='hidden' name='careerCode'/>");
        var input12 = $("<input type='hidden' name='orderStatement'/>");
        input1.attr('value', $('#mail').val());
        input2.attr('value', $('#title').val());
        input3.attr('value', $('#address').val());
        input4.attr('value', $('#uuid').val());
        input12.attr('value', $('#orderStatement').val());
        // input5.attr('value', $('#age').val());
        if( parseInt($('#gender').val()) != null){
            input6.attr('value',parseInt($('#gender').val()));
            form.append(input6);
        }
        if($('#dateFrom').val() != null && $('#dateFrom').val() != ""){
            var  d   = new   Date( ($('#dateFrom').val()).replace(/-/g,   "/"));
            input7.attr('value', d);
            form.append(input7);
        }
        if($('#dateTo').val() != null && $('#dateTo').val() != ""){
            var  t   = new   Date( ($('#dateTo').val()).replace(/-/g,   "/"));
            input8.attr('value', t);
            form.append(input8);
        }
        if($('#ageFrom').val()!= null&& $('#ageFrom').val() != ""){
            input9.attr('value', parseInt($('#ageFrom').val()) );
            form.append(input9);
        }
        if($('#ageTo').val()!= null && $('#ageTo').val() != ""){
            input10.attr('value', parseInt($('#ageTo').val()));
            form.append(input10);
        }
        if($('#careerCode').val()!= null && $('#careerCode').val() != ""){
            input11.attr('value', parseInt($('#careerCode').val()));
            form.append(input11);
        }

        form.append(input1);
        form.append(input2);
        form.append(input3);
        form.append(input4);
        form.append(input12);

        form.appendTo("body");
        form.css('display', 'none');
        form.attr('method', 'post');
        form.attr('action', '${pageContext.request.contextPath}/admin/entry/csv/downLoad/');
        form.submit();
        // form.attr('dataType ','json');enctype="multipart/form-data"
        // form.attr('enctype','multipart/form-data');
        // form.attr('contentType','application/json;charset=UTF-8');
        <%--window.location.href = '${pageContext.request.contextPath}/test/csvDownLoad/';--%>
    }

</script>

</body>
</html>
