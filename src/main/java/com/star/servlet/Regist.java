package com.star.servlet;

import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册 后台逻辑
 */

@WebServlet("/regist")
public class Regist extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("有人注册啦...");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UserDao userDao = new UserDao();
        boolean add = userDao.add(user);
        if (add) {
            // 注册成功
            response.getWriter().write(
                    "<p>注册成功！<span id=\"time\">2</span>秒后自动转跳登陆页面...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"login.html\";\n" +
                            "        }\n" +
                            "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                            "    }\n" +
                            "    setInterval(showTime,1000)\n" +
                            "</script>"
            );
        } else {
            // 注册失败 用户名已存在
            response.getWriter().write(
                    "<p>注册失败！该用户名已存在！<span id=\"time\">2</span>秒后自动转跳注册页面...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"regist.html\";\n" +
                            "        }\n" +
                            "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                            "    }\n" +
                            "    setInterval(showTime,1000)\n" +
                            "</script>"
            );
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
