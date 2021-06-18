package com.star.servlet;

import com.star.dao.HDFSDao;
import com.star.domain.User;
import com.star.utils.DownloadUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/preview")
public class Preview extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            String filename = request.getParameter("filename");
            String type = request.getParameter("type");
            System.out.println(filename);
            ServletContext servletContext = this.getServletContext();
            ServletOutputStream outputStream = response.getOutputStream();
            String agent = request.getHeader("user-agent");
            String mimeType = servletContext.getMimeType(filename);
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("content-type", mimeType);
            if ("download".equals(type)) {
                response.setHeader("content-disposition", "attachment;filename=" + DownloadUtils.getFileName(agent, filename));
            }
            HDFSDao hdfsDao = new HDFSDao();
            hdfsDao.downloadToStream(outputStream, "/qkx/homework/" + username + "/" + filename);
        } else {
            response.sendRedirect("login.html");    // 游客 先登陆
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
