<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>统计</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${basePath}/js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/css/index.css">
    <%@ include file="../../common/head.jsp"%>
    <%@ include file="../../common/theme.jsp"%>
    <script src="${basePath}/js/jquery/jQuery-2.2.0.min.js"></script>
    <script src="${basePath}/js/charts/Chart.js"></script>
    <script>
        $(function () {
            Canvas3();
        });
        function Canvas3() {
            var randomScalingFactor = function () { return Math.round(Math.random() * 100) };
            $.ajax({
                type: "POST",
                url: "${basePath}/accessStatistic/selectForMonth",
                success: function(list){
                	//alert(list);
                	var myobj=(eval("(" + list + ")").list);
                	$.each(myobj,function(i,val){
                 	   
                	});
                }
            });
            var lineChartData = {
                labels: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                datasets: [
                    {
                        fillColor: "#578ebe",
                        data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
                    }
                ]
            };
            var ctx = document.getElementById("Canvas3").getContext("2d");
            window.myLine = new Chart(ctx).Bar(lineChartData, {
                bezierCurve: false,
             
            });
        }
    </script>
</head>
<body>
<div class="title">
    <p>存取记录统计</p>
</div>
    <div class="content">
       <form action="" method="post">
         <div class="list">
	         <ul>
	            <li>箱号：<input id="" name="" type="text"/></li>
	            <li>时间：<input id="" name="" type="text"/></li>
	            <li><input type="button" value="查询" style="width:88px;"/></li>
	         </ul>
         </div>
       </form>
    <div class="tab">
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
