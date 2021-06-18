package com.star.servlet;

import com.star.dao.DataDao;
import com.star.dao.HDFSDao;
import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/format")
public class Format extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        UserDao userDao = new UserDao();
        User user = userDao.find(loginUser);
        if (user == null) {
            // 清空失败，用户名或密码错误
            response.getWriter().write(
                    "<p>清空失败！用户名或密码错误，<span id=\"time\">2</span>秒后自动转跳原页面...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"format.html\";\n" +
                            "        }\n" +
                            "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                            "    }\n" +
                            "    setInterval(showTime,1000)\n" +
                            "</script>"
            );
        } else {
            HDFSDao hdfsDao = new HDFSDao();
            String hdfspath = "/qkx/homework/" + username;
            boolean delete = hdfsDao.delete(hdfspath);
            System.out.println("11111+" + delete);
            if (delete) {
                // 删除数据库中的记录
                int showid = userDao.showid(username);
                boolean b = new DataDao().deleteByUserId(showid);
                System.out.println("11111+" + b);
                // 清理成功
                response.getWriter().write(
                        "<p>清空成功！<span id=\"time\">2</span>秒后自动转跳我的...</p>\n" +
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
            } else {
                // 清理失败
                response.getWriter().write(
                        "<p>清空失败！<span id=\"time\">2</span>秒后自动转跳首页...</p>\n" +
                                "<script>\n" +
                                "    var second = 2;\n" +
                                "    function showTime() {\n" +
                                "        second --;\n" +
                                "        if(second<=0){\n" +
                                "            location.href=\"index.html\";\n" +
                                "        }\n" +
                                "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                                "    }\n" +
                                "    setInterval(showTime,1000)\n" +
                                "</script>"
                );
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
