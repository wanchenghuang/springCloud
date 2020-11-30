<!DOCTYPE html>
<html>
<head lang="en">
    <title>pdf打印测试</title>
    <link href="http://localhost:9010/css/index.css" rel="stylesheet" type="text/css"/>
    <style>
        @page {
            size: A4 portrait;
            @bottom-center {
                font-family: SimSun;
                font-size: 12px;
                color: #000000;
                content: "第" counter(page) "页  共 " counter(pages) "页" ;
                padding-bottom: 150px;
                width: 100%;
            };
        }
    </style>
</head>
<body style="font-family: SimSun">
<h2>更改单和异常费用单对账单pdf打印测试</h2>
<br/>
<div>
    <table style="border: 0px;width: auto;">
        <tr>
            <th colspan="13">广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="13">对账单</th>
        </tr>
        <tr>
            <th colspan="13">工厂名称：广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="13">对账区间：广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="3">No：取对账单号取对账单号取对账单号</th>
            <th colspan="3">币种：CNY</th>
            <th colspan="2">税率：12%</th>
            <th colspan="5">制单时间：2020-09-04 12:00:32</th>
        </tr>
        <tr>
            <td>序号</td>
            <td>单据类型</td>
            <td>费用单行号</td>
            <td>产品名称</td>
            <td>产品型号</td>
            <td>费用类型</td>
            <td>异常人数</td>
            <td>工时(小时)</td>
            <td>辅料</td>
            <td>用量/面积</td>
            <td>本次更改数量</td>
            <td>未税单价</td>
            <td>未税金额</td>
            <td>未税金额</td>
        </tr>
        <#if users?? && (users?size>0)>
            <#list  users as user>
                <tr>
                    <#if user.id??>
                        <td>${user.id}</td>
                    </#if>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.salary}</td>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.salary}</td>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.salary}</td>
                    <td>${user.salary}</td>
                </tr>
            </#list>
        </#if>
        <tr>
            <td>合计</td>
            <td colspan="10"></td>
            <td>合计数</td>
            <td>合计数</td>
        </tr>
    </table>

</div>
<br/>
<br/>

</body>
</html>