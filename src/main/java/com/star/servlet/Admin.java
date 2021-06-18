package com.star.servlet;

import com.star.dao.DataDao;
import com.star.dao.HDFSDao;
import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class Admin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String admin = request.getParameter("admin");
        String password = request.getParameter("password");
        User adminUser = new User();
        adminUser.setUsername(admin);
        adminUser.setPassword(password);
        UserDao userDao = new UserDao();
        User user = userDao.find(adminUser);
        if(user!=null&&"admin".equals(user.getRole())){
            User deleteUser = new User();
            deleteUser.setUsername(username);
            boolean delete = userDao.delete(deleteUser);
            HDFSDao hdfsDao = new HDFSDao();
            String hdfspath = "/qkx/homework/" + username;
            boolean b = hdfsDao.delete(hdfspath);
            if(delete){
                // 删除成功
                response.getWriter().write(
                        "<p>删除成功！<span id=\"time\">2</span>秒后自动转跳管理页面...</p>\n" +
                                "<script>\n" +
                                "    var second = 2;\n" +
                                "    function showTime() {\n" +
                                "        second --;\n" +
                                "        if(second<=0){\n" +
                                "            location.href=\"admin.html\";\n" +
                                "        }\n" +
                                "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                                "    }\n" +
                                "    setInterval(showTime,1000)\n" +
                                "</script>"
                );
            }else {
                // 删除失败
                response.getWriter().write(
                        "<p>删除失败！被删除的用户不存在，<span id=\"time\">2</span>秒后自动转跳管理页面...</p>\n" +
                                "<script>\n" +
                                "    var second = 2;\n" +
                                "    function showTime() {\n" +
                                "        second --;\n" +
                                "        if(second<=0){\n" +
                                "            location.href=\"admin.html\";\n" +
                                "        }\n" +
                                "        document.getElementById(\"time\").innerHTML = second+\"\";\n" +
                                "    }\n" +
                                "    setInterval(showTime,1000)\n" +
                                "</script>"
                );
            }
        }else {
            // 管理员用户名或密码错误
            response.getWriter().write(
                    "<p>删除失败！管理员用户名或密码错误，<span id=\"time\">2</span>秒后自动转跳管理页面...</p>\n" +
                            "<script>\n" +
                            "    var second = 2;\n" +
                            "    function showTime() {\n" +
                            "        second --;\n" +
                            "        if(second<=0){\n" +
                            "            location.href=\"admin.html\";\n" +
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
