package com.star.servlet;

import com.star.dao.DataDao;
import com.star.dao.HDFSDao;
import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/delete")
public class Delete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            response.setContentType("text/html;charset=utf-8");
            String filename = request.getParameter("filename");
            String hdfspath = "/qkx/homework/" + username + "/" + filename;
            if (new HDFSDao().delete(hdfspath)) {
                new DataDao().delete(hdfspath);
                response.getWriter().write("<script>alert(\"" + filename + "删除成功！\");location.href=\"download\";</script>");
            } else {
                response.getWriter().write("<script>alert(\"" + filename + "删除失败！\");location.href=\"download\";</script>");
            }
        } else {
            response.sendRedirect("login.html");    // 游客 先登陆
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
