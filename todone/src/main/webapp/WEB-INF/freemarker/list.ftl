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
<h1>欢迎您，${username}！</h1>
<hr>
<div class="span12">
    <div class="input-append">
        <form action="/add">
            <table>
                <thead>
                <tr>
                    <th >发送日期</th>
                    <th>leader</th>
                    <th>任务详情</th>
                    <th>发送者</th>
                    <th>已经耗时</th>
                    <th>总耗时</th>
                    <th>开始日期</th>
                    <th>描述</th>
                    <th></th>
                </tr>
                </thead>
                <tr>
                    <td><input type="text" id="sendDay" name="sendDay" onClick="getTime();" placeholder="点击选择时间"></td>
                    <td><input class="inp" type="text" name="leader"/></td>
                    <td><input style=" width: 350px;" type="text" name="task"/></td>
                    <td><input class="inp" type="text" name="sender"/></td>
                    <td><input class="inp" type="text" name="taskCosts"/></td>
                    <td><input class="inp" type="text" name="taskTotalCosts"/></td>
                    <td><input type="text" id="taskStartDay" name="taskStartDay" onClick="getTime();" placeholder="点击选择时间"></td>
                    <td><label>
                        <input type="text" name="desc"/>
                    </label></td>
                    <td><input type="submit" value="添加" class="btn btn"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<hr>
<div class="span12">
    <div class=""> </div>
    <div class="">
    <@page.show url="${rc.getContextPath()}/role/list_page?start=" totalPages="${rolePager.totalPageCount}" totalRecords="${rolePager.totalCount}" currPage="${rolePager.currentPageNo}" />

        <table class="table table-striped">
            <thead>
            <tr>
                <th>发送日期</th>
                <th>leader</th>
                <th>任务详情</th>
                <th>发送者</th>
                <th>已经耗时</th>
                <th>总耗时</th>
                <th>开始日期</th>
                <th>描述</th>
                <th>还想干什么</th>
            </tr>
            </thead>
            <tbody>
            <#list rolePager.data as dailyLog>
            <tr>
                <td>${dailyLog.sendDay}</td>
                <td>${dailyLog.leader}</td>
                <td>${dailyLog.task}</td>
                <td>${dailyLog.sender}</td>
                <td>${dailyLog.taskCosts}</td>
                <td>${dailyLog.taskTotalCosts}</td>
                <td>${dailyLog.taskStartDay}</td>
                <td>${dailyLog.desc}</td>
                <td>
                    <a href="#" >呵呵 笑一个</a>
                </td>
            </tr>

            </#list>

            </tbody>
        </table>

    <@page.show url="${rc.getContextPath()}/role/list_page?start=" totalPages="${rolePager.totalPageCount}" totalRecords="${rolePager.totalCount}" currPage="${rolePager.currentPageNo}" />

    </div>
</div>
</body>
</html>