<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
  
  <style>
    body,button, input, select, textarea,h1 ,h2, h3, h4, h5, h6 {
      font-family: Microsoft YaHei,"宋体" , Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif;
    }
    .content-header > .breadcrumb {
      position: relative;
      margin-top: 5px;
      top: 0;
      right: 0;
      float: none;
      background: #d2d6de;
      padding-left: 10px;
    }
    .content-header > .breadcrumb li:before {
      color: #97a0b3;
    }
  </style>
</head>
<body>
<!-- Content Header (Page header) -->
<section class="content-header">
  <ol class="breadcrumb">
    <li><a href="/Xungeng/main/showInfo"><i class="fa fa-dashboard"></i> 首页</a></li>
    <li class="active">系统总览</li>
  </ol>
</section>

<!-- Main content -->
<section class="content">
  <!-- Small boxes (Stat box) -->
  <div class="row">
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-green">
        <div class="inner">
          <h3>${taskNumbers[0] }</h3>
          <p>已完成任务</p>
        </div>
        <a data-pjax href="/Xungeng/patrol/showTaskList" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-aqua">
        <div class="inner">
          <h3>${taskNumbers[1] }</h3>
          <p>待完成任务</p>
        </div>
        <a data-pjax href="/Xungeng/patrol/showTaskList" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-yellow">
        <div class="inner">
          <h3>${taskNumbers[2] }</h3>
          <p>未按时完成任务</p>
        </div>
        <a data-pjax href="/Xungeng/patrol/showTaskList" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-3 col-xs-6">
      <!-- small box -->
      <div class="small-box bg-red">
        <div class="inner">
          <h3>${taskNumbers[3] }</h3>
          <p>未完成任务</p>
        </div>
        <a data-pjax href="/Xungeng/patrol/showTaskList" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
      </div>
    </div>
    <!-- ./col -->
  </div>
  <div class="row">
    <div class="col-xs-12">
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">服务器基本信息</h3>
        </div>
        <!-- /.box-header -->
        <div class="box-body table-responsive">
          <table class="table table-bordered">
            <colgroup class="row">
              <col class="col-xs-2">
              <col class="col-xs-4">
              <col class="col-xs-2">
              <col class="col-xs-4">
            </colgroup>
            <tr>
              <td>服务器操作系统：</td>
              <td>Windows Server</td>
              <td>服务器IP：</td>
              <td></td>
            </tr>
            <tr>
              <td>Web 服务器：</td>
              <td>apache-tomcat-7.0.72</td>
              <td>Java 版本：</td>
              <td>1.8.0_73</td>
            </tr>
            <tr>
              <td>MySQL 版本：</td>
              <td>5.5.8</td>
              <td>当前时区：</td>
              <td>Asia/Shanghai</td>
            </tr>
            <tr>
              <td>文件上传最大：</td>
              <td>50M</td>
              <td>软件版本：</td>
              <td>V1.0 beta</td>
            </tr>
          </table>
        </div>
        <!-- /.box-body -->
      </div>
      <!-- /.box -->
    </div>
  </div>
  <!-- /.row -->
</section>
<!-- /.content -->
</body>
</html>