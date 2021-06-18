package com.star.servlet;

import com.star.dao.DataDao;
import com.star.dao.HDFSDao;
import com.star.dao.UserDao;
import com.star.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/download")
public class Download extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String username = user.getUsername();
            UserDao userDao = new UserDao();
            int userid = userDao.showid(username);
            DataDao dataDao = new DataDao();
            List<String> allFilename = dataDao.getAllFilename(userid);
            response.setContentType("text/html;charset=utf-8");
            String htmlTop = getHtmlTop();
            String htmlFoot = getHtmlFoot();
            String html = "";
            for (String filename : allFilename) {
                double size = dataDao.getSize(filename, userid);
                String date = dataDao.getDate(filename, userid);
                html += "<tr class=\"s\">\n" +
                        "   <td class=\"td-left\"><input type=\"button\" onclick=\"location.href='preview?type=download&filename=" + filename + "'\" value=\"下载\"></td>\n" +
                        "   <td class=\"td-lr\"><a href=\"preview?type=preview&filename=" + filename + "\" target=\"_blank\">" + filename + "</a></td>\n" +
                        "   <td class=\"td-middle\">" + date + "</td>\n" +
                        "   <td class=\"td-rl\">" + size + "MB</td>\n" +
                        "   <td class=\"td-right\"><input type=\"button\" onclick=\"if(confirm('你确定要删除" + filename + "吗？')){location.href='delete?filename=" + filename + "'}\" value=\"删除\"></td>\n" +
                        "</tr>";
            }
            response.getWriter().write(htmlTop + html + htmlFoot);
        } else {
            response.sendRedirect("login.html");    // 游客 先登陆
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    private String getHtmlTop() {
        String html_top = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>管理照片 | 照片储存管理系统</title>\n" +
                "        <script src=\"base.js\"></script>\n" +
                "        <style>@import \"base.css\";</style>\n" +
                "        <style>\n" +
                "            #b {\n" +
                "                cursor: default;\n" +
                "                background-color: navajowhite;\n" +
                "            }\n" +
                "            h2 {\n" +
                "                width: 80%;\n" +
                "                margin: 0 auto;\n" +
                "                color: darkslategrey;\n" +
                "                text-align: center;\n" +
                "                line-height: 100px;\n" +
                "                font-size: 24px;\n" +
                "            }\n" +
                "            .table2 {\n" +
                "                width: 800px;\n" +
                "                padding: 20px;\n" +
                "            }\n" +
                "\n" +
                "            .ss{\n" +
                "                height: 30px;\n" +
                "            }\n" +
                "\n" +
                "            .s{\n" +
                "                height: 40px;\n" +
                "            }" +
                "\n" +
                "            .td-left {\n" +
                "                text-align: right;\n" +
                "                padding-right: 30px;\n" +
                "                width: 80px;\n" +
                "            }\n" +
                "            .td-lr{\n" +
                "                text-align: left;\n" +
                "                padding-left: 30px;\n" +
                "                width: 150px;\n" +
                "            }\n" +
                "\n" +
                "            .td-right {\n" +
                "                text-align: left;\n" +
                "                padding-left: 30px;\n" +
                "                width: 80px;\n" +
                "            }\n" +
                "            .td-rl{\n" +
                "                text-align: left;\n" +
                "                padding-left: 30px;\n" +
                "                width: 80px;\n" +
                "            }\n" +
                "\n" +
                "            .td-middle {\n" +
                "                width: 150px;\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "\n" +
                "            a:link {\n" +
                "                color: brown;\n" +
                "            }\n" +
                "\n" +
                "            a:visited {\n" +
                "                color: darkblue;\n" +
                "            }\n" +
                "\n" +
                "            a:hover {\n" +
                "                color: red;\n" +
                "            }\n" +
                "\n" +
                "            th[class=td-lr]{\n" +
                "                text-align: left;\n" +
                "                padding-left: 60px;\n" +
                "                width: 150px;\n" +
                "            }\n" +
                "\n" +
                "            th[class=td-right]{\n" +
                "                text-align: left;\n" +
                "                padding-left: 30px;\n" +
                "                width: 160px;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <hr color=\"grey\" width=\"80%\" size=\"2\">\n" +
                "        <p id=\"name\"></p>\n" +
                "        <h1>照片存储管理系统</h1>\n" +
                "        <hr color=\"grey\" width=\"80%\" size=\"2\">\n" +
                "        <div>\n" +
                "            <table align=\"center\" class=\"table1\">\n" +
                "                <tr>\n" +
                "                    <td id=\"a\" onclick=\"location.href='index.html'\"><span>首页</span></td>\n" +
                "                    <td id=\"b\" onclick=\"location.href='my'\"><span>我的</span></td>\n" +
                "                    <td id=\"c\" onclick=\"location.href='update.html'\"><span>修改密码</span></td>\n" +
                "                    <td id=\"d\" onclick=\"location.href='login.html'\"><span>切换账户</span></td>\n" +
                "                    <td id=\"e\" onclick=\"delCookie('username');location.href='logout'\"><span>退出账户</span></td>\n" +
                "                    <td id=\"f\" onclick=\"location.href='admin.html'\"><span>系统管理</span></td>\n" +
                "                    <td id=\"g\" onclick=\"location.href='login.html'\"><span>登陆/注册</span></td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <hr color=\"grey\" width=\"80%\" size=\"2\">\n" +
                "        <h2>我的 | 管理照片</h2>\n" +
                "        <div>\n" +
                "            <table align=\"center\" class=\"table2\">\n" +
                "                <tr>\n" +
                "                    <th width=\"80\"></th>\n" +
                "                    <th class=\"td-lr\"><b>文件名称</b></th>\n" +
                "                    <th class=\"td-middle\"><b>上传时间</b></th>\n" +
                "                    <th colspan=\"2\" class=\"td-right\"><b>文件大小</b></th>\n" +
                "                </tr>\n" +
                "                <tr class=\"ss\"><td colspan=\"5\"><hr color=\"black\"></td></tr>";
        return html_top;
    }

    private String getHtmlFoot() {
        String html_foot = "</table>\n" +
                "        </div>\n" +
                "        <br><br><br><br><br><br><br><br><br><br><div align='center'>没有照片了哦~</div>\n" +
                "    </body>\n" +
                "</html>";
        return html_foot;
    }
}
