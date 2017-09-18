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
		<li>时段管理</li>
		<li class="active">时段列表</li>
	</ol>
	</section>

	<!-- Main content -->
	<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3 class="box-title">时段列表</h3>

					<div class="box-tools">
						<div class="input-group input-group-sm" style="width: 150px;">
							<input type="text" name="table_search"
								class="form-control pull-right" placeholder="Search">

							<div class="input-group-btn">
								<button type="submit" class="btn btn-default">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body table-responsive no-padding">
					<table class="table table-hover table-bordered text-center">
						<thead>
							<tr>
								<th>序号</th>
								<th>时段</th>
								<th>备注</th>
								<th>更新时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<c:if test="${ttimeList!=null }">
							<c:forEach var="ttime" items="${ttimeList }" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td class="time_range"><fmt:formatDate
											value="${ttime.startTime}" pattern="HH:mm" />-<fmt:formatDate
											value="${ttime.endTime}" pattern="HH:mm" /></td>
									<td class="time_extra">${ttime.extra }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${ttime.addTime }"/></td>
									<td><a data-id="${ttime.id }" class="update"
										href="javascript:void(0)" data-toggle="modal"
										data-remote="false" data-target="#time_update_modal"
										data-backdrop="static"> <i class="fa fa-edit"></i>编辑
									</a> <a data-id="${ttime.id }" class="del"
										href="javascript:void(0)"> <i class="fa fa-trash"></i> 删除
									</a></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${ttimeList==null }">
							<tr>
								<td colspan="5">无记录！</td>
							</tr>
						</c:if>
					</table>
				</div>
				<!-- /.box-body -->
				<div class="box-footer clearfix">
					<div class="pull-right">
						<nav aria-label="Page navigation">
						<ul class="pagination">${pageCode }
						</ul>
						</nav>
					</div>
				</div>
			</div>
			<!-- /.box -->
		</div>
	</div>
	</section>
	<!-- /.content -->

	<!-- Modal -->
	<div class="modal fade" id="time_update_modal" tabindex="-1"
		role="dialog" aria-labelledby="time_update_label">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<!-- Horizontal Form -->
				<div class="box box-info">
					<div class="box-header with-border">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h3 class="box-title" id="time_update_label">编辑时段</h3>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<form class="form-horizontal" method="post" id="time_update_form">
						<div class="box-body">
							<input type="hidden" id="time_id" name="id">
							<div class="form-group">
								<label class="col-sm-3 control-label">开始时间</label>
								<div class="col-sm-5">
									<div class="row">
										<div class="col-sm-6">
											<select class="form-control hour" id="start_hour" title="时"></select>
										</div>
										<div class="col-sm-6">
											<select class="form-control minute" id="start_minute"
												title="分"></select>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">结束时间</label>
								<div class="col-sm-5">
									<div class="row">
										<div class="col-sm-6">
											<select class="form-control hour" id="end_hour" title="时"></select>
										</div>
										<div class="col-sm-6">
											<select class="form-control minute" id="end_minute" title="分"></select>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="time_extra" class="col-sm-3 control-label">备注</label>
								<div class="col-sm-8">
									<textarea class="form-control" rows="3" id="time_extra"
										name="extra" placeholder="请输入备注，100字以内，选填"></textarea>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-info pull-right"
								id="time_update_button" data-loading-text="更新中...">更新</button>
						</div>
						<!-- /.box-footer -->
					</form>
				</div>
				<!-- /.box -->
			</div>
		</div>
	</div>

	<script>
    $(function(){

        //设置小时的option
        $(".hour").html(function () {
            var str = "";
            for (var i = 0; i < 24; i++) {
                str += "<option value=\"" + i + "\">" + i + "</option>";
            }
            return str;
        });

        //设置分钟的option
        $(".minute").html(function () {
            var str = "";
            for (var i = 0; i < 60; i += 10) {
                str += "<option value=\"" + i + "\">" + i + "</option>";
            }
            return str;
        });

        //删除
        $(".del").click(function () {

            if (confirm("确认要删除吗？")) {
                var id = $(this).data("id");
                $.ajax({
                    url: "ttime/del",
                    type: "POST",
                    data: {id: id},
                    dataType: "json",
                    success: function (data) {
                        alert(data.msg);
                        if (data.success) {
                            //菜单栏当前选中
                            window.location.href="ttime/showList";
                            $now_selected = $("ul.treeview-menu>li.active>a");
                            $now_selected.trigger("click");
                        }
                    },
                    error: function (XMLHttpRequest, textStatus) {
                        if (textStatus === "timeout") {
                            alert("删除超时！");
                        } else {
                            alert("删除失败！");
                        }
                    }
                })
            }
        });

        //更新modal
        $(".update").click(function () {
            $("#time_id").val($(this).data("id"));

            //解析时间范围
            var time_range = $(this).parent().prevAll(".time_range").text();
            var time_arr = time_range.split("-");
            //去除 如01前边的0
            var start_hour = ~~time_arr[0].split(":")[0];//~是按位取反运算，~~是取反两次 经过位运算的会自动变成整数 同parseInt()
            var start_minute = ~~time_arr[0].split(":")[1];
            var end_hour = ~~time_arr[1].split(":")[0];
            var end_minute = ~~time_arr[1].split(":")[1];

            $("#start_hour").val(start_hour);
            $("#start_minute").val(start_minute);
            $("#end_hour").val(end_hour);
            $("#end_minute").val(end_minute);

            $("#time_extra").val($(this).parent().prevAll(".time_extra").text());
        });

        //更新
        var $time_update_form = $("#time_update_form");
        $time_update_form.submit(function () {

            var id = $("#time_id").val();
            var start_time = $("#start_hour").val() + ":" + $("#start_minute").val();
            var end_time = $("#end_hour").val() + ":" + $("#end_minute").val();
            var extra = $("#time_extra").val();

            var $update_btn = $("#time_update_button");

            $.ajax({
                url: "ttime/update",
                type: "POST",
                dataType: "json",
                data: {id: id, startTime: start_time, endTime: end_time, extra: extra},
                beforeSend: function () {
                    $update_btn.button("loading");
                },
                complete: function () {
                    $update_btn.button("reset");
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        //菜单栏当前选中
                        $now_selected = $("ul.treeview-menu>li.active>a");
                        $now_selected.trigger("click");
                        $("#time_update_modal").modal("hide");
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    if (textStatus === "timeout") {
                        alert("更新超时！");
                    } else {
                        alert("更新失败！");
                    }
                }
            });
            return false;
        });
    })
</script>
</body>
</html>