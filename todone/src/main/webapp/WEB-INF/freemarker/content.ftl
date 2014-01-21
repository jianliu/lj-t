<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
     <link rel="stylesheet" href="/css/bootstrap.css" />
     <link rel="stylesheet" href="/css/bootstrap-responsive.css"/>
     <style>
     input, textarea {
         width: 80px;
     }
     .inp{
        width: 50px;
     }
     </style>
</head>
<body>
<script src="/js/jqery/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="/js/jqery/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
   $("#search2").click(function(){
    alert("hehe");
   });
   });
  function getTime(){
    WdatePicker({skin: 'default', dateFmt: 'yyyy-MM-dd'});
   }
</script>
<h1>欢迎您，hehe！</h1>
<hr>
<div class="span12">
<div class="input-append">

    </div>
    </div>
    <hr>
<div class="span12">
<div class="">  </div>
<div class="">
    <table class="table table-striped">

        <tbody>
        <#list list as foodItem>
        <tr>
            <td>
            <img src="http://img01.taobaocdn.com/bao/uploaded/i1/16630035969884313/T14ne0XEJfXXXXXXXX_!!0-item_pic.jpg" style="width:200px;height:120px;">
            <br />
            <div>[${foodItem.category}] ${foodItem.title} <div/> <br />
            <div> ${foodItem.price}<div/>
            </td>

        </tr>

        </#list>

        </tbody>
    </table>
</div>
</div>
</body>
</html>