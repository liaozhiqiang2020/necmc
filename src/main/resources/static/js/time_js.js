// JavaScript Document

// Date: 2014-11-07
// Author: Agnes Xu


var i = 0;
var j = 0;
var count = 0;
// MM = 0;
// SS = 0;  // 秒 90s
// MS = 0;
var totle = (MM + 1) * gameTime * 10;
var d = 180 * (MM + 1);
MM = MM;

// var tmpA = true;
// var tmpB = tmpA;
// function autoRefurbish(){
//     alert(888);
//     tmpB = tmpA;
//     if(tmpB != tmpA){
//         window.location.href="https://www.infhp.cn/mc/alipay/alipayScan";
//         clearTimeout(ss);
//     }
// }
// var ss = setTimeout("autoRefurbish()",500);	//每秒刷一次

var s;
var t1;
var countDown = function () {
    i = 0;
    j = 0;
    count = 0;
    totle = (MM + 1) * gameTime * 10;
    d = 180 * (MM + 1);
    MM = "0" + MM;

    showTime();

    s = setInterval("showTime()", 100);
    start1();
    t1 = setInterval("start1()", 100);
}


var showTime = function () {
    totle = totle - 1;
    if (totle == 0) {
        clearInterval(s);
        clearInterval(t1);
        // clearInterval(t2);
        $(".pie2").css("-o-transform", "rotate(" + d + "deg)");
        $(".pie2").css("-moz-transform", "rotate(" + d + "deg)");
        $(".pie2").css("-webkit-transform", "rotate(" + d + "deg)");
    } else {
        if (totle > 0 && MS > 0) {
            MS = MS - 1;
            if (MS < 10) {
                MS = "0" + MS
            }
            ;
        }
        ;
        if (MS == 0 && SS > 0) {
            MS = 10;
            SS = SS - 1;
            if (SS < 10) {
                SS = "0" + SS
            }
            ;
        }
        ;
        if (SS == 0 && MM > 0) {
            SS = 60;
            MM = MM - 1;
            if (MM < 10) {
                MM = MM;
            }
            ;
        }
        ;
        if (SS == 0 && MM == 0) {
            // tmpA=false;
            console.log($("#orderNo").val().split("_")[1]);
            // var auth_app_id = getQueryString("buyer_user_id");

            clearInterval(s);
            clearInterval(t1);
            $(".time").html("按摩完成！");
            // console.log(auth_app_id);
            window.location.href="https://www.infhp.cn/mc/alipay/turnToAlipayScan?auth_app_id="+$("#orderNo").val().split("_")[1];
            return;
        }
    }
    ;
    $(".time").html(MM + "m," + SS + "s");
};


var start1 = function () {
    i = i + 360 / ((gameTime) * 10);  //旋转的角度  90s 为 0.4  60s为0.6
    count = count + 1;
    if (count <= (gameTime / 2 * 10)) {  // 一半的角度  90s 为 450
        $(".pie1").css("-o-transform", "rotate(" + i + "deg)");
        $(".pie1").css("-moz-transform", "rotate(" + i + "deg)");
        $(".pie1").css("-webkit-transform", "rotate(" + i + "deg)");
    } else {
        $(".pie2").css("backgroundColor", "#dedede");
        $(".pie2").css("-o-transform", "rotate(" + i + "deg)");
        $(".pie2").css("-moz-transform", "rotate(" + i + "deg)");
        $(".pie2").css("-webkit-transform", "rotate(" + i + "deg)");
    }
};


var start2 = function () {
    j = j + 0.6;
    count = count + 1;
    if (count == 300) {
        count = 0;
        clearInterval(t2);
        t1 = setInterval("start1()", 100);
    }
    $(".pie2").css("-o-transform", "rotate(" + j + "deg)");
    $(".pie2").css("-moz-transform", "rotate(" + j + "deg)");
    $(".pie2").css("-webkit-transform", "rotate(" + j + "deg)");
};


//获取url中的参数值
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}




