<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <!-- Custom Theme Style -->
    <link href="/static/admin/build/css/custom.min.css" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">

        <!-- sidebar menu -->
        <jsp:include page="sidebar.jsp"/>
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
            <!-- top tiles -->
            <div class="row tile_count">
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> 登録</span>
                    <div class="count">${dashboardForm.mau}</div>
                </div>
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> 店舗</span>
                    <div class="count">${dashboardForm.shopAmount}</div>
                </div>
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> NEWS</span>
                    <div class="count">${dashboardForm.news}</div>
                </div>
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> GOODS</span>
                    <div class="count">${dashboardForm.goodsAmount}</div>
                </div>
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> EVENT</span>
                    <div class="count">${dashboardForm.event}</div>
                </div>
                <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
                    <span class="count_top"><i class="fa fa-user"></i> FREEPAPER</span>
                    <div class="count">${dashboardForm.freepaper}</div>
                </div>
            </div>
            <!-- /top tiles -->


            <br/>

            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="x_panel tile fixed_height_320">
                        <div class="x_title">
                            <h2>職業</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <h4>職業比率</h4>
                            <div class="widget_summary">
                                <div class="w_left w_25">
                                    <span>会社員</span>
                                </div>
                                <div class="w_center w_55">
                                    <div class="progress">
                                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60"
                                             aria-valuemin="0" aria-valuemax="100" style="width: ${dashboardForm.careerRatio.employee}%;">
                                            <span class="sr-only">60% Complete</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="w_right w_20">
                                    <span>${dashboardForm.careerRatio.employee}%</span>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                            <div class="widget_summary">
                                <div class="w_left w_25">
                                    <span>学生</span>
                                </div>
                                <div class="w_center w_55">
                                    <div class="progress">
                                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60"
                                             aria-valuemin="0" aria-valuemax="100" style="width: ${dashboardForm.careerRatio.student}%;">
                                            <span class="sr-only">60% Complete</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="w_right w_20">
                                    <span>${dashboardForm.careerRatio.student}%</span>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="widget_summary">
                                <div class="w_left w_25">
                                    <span>専門主婦</span>
                                </div>
                                <div class="w_center w_55">
                                    <div class="progress">
                                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60"
                                             aria-valuemin="0" aria-valuemax="100" style="width: ${dashboardForm.careerRatio.housewife}%;">
                                            <span class="sr-only">60% Complete</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="w_right w_20">
                                    <span>${dashboardForm.careerRatio.housewife}%</span>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="widget_summary">
                                <div class="w_left w_25">
                                    <span>アルバイト</span>
                                </div>
                                <div class="w_center w_55">
                                    <div class="progress">
                                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60"
                                             aria-valuemin="0" aria-valuemax="100" style="width: ${dashboardForm.careerRatio.job}%;">
                                            <span class="sr-only">60% Complete</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="w_right w_20">
                                    <span>${dashboardForm.careerRatio.job}%</span>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="widget_summary">
                                <div class="w_left w_25">
                                    <span>その他</span>
                                </div>
                                <div class="w_center w_55">
                                    <div class="progress">
                                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60"
                                             aria-valuemin="0" aria-valuemax="100" style="width: ${dashboardForm.careerRatio.other}%;">
                                            <span class="sr-only">60% Complete</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="w_right w_20">
                                    <span>${dashboardForm.careerRatio.other}%</span>
                                </div>
                                <div class="clearfix"></div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="x_panel tile fixed_height_320 overflow_hidden">
                        <div class="x_title">
                            <h2>年齢</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <table class="" style="width:100%">
                                <tr>
                                    <th style="width:37%;">
                                    </th>
                                    <th>
                                        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                                            <p class="">年齢</p>
                                        </div>
                                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                                            <p class="">比率</p>
                                        </div>
                                    </th>
                                </tr>
                                <tr>
                                    <td>
                                        <canvas class="canvasDoughnut" height="140" width="140"
                                                style="margin: 15px 10px 10px 0"></canvas>
                                    </td>
                                    <td>
                                        <table class="tile_info">
                                            <tr>
                                                <td>
                                                    <p><i class="fa fa-square blue"></i>10代 </p>
                                                </td>
                                                <td>${dashboardForm.age.age10}%</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <p><i class="fa fa-square green"></i>20代 </p>
                                                </td>
                                                <td>${dashboardForm.age.age20}%</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <p><i class="fa fa-square purple"></i>30代 </p>
                                                </td>
                                                <td>${dashboardForm.age.age30}%</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <p><i class="fa fa-square  " style="color: #26B99A"></i>40代 </p>
                                                </td>
                                                <td>${dashboardForm.age.age40}%</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <p><i class="fa fa-square red"></i>不明 </p>
                                                </td>
                                                <td>${dashboardForm.age.ageOther}%</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>


                <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="x_panel tile fixed_height_320">
                        <div class="x_title">
                            <h2>性別</h2>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <div class="dashboard-widget-content">
                                <ul class="quick-list">
                                    <li><i class="fa fa-calendar-o"></i><a href="#">男性</a>
                                    </li>
                                    <li><i class="fa fa-bars"></i><a href="#">女性</a>
                                    </li>
                                </ul>

                                <div class="sidebar-widget">
                                    <h4>登録ユーザー</h4>
                                    <canvas width="150" height="80" id="chart_gauge_01" class=""
                                            style="width: 160px; height: 100px;"></canvas>
                                    <div class="goal-wrapper">
                                        <span id="gauge-text" class="gauge-value pull-left">${dashboardForm.gender.malePercent}%男性</span>
                                        <span id="goal-text" class="goal-value pull-right">${dashboardForm.gender.femalePercent}%女性</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <div class="row">
                <div class="col-md-4 col-sm-4 col-xs-12">
                    <div class="x_panel">
                        <div class="x_title">
                            <h3>お知らせ
                                <small></small>
                            </h3>

                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <div class="dashboard-widget-content">

                                <ul class="list-unstyled timeline widget">
                                    <li>
                                        <div class="block">
                                            <div class="block_content">
                                                <h2 class="title">
                                                    <a>店舗申請があります！</a>
                                                </h2>
                                                <div class="byline">
                                                    <span>1 時間前</span> by <a>Admin</a>
                                                </div>
                                                <p class="excerpt">店舗申請あります、早めに対応してください。
                                                </p>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="block">
                                            <div class="block_content">
                                                <h2 class="title">
                                                    <a>店舗申請があります！</a>
                                                </h2>
                                                <div class="byline">
                                                    <span>1 時間前</span> by <a>Admin</a>
                                                </div>
                                                <p class="excerpt">店舗申請あります、早めに対応してください。
                                                </p>
                                            </div>
                                        </div>
                                    </li>

                                </ul>
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

