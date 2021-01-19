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

    <!-- bootstrap-fileinput -->
    <link href="/static/admin/vendors/bootstrap-fileinput/css/fileinput.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">

    <style>
        .krajee-default.file-preview-frame .kv-file-content {
            width: 135px;
            height: 125px;
        }

        .krajee-default.file-preview-frame .file-footer-caption {
            width: 135px;
        }

        .krajee-default.file-preview-frame .file-caption-info {
            width: 135px;
        }

        .krajee-default.file-preview-frame .file-size-info {
            width: 135px;
        }
    </style>

</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <!-- sidebar menu -->
        <jsp:include page="./sidebar.jsp" flush="true"/>
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
                        <h3>ポイント</h3>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="col-md-12 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>ポイント詳細</h2>
                            <div class="clearfix"></div>
                        </div>

                        <div class="x_content">
                            <br/>

                            <form:form modelAttribute="coin_detail" enctype="multipart/form-data" method="post"
                                       action="" id="demo-form2"
                                       class="form-horizontal form-label-left" onsubmit="return onUpdate()">
                                <div class="form-group">
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">番号 :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.serialNumber}"/></div>
                                    </div>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">生成日 :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.createDatetime}"/></div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <hr style="height:1px;border:none;border-top:1px dashed #ccc5bd; width: 85%"/>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">リアルタイムcoin :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.amount}"/></div>
                                    </div>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">ユーザーID :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.userId}"/></div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <hr style="height:1px;border:none;border-top:1px dashed #ccc5bd; width: 85%"/>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">タイプ :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left">
                                            <c:if test="${coin_detail.coinType==1}">付与</c:if>
                                            <c:if test="${coin_detail.coinType==2}">消費</c:if>
                                        </div>
                                    </div>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">コイン変化数 :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.coinChanged}"/></div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <hr style="height:1px;border:none;border-top:1px dashed #ccc5bd; width: 85%"/>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">コインID :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left">
                                            <c:if test="${coin_detail.coinId==1}">SHOP</c:if>
                                            <c:if test="${coin_detail.coinId==2}">GOODS</c:if>
                                            <c:if test="${coin_detail.coinId==3}">INVITE</c:if>
                                            <c:if test="${coin_detail.coinId==4}">SNS</c:if>
                                            <c:if test="${coin_detail.coinId==99}">TEST</c:if>
                                        </div>
                                    </div>
                                    <label class="control-label col-md-2 col-sm-2 col-xs-12">内容 :</label>
                                    <div class="col-md-4 col-sm-4 col-xs-12 form-group has-feedback">
                                        <div class="control-label " style="text-align: left"><c:out value="${coin_detail.desc}"/></div>
                                    </div>
                                </div>

                                <hr/>

                                <div class="form-group">
                                    <label class="col-md-9 col-sm-8 col-xs-12"></label>
                                    <div class="col-md-3 col-sm-4 col-xs-12">
                                        <a class="btn btn-primary" href="/admin/coin/list/" style="width: 100px;">戻る</a>
                                    </div>
                                </div>
                            </form:form>
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
<div id="customerLoading" style="display: none" class="loading">
    <div class="loading-text">Processing...</div>
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

<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min.js"></script>

<script>


    $('#picFile1').fileinput({
        'showUpload': false, 'previewFileType': 'any', 'autoReplace': true
        <c:if test="${eventRegistForm.picUrl1 != null && eventRegistForm.picUrl1 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${eventRegistForm.picUrl1}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile2').fileinput({
        'showUpload': false, 'previewFileType': 'any', 'autoReplace': true
        <c:if test="${eventRegistForm.picUrl2 != null && eventRegistForm.picUrl2 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${eventRegistForm.picUrl2}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile3').fileinput({
        'showUpload': false, 'previewFileType': 'any', 'autoReplace': true
        <c:if test="${eventRegistForm.picUrl3 != null && eventRegistForm.picUrl3 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${eventRegistForm.picUrl3}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile4').fileinput({
        'showUpload': false, 'previewFileType': 'any', 'autoReplace': true
        <c:if test="${eventRegistForm.picUrl4 != null && eventRegistForm.picUrl4 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${eventRegistForm.picUrl4}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });
    $('#picFile5').fileinput({
        'showUpload': false, 'previewFileType': 'any', 'autoReplace': true
        <c:if test="${eventRegistForm.picUrl5 != null && eventRegistForm.picUrl5 != ''}">
        , 'uploadUrl': null, 'initialPreview': [
            "<img src='${eventRegistForm.picUrl5}' class='file-preview-image kv-preview-data' stype='width:auto;height:auto;max-width:100%;max-height:100%;' alt='Desert' title='Desert'>"
        ]
        </c:if>
    });

    function onUpdate() {
        $('#customerLoading').show();
        return true;
    }
</script>

</body>
</html>

