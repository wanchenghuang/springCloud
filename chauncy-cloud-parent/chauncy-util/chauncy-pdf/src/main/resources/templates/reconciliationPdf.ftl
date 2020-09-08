<!DOCTYPE html>
<html>
<head>
    <link href="http://localhost:8999/css/index.css" rel="stylesheet" type="text/css"/>
    <style>
        @page {
            size: 210mm 297mm; /*设置纸张大小:A4(210mm 297mm)、A3(297mm 420mm) 横向则反过来*/
            margin: 0.25in;
            padding: 1em;
            /*@bottom-center{*/
            /*content:"cvte © 版权所有";*/
            /*font-family: SimSun;*/
            /*font-size: 12px;*/
            /*color:red;*/
            /*};*/
            @top-center {
                content: element(header)
            };
            @bottom-center {
                content: "第" counter(page) "页  共 " counter(pages) "页";
                font-family: SimSun;
                font-size: 12px;
                color: #000;
            };
        }

        @page :right {
            @bottom-left {
                margin: 10pt 0 30pt 0;
                border-top: .25pt solid #666;
                content: "Our Cats";
                font-size: 9pt;
                color: #333;
            }
            @bottom-right {
                margin: 10pt 0 30pt 0;
                border-top: .25pt solid #666;
                content: counter(page);
                font-size: 9pt;
            }
            @top-right {
                content: string(doctitle);
                margin: 30pt 0 10pt 0;
                font-size: 9pt;
                color: #333;
            }
        }

        .tg {
            border-collapse: collapse;
            border-spacing: 0;
        }

        .tg td {
            font-family: 'SimSun';
            font-size: 13px;
            padding: 16px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-br /
            eak: normal;
        }

        /*.tg th {
            font-family: 'SimSun';
            font-size: 13px;
            font-weight: normal;
            padding: 16px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
        }*/

        .tg .tg-s6z2 {
            text-align: center
        }

        .tg .tg-baqh {
            text-align: center;
            vertical-align: top
        }

        .tg .tg-yw4l {
            vertical-align: center;
        }

        .tg .tg-noborder {
            border: none;
        }                    </style>
</head>
<body style="font-family:'SimSun';size: 210mm 297mm;margin: 0.25in;padding: 1em;">
<div style="width: 700px;margin: 0 auto;"><h4 style="text-align: center;color: crimson">更改单和异常费用单对账单pdf打印测试</h4>
    <table class="tg" style="width: 100%;table-layout: fixed; word-break:break-strict;">
        <tr>
            <th colspan="13">广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="13">对账单：</th>
        </tr>
        <tr>
            <th colspan="13">工厂名称：广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="13">对账区间：广州视睿电子科技有限公司</th>
        </tr>
        <tr>
            <th colspan="4">No：取对账单号取对账单号取对账单号</th>
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
        </tr>
        <#if users?? && (users?size>0)>
            <#list  users as user>
                <tr>
                    <#if user.id??>
                        <td>${user.id}</td>
                    </#if>
                    <#if user.name??>
                        <td>${user.name}</td>
                    </#if>
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
</body>
</html>

