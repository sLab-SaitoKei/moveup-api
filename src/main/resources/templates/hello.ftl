<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/><br/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
</head>
<body style="font-family: SimSun;">
<div id="bro" style="border: 1px;">
    <p style="text-align:right">表示日${time}&emsp;</p>
    <h1 style="text-align:center">《領収書》</h1>
    <div style="position: absolute;left: 180px;right: 80px;height: 160px;">
        <div id="tab" style="width: 100%;text-align:left;padding-left:20%;">
            <span style="width: 20%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="text-align:left;">
                <b>宛名</b>
            </span>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
            <span style="font-size: 20px;">${shopName}御中</span>
        </div>
        <hr style="width: 70%;text-align: center;"/>
        <div id="tab" style="width: 100%;text-align:left;padding-left:20%;">
            <span style="width: 20%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="text-align:left;">
                <b>金額</b>
            </span>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
            <span style="font-size: 20px;">${subtotal}</span>
        </div>
        <hr style="width: 70%;text-align: center;"/>
        <div id="tab" style="width: 100%;text-align:left;padding-left:20%;">
            <span style="width: 20%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="text-align:left;">
                <b>但し</b>
            </span>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
            <span style="font-size: 14px;">JAPAN MOVE UP WEST WEB ${yyyy}年${mm}月分 利用料</span>
        </div>
        <hr style="width: 70%;text-align: center;"/>
    </div>
    <p style=" text-align:right;margin-top: 10px;margin-right: 10px;font-size:14px;"><h2>株式会社HEADLINE WEST&emsp;</h2></p>
    <p style="text-align:right;margin-top: 10px;font-size:12px;">岡山県岡山市北区大元上町12-14&emsp;</p>
    <p style="text-align:right;margin-top: 10px;font-size:12px;">Lee building1F&emsp;</p>
    <p style="text-align:right;margin-top: 10px;font-size:12px;">TEL:086-250-8089／FAX:086-246-0588&emsp;</p>
    <hr style="width: 98%;text-align: center;"/>
    <h4>&emsp;《ご利用明細》</h4>
    <table border="1px" width="700px" align="center" cellspacing="0"  cellpadding="0">
        <tr>
            <td align="center" width="30%">項目</td>
            <td align="center">数量</td>
            <td align="center">単位</td>
            <td align="center">金額</td>
            <td align="center">備考</td>
        </tr>
        <#list scores as item>
        <tr>
            <td align="center">${item.title}</td>
            <td align="center">1</td>
            <td align="center">${item.type}</td>
            <td align="center">${item.amount}</td>
            <td align="center">&emsp;</td>
        </tr>
        </#list>
        <tr>
            <td border-bottom="0px" colspan="2"></td>
            <td align="center">小計</td>
            <td align="center">${totlePrice}</td>
            <td align="center">&emsp;</td>
        </tr>
        <tr>
            <td border="0px" colspan="2"></td>
            <td align="center">消費税(8%)</td>
            <td align="center">${tax}</td>
            <td align="center">&emsp;</td>
        </tr>
        <tr>
            <td border="0px" colspan="2" ></td>
            <td align="center">合計金額</td>
            <td align="center">${subtotal}</td>
            <td align="center">&emsp;</td>
        </tr>
    </table>

    <p style="text-align:left;margin-top: 10px;font-size:12px;">※この領収書は株式会社HEADLINE WESTが電子的に保持している領収情報を画面表示したものです。</p>
    <p style="text-align:left;margin-top: 10px;font-size:12px;">・お引き落とし日：当月利用料⇒翌月23日となります。（土日祝の場合は翌営業日のお引き落としとなります）</p>
</div>
</body>
</html>