<!-- bootstrap-datetimepicker -->
<script src="/static/admin/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<!-- bootstrap-fileinput -->
<script src="/static/admin/vendors/bootstrap-fileinput/js/fileinput.min.js"></script>

<!-- Custom Theme Scripts -->
<script src="/static/admin/build/js/custom.min.js"></script>

</body>
<script type="text/javascript">
    $(document).ready(function() {
        init_chart_doughnut();
        init_gauge();
    });

    function init_chart_doughnut(){

        if( typeof (Chart) === 'undefined'){ return; }

        console.log('init_chart_doughnut');

        if ($('.canvasDoughnut').length){

            var chart_doughnut_settings = {
                type: 'doughnut',
                tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                data: {
                    labels: [
                        "10代",
                        "20代",
                        "30代",
                        "40代",
                        "不明"
                    ],
                    datasets: [{
                        data: [${dashboardForm.age.age10}, ${dashboardForm.age.age20}, ${dashboardForm.age.age30}, ${dashboardForm.age.age40}, ${dashboardForm.age.ageOther}],
                        backgroundColor: [
                            "#3498DB",
                            "#BDC3C7",
                            "#9B59B6",
                            "#26B99A",
                            "#E74C3C"

                        ],
                        hoverBackgroundColor: [
                            "#49A9EA",
                            "#CFD4D8",
                            "#B370CF",
                            "#36CAAB",
                            "#E95E4F"

                        ]
                    }]
                },
                options: {
                    legend: false,
                    responsive: false
                }
            }

            $('.canvasDoughnut').each(function(){

                var chart_element = $(this);
                var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);

            });

        }

    }

    function init_gauge() {

        if( typeof (Gauge) === 'undefined'){ return; }

        console.log('init_gauge [' + $('.gauge-chart').length + ']');

        console.log('init_gauge');


        var chart_gauge_settings = {
            lines: 12,
            angle: 0,
            lineWidth: 0.4,
            pointer: {
                length: 0.75,
                strokeWidth: 0.042,
                color: '#1D212A'
            },
            limitMax: 'false',
            colorStart: '#1ABC9C',
            colorStop: '#1ABC9C',
            strokeColor: '#F0F3F3',
            generateGradient: true
        };


        if ($('#chart_gauge_01').length){

            var chart_gauge_01_elem = document.getElementById('chart_gauge_01');
            var chart_gauge_01 = new Gauge(chart_gauge_01_elem).setOptions(chart_gauge_settings);

        }


        if ($('#gauge-text').length){

            chart_gauge_01.maxValue = ${dashboardForm.gender.personQuantity};
            chart_gauge_01.animationSpeed = 32;
            chart_gauge_01.set(${dashboardForm.gender.male});
            // chart_gauge_01.setTextField(document.getElementById("gauge-text"));

        }

        if ($('#chart_gauge_02').length){

            var chart_gauge_02_elem = document.getElementById('chart_gauge_02');
            var chart_gauge_02 = new Gauge(chart_gauge_02_elem).setOptions(chart_gauge_settings);

        }


        if ($('#gauge-text2').length){

            chart_gauge_02.maxValue = 9000;
            chart_gauge_02.animationSpeed = 32;
            chart_gauge_02.set(2400);
            chart_gauge_02.setTextField(document.getElementById("gauge-text2"));

        }


    }
</script>
</html>
