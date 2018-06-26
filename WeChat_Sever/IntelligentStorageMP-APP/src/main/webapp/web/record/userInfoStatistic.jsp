<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>统计</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="../../js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/font-awesome.min.css">
    <link rel="stylesheet" href="../../css/index.css">
    <script src="../../js/jquery/jQuery-2.2.0.min.js"></script>
    <script src="../../js/charts/Chart.js"></script>
    <script>
        $(function () {
            Canvas3();
        });
        function Canvas3() {
            var randomScalingFactor = function () { return Math.round(Math.random() * 100) };
            var lineChartData = {
                labels: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                datasets: [
                    {
                        fillColor: "#578ebe",
                        data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
                    }
                ]
            }
            var ctx = document.getElementById("Canvas3").getContext("2d");
            window.myLine = new Chart(ctx).Bar(lineChartData, {
                bezierCurve: false,
             
            });
        }
    </script>
</head>
<body>
    <div id="areascontent">
        <div class="rows" style="margin-bottom: 0.8%; overflow: hidden;">
            <div style="float: left; width: 69.2%;">
                <div style="height: 290px; border: 1px solid #e6e6e6; background-color: #fff;">
                    <div class="panel panel-default">
                        <div class="panel-heading"><i class="fa fa-bar-chart fa-lg" style="padding-right: 5px;"></i>柱状图</div>
                        <div class="panel-body">
                            <canvas id="Canvas3" style="height: 230px; width: 100%;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
    <style>
        #copyrightcontent {
            height: 30px;
            line-height: 29px;
            overflow: hidden;
            position: absolute;
            top: 100%;
            margin-top: -30px;
            width: 100%;
            background-color: #fff;
            border: 1px solid #e6e6e6;
            padding-left: 10px;
            padding-right: 10px;
        }

        .dashboard-stats {
            float: left;
            width: 25%;
        }

        .dashboard-stats-item {
            position: relative;
            overflow: hidden;
            color: #fff;
            cursor: pointer;
            height: 105px;
            margin-right: 10px;
            margin-bottom: 10px;
            padding-left: 15px;
            padding-top: 20px;
        }

            .dashboard-stats-item .m-top-none {
                margin-top: 5px;
            }

            .dashboard-stats-item h2 {
                font-size: 28px;
                font-family: inherit;
                line-height: 1.1;
                font-weight: 500;
                padding-left: 70px;
            }

                .dashboard-stats-item h2 span {
                    font-size: 12px;
                    padding-left: 5px;
                }

            .dashboard-stats-item h5 {
                font-size: 12px;
                font-family: inherit;
                margin-top: 1px;
                line-height: 1.1;
                padding-left: 70px;
            }


            .dashboard-stats-item .stat-icon {
                position: absolute;
                top: 18px;
                font-size: 50px;
                opacity: .3;
            }

        .dashboard-stats i.fa.stats-icon {
            width: 50px;
            padding: 20px;
            font-size: 50px;
            text-align: center;
            color: #fff;
            height: 50px;
            border-radius: 10px;
        }

        .panel-default {
            border: none;
            border-radius: 0px;
            margin-bottom: 0px;
            box-shadow: none;
            -webkit-box-shadow: none;
        }

            .panel-default > .panel-heading {
                color: #777;
                background-color: #fff;
                border-color: #e6e6e6;
                padding: 10px 10px;
            }

            .panel-default > .panel-body {
                padding: 10px;
                padding-bottom: 0px;
            }

                .panel-default > .panel-body ul {
                    overflow: hidden;
                    padding: 0;
                    margin: 0px;
                    margin-top: -5px;
                }

                    .panel-default > .panel-body ul li {
                        line-height: 27px;
                        list-style-type: none;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                    }

                        .panel-default > .panel-body ul li .time {
                            color: #a1a1a1;
                            float: right;
                            padding-right: 5px;
                        }
    </style>
</body>
</html>
