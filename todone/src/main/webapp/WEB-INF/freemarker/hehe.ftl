<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
     <link rel="stylesheet" href="/css/bootstrap.css" />
     <link rel="stylesheet" href="/css/bootstrap-responsive.css"/>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <a class="brand" href="#">d-sys</a>
        <ul class="nav">
            <li class="active"><a href="#">首页</a></li>
            <li><a href="/login">登录</a></li>
            <li><a href="#">讨论</a></li>
        </ul>
    </div>
</div>
<div>欢迎您，${username}！</div>
<hr>
<div>欢迎您，${username}！at ${time}</div>
<script src="/js/jqery/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="/js/jqery/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
  function getTime(){
    WdatePicker({skin: 'default', dateFmt: 'yyyy-MM-dd'});
}
</script>
</body>
</html>