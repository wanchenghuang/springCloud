<!DOCTYPE html>
<html>
<head lang="en">
    <title>pdf打印测试</title>
    <link href="http://localhost:9010/css/index.css" rel="stylesheet" type="text/css"/>
    <style>
        @page {
            size: A4 portrait;
            margin: 198px 30px 100px 30px;
            @top-center {
                width: 100%;
                /*background-size: 100% 70px;*/
                /*background: url('http://localhost:9010/imgs/top.png') no-repeat center 76px;*/
            };
            @bottom-center {
                font-family: SimSun;
                font-size: 12px;
                color: #000000;
                content: "第" counter(page) "页  共 " counter(pages) "页" ;
                padding-bottom:150px;
                background-size: 100% 40px;
                background: url('http://localhost:9010/imgs/bottom.png') no-repeat center 45px;
                width: 100%;
            };
        }
    </style>
</head>
<body style="font-family: SimSun">
<div>正文在这里开始</div>
<h2>pdf打印测试</h2>
<br/>
<div>
    <table style="border: 0px;width: auto;">
        <tr>
            <th colspan="4">用户信息表</th>
        </tr>
        <tr>
            <td>id</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>薪资</td>
        </tr>
        <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.salary}</td>
            </tr>
        </#list>
    </table>

</div>
<br/>
<br/>

</body>
</html>