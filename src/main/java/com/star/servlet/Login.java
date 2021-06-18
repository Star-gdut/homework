package com.star.servlet;

import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 登陆 后台逻辑
 */

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        UserDao userDao = new UserDao();
        System.out.println(loginUser);
        User user = userDao.find(loginUser);
        if (user == null) {
            // 登陆失败
            response.getWriter().write(
                    "<p>登陆失败！用户名或密码错误，<span id=\"time\">2</span>秒后自动转跳登陆页面...</p>\n" +
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
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            Cookie cookie = new Cookie("username", URLEncoder.encode(username, "utf-8"));
            cookie.setMaxAge(1800);     // 登陆状态保持时间
            response.addCookie(cookie);
            // 登陆成功
            response.getWriter().write(
                    "<p>登陆成功！<span id=\"time\">2</span>秒后自动转跳我的...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"my.html\";\n" +
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
