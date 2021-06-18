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
 * 修改密码 后台逻辑
 */

@WebServlet("/update")
public class Update extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password_old = request.getParameter("password_old");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password_old);
        User user1 = userDao.find(user);
        user.setPassword(password);
        if (user1 != null && userDao.update(user)) {
            // 密码修改成功
            response.getWriter().write(
                    "<p>密码修改成功！<span id=\"time\">2</span>秒后自动转跳登陆页面...</p>\n" +
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
            // 密码修改失败
            response.getWriter().write(
                    "<p>密码修改失败！原密码错误，<span id=\"time\">2</span>秒后自动转跳修改密码页面...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"update.html\";\n" +
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
