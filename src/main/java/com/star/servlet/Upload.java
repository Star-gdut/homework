package com.star.servlet;

import com.star.dao.DataDao;
import com.star.dao.HDFSDao;
import com.star.dao.UserDao;
import com.star.domain.Data;
import com.star.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            request.setCharacterEncoding("utf-8");
            Collection<Part> parts = request.getParts();
            HDFSDao hdfsDao = new HDFSDao();
            boolean f = false;
            String failname = "";
            for (Part part : parts) {
                if ("filename".equals(part.getName())) {
                    String header = part.getHeader("Content-Disposition");
                    String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
                    InputStream inputStream = part.getInputStream();
                    int available = inputStream.available();
                    double size = Math.round(available / 1024.0 / 1024.0 * 100.0) / 100.0;
                    System.out.println("文件大小：" + size + "MB");
                    String hdfspath = "/qkx/homework/" + username + "/" + filename;
                    String cachepath = this.getServletContext().getRealPath("/WEB-INF/") + "\\data\\" + username + "\\" + filename;
                    DataDao dataDao = new DataDao();
                    response.setContentType("text/html;charset=utf-8");
                    if (!dataDao.check(hdfspath)) {    // 检查文件名是否重复
                        if (hdfsDao.uploadFromStream(inputStream, hdfspath)) {  //  上传到HDFS中
                            Data data = new Data();
                            data.setFilename(filename);
                            data.setHdfspath(hdfspath);
                            data.setCachepath(cachepath);
                            data.setSize(size);
                            data.setUserid(new UserDao().showid(username));
                            boolean add = dataDao.add(data);
                            System.out.println(add);
                            System.out.println(data);
                            System.out.println("文件 " + filename + " 上传完成！");
                        }
                    } else {
                        f = true;
                        failname += filename + ", ";
                    }
                }
            }
            if (f) {
                response.getWriter().write("<script>alert(\"" + failname.substring(0, failname.length() - 2) + "文件已存在，无法上传！其余上传成功！\");location.href=\"upload.html\";</script>");
            } else {
                response.getWriter().write("<script>alert(\"全部文件上传成功！\");location.href=\"upload.html\";</script>");
            }
        } else {
            response.sendRedirect("login.html");    // 游客 先登陆
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
