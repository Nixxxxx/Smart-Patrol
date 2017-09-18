<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<base href="<%=basePath%>">

<!-- jQuery 3.1.1 -->
<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Font Awesome -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">
<!-- Select2 -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/select2/4.0.3/css/select2.min.css">
<script src="//cdn.bootcss.com/select2/4.0.3/js/select2.min.js"></script>
<!-- bootstrap datepicker -->
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css">
<script
	src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script
	src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!-- Slimscroll -->
<script
	src="//cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>
<!-- Theme style -->
<link rel="stylesheet" href="static/dist/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css">

<style>
body, button, input, select, textarea, h1, h2, h3, h4, h5, h6 {
	font-family: Microsoft YaHei, "宋体", Tahoma, Helvetica, Arial,
		"\5b8b\4f53", sans-serif;
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
		<li><a href="main/showInfo"><i
				class="fa fa-dashboard"></i> 首页</a></li>
		<li>巡更管理</li>
		<li class="active">指派任务</li>
	</ol>
	</section>

	<!-- Main content -->
	<section class="content"> <!-- Small boxes (Stat box) -->
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<!-- Horizontal Form -->
			<div class="box box-info">
				<div class="box-header with-border">
					<h3 class="box-title">指派任务</h3>
				</div>
				<!-- /.box-header -->
				<!-- form start -->
				<form class="form-horizontal" method="post" id="patrol_add_form">
					<div class="box-body">
						<div class="form-group">
							<label for="patrol_member" class="col-sm-2 control-label">人员</label>
							<div class="col-sm-10">
								<select class="form-control select2" name="member"
									id="patrol_member" style="width: 100%">
									<c:if test="${members!=null }"></c:if>
									<option value="">请选择人员</option>
									<c:forEach var="member" items="${members }">
										<option value="${member.id }">${member.number }-${member.name }
										</option>
									</c:forEach>
									<c:if test="${members==null }">
										<option>请添加人员</option>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="patrol_device" class="col-sm-2 control-label">设备</label>
							<div class="col-sm-10">
								<select class="form-control select2" name="device"
									id="patrol_device" style="width: 100%">
									<c:if test="${devices!=null }">
										<option value="">请选择设备</option>
										<c:forEach var="device" items="${devices }">
											<option value="${device.id }">${device.number }-${device.name }
											</option>
										</c:forEach>
									</c:if>
									<c:if test="${devices==null }">
										<option>请添加设备</option>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="patrol_date" class="col-sm-2 control-label">日期</label>
							<div class="col-sm-10">
								<input type="text" class="form-control pull-right" name="date"
									id="patrol_date">
							</div>
						</div>
						<div class="form-group">
							<label for="device_extra" class="col-sm-2 control-label">备注</label>
							<div class="col-sm-10">
								<textarea class="form-control" rows="3" id="device_extra"
									name="extra" placeholder="请输入备注，100字以内，选填"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">地点与时段</label>
							<div class="col-sm-9">
								<input type="hidden" name="location_time" id="location_time">
								<div class="row" id="location_time_content" style="cursor: pointer" data-toggle="modal"
									data-target="#patrol_location_time_modal" data-backdrop="static">
									<div class="col-sm-12">
										<p class="form-control-static">点击选择地点与时段</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
						<button type="reset" class="btn btn-default">重置</button>
						<button type="submit" class="btn btn-info pull-right"
							id="patrol_add_button" data-loading-text="添加中...">添加</button>
					</div>
					<!-- /.box-footer -->
				</form>
			</div>
			<!-- /.box -->
		</div>
	</div>
	</section>
	<!-- /.content -->

	<!-- Modal -->
	<div class="modal fade" id="patrol_location_time_modal" tabindex="-1"
		role="dialog" aria-labelledby="patrol_location_time_label">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<!-- Horizontal Form -->
				<div class="box box-info">
					<div class="box-header with-border">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h3 class="box-title" id="patrol_location_time_label">选择地点与时段</h3>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<form class="form-horizontal" method="post" id="patrol_location_time_form">
						<div class="box-body">
							<div class="form-group">
								<label class="col-sm-2 control-label">地点</label>
								<div class="col-sm-10">
									<c:if test="${locations!=null }">
										<div class="row">
											<c:forEach var="location" items="${locations }">
												<div class="col-xs-6 col-sm-4">
													<label class="radio-inline"> <input type="radio"
														name="location"
														value="${location.id }|${location.number }-${location.name }">
														${location.number }-${location.name }
													</label>
												</div>
											</c:forEach>
										</div>
									</c:if>
									<c:if test="${locations==null }">
										<p class="form-control-static">请添加地点</p>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">时段</label>
								<div class="col-sm-10">
									<c:if test="${ttimes!=null }">
										<div class="row">
											<c:forEach var="ttime" items="${ttimes }">
												<div class="col-xs-6 col-sm-4">
													<label class="checkbox-inline"> 
													<input type="checkbox" name="ttime" value="${ttime.startTime }-${ttime.endTime }|${ttime.startTime}-${ttime.endTime}">
													<fmt:formatDate value="${ttime.startTime}" pattern="HH:mm" />-<fmt:formatDate value="${ttime.endTime}" pattern="HH:mm" />
													</label>
												</div>
											</c:forEach>
										</div>
									</c:if>
									<c:if test="${ttimes==null }">
										<p class="form-control-static">请添加时段</p>
									</c:if>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-info pull-right" id="patrol_location_time_button">添加</button>
						</div>
						<!-- /.box-footer -->
					</form>
				</div>
				<!-- /.box -->
			</div>
		</div>
	</div>

	<script type="text/javascript">

    $(function () {

        //Initialize Select2 Elements
        $(".select2").select2();

        var date_obj = new Date();
        var today = date_obj.toDateString();
        //Date picker
        $("#patrol_date").datepicker({
            language: "zh-CN",
            startDate: today,
            format: "yyyy-mm-dd",
            todayHighlight: true,
            todayBtn: "linked",
            autoclose: true
        }).datepicker("setDate", today);

        //添加时间与地点
        $("#patrol_location_time_form").submit(function () {
            var data = $(this).serializeArray();
            //如果时间或者地点未选择
            if (!data || !data[0] || !data[1]) {
                return false;
            }

            var str = "<div class=\"col-sm-12\"><p class=\"form-control-static\">";
            var $loc_time = $("#location_time");
            //构造如如下数据（第一个为地点ID） 104|03:00:00-04:00:00|04:00:00-06:20:00|,103|03:00:00-04:00:00|,
            $.each(data, function (index, obj) {
                var value_arr = obj.value.split("|");
                str += value_arr[1] + " ";
                //拼接单个地点时段
                $loc_time.val($loc_time.val() + value_arr[0] + "|");
            });
            str += "</p></div>";
            //拼接多个地点时段
            $loc_time.val($loc_time.val() + ",");

            $("#location_time_content").append(str);
            //关闭模态框
            $("#patrol_location_time_modal").modal("hide");

            this.reset();

            return false;
        });


        var $patrol_add_form = $("#patrol_add_form");
        $patrol_add_form.submit(function () {

            var $add_btn = $("#patrol_add_button");

            $.ajax({
                url: "patrol/insertTask",
                type: "POST",
                dataType: "json",
                data: $patrol_add_form.serialize(),
                beforeSend: function () {
                    $add_btn.button("loading");
                },
                complete: function () {
                    $add_btn.button("reset");
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                    	window.location.reload();
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    if (textStatus === "timeout") {
                        alert("添加超时！");
                    } else {
                        alert("添加失败！");
                    }
                }
            });
            return false;
        });
    })
</script>
</body>
</html>