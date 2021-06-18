function delCookie(name) {//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
    var date = new Date();
    date.setTime(date.getTime() - 10000);
    document.cookie = name + "=a; expires=" + date.toGMTString();
    alert("已退出账户！");
}

function getCookie() {
    var allCookie = document.cookie;
    var array = allCookie.split(";");
    for (var i = 0; i < array.length; i++) {
        var item = array[i].split("=");
        if (item[0] === "username") {
            return item[1];
        }
    }
    return null;
}
window.onload = function () {
    var username = getCookie();
    var n = document.getElementById("name");
    if (username != null) {
        n.innerHTML = username + "，欢迎您！";
    }else {
        n.innerHTML = "游客&nbsp";
    }
}