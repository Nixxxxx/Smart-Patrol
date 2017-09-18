<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>无线巡更管理中心</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<base href="<%=basePath%>">

<!-- jQuery 3.1.1 -->
<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Font Awesome -->
<link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">
<!-- Select2 -->
<link rel="stylesheet" href="//cdn.bootcss.com/select2/4.0.3/css/select2.min.css">
<script src="//cdn.bootcss.com/select2/4.0.3/js/select2.min.js"></script>
<!-- bootstrap datepicker -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css">
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!-- Slimscroll -->
<script src="//cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>
<!-- Theme style -->
<link rel="stylesheet" href="static/dist/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css"> 
<!-- echarts -->
<script src="//cdn.bootcss.com/echarts/3.5.4/echarts.min.js"></script> 
<!-- moment.js -->
<script src="//cdn.bootcss.com/moment.js/2.18.1/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.5/locale/zh-cn.js"></script>
<!-- daterangepicker.js -->
<link href="//cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min.css" rel="stylesheet">
<script src="//cdn.bootcss.com/bootstrap-daterangepicker/2.1.25/daterangepicker.min.js"></script>

<style>
body, button, input, select, textarea, h1, h2, h3, h4, h5, h6 {
	font-family: Microsoft YaHei, "宋体", Tahoma, Helvetica, Arial,"\5b8b\4f53", sans-serif;
	background: #eee;
}

.content-header>.breadcrumb {
	position: relative;
	margin-top: 5px;
	top: 0;
	right: 0;
	float: none;
	background: #d2d6de;
	padding-left: 10px;
}

.content-header>.breadcrumb li:before {
	color: #97a0b3;
}
</style>
</head>
<body>

	<!-- Content Header (Page header) -->
	<section class="content-header">
  <ol class="breadcrumb">
    <li><a href="main/showInfo"><i class="fa fa-dashboard"></i> 首页</a></li>
    <li>巡更管理</li>
    <li class="active">巡更统计</li>
  </ol>
</section>

<!-- Main content -->
<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <!-- AREA CHART -->
      <div class="box box-info">
        <div class="box-header with-border">
          <h3 class="box-title pull-left">出勤任务统计</h3>
          <div class="input-group pull-left" style="margin: 0 10px">
            <input type="button" class="btn btn-default btn-xs" id="daterange-btn">
          </div>
          <a href="patrol/downloadExcel" target="_blank" id="download_excel">下载Excel</a>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
          </div>
        </div>
        <div class="box-body">
          <div class="chart">
            <div id="task_stat" style="height:450px;"></div>
          </div>
        </div>
        <!-- /.box-body -->
      </div>
      <!-- /.box -->

    </div>
  </div>
</section>
<!-- /.content -->

<script>
    $(function () {

        var start = moment().startOf('month');
        var end = moment();

        function cb(start, end) {
            var start_date = start.format('YYYY-MM-DD');
            var end_date = end.format('YYYY-MM-DD');
            //改变下载excel的url
            var excel_href = $("#download_excel").attr('href');
            if (excel_href.indexOf('?') !== -1) {
                excel_href = excel_href.substring(0, excel_href.indexOf('?'));
            }
            excel_href += '?start=' + start_date + '&end=' + end_date;
            $("#download_excel").attr('href', excel_href);

            // 初始化echarts实例
            var myChart = echarts.init(document.getElementById('task_stat'));

            $.ajax({
                url: "patrol/getTaskData",
                type: 'post',
                dataType: 'json',
                data: {start: start_date, end: end_date},
                beforeSend: function () {
                    myChart.showLoading();
                },
                complete: function () {
                    myChart.hideLoading();
                },
                success: function (data) {
                    if (data.success) {
                        // 指定图表的配置项和数据
                        var option = {
                            title: {
                                show: false,
                                text: '出勤任务统计'
                            },
                            tooltip: {},
                            legend: {
                                data: ['已完成任务', '未按时完成任务', '未完成任务']
                            },
                            xAxis: {
                                "axisLabel": {
                                    interval: 0, //显示全部x轴
                                    formatter: function (val) {
                                        return val.split("").join("\n"); //横轴信息文字竖直显示
                                    }
                                },
                                data: data.data.name
                            },
                            yAxis: {
                                min: 0,                        //控制数轴的最小值
                                interval: 1
                            },
                            series: [
                                {
                                    name: '未完成任务',
                                    type: 'bar',
                                    stack: '任务',
                                    label: {
                                        normal: {
                                            show: true,
                                            formatter: function (val) {
                                                //图上的label不显示0的数据
                                                if (val.data === 0) {
                                                    return '';
                                                }
                                            }
                                        }
                                    },
                                    data: data.data.notCompleted
                                },
                                {
                                    name: '未按时完成任务',
                                    type: 'bar',
                                    stack: '任务',
                                    label: {
                                        normal: {
                                            show: true,
                                            formatter: function (val) {
                                                if (val.data === 0) {
                                                    return '';
                                                }
                                            }
                                        }
                                    },
                                    data: data.data.notOnTime
                                },
                                {
                                    name: '已完成任务',
                                    type: 'bar',
                                    stack: '任务',
                                    label: {
                                        normal: {
                                            show: true,
                                            formatter: function (val) {
                                                if (val.data === 0) {
                                                    return '';
                                                }
                                            }
                                        }
                                    },
                                    data: data.data.onTime
                                }
                            ],
                            color: ['#dd4b39', '#f39c12', '#00a65a']
                        };

                        // 使用指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    } else {
                        alert('加载失败');
                    }
                },
                error: function () {
                    alert('加载失败，请检查网络');
                }
            });
        }

        $('#daterange-btn').daterangepicker(
            {
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '最近7天': [moment().subtract(6, 'days'), moment()],
                    '最近30天': [moment().subtract(29, 'days'), moment()],
                    //'本月': [moment().startOf('month'), moment().endOf('month')],
                    '本月': [moment().startOf('month'), moment()],
                    '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                startDate: start,
                endDate: end,
                locale: {
                    format: 'YYYY-MM-DD',
                    customRangeLabel: '自定义',
                    applyLabel: '提交',
                    cancelLabel: '取消',
                }
            }, cb
        );
        //执行一次
        cb(start, end);
    })
</script>
</body>
</html>