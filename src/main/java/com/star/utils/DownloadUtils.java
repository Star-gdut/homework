package com.star.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Base64.Encoder;


public class DownloadUtils {
    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        System.out.println(agent);
        if (agent.contains("MSIE")) {
            // IE浏览器 （IE11中不再包含“MSIE”标识）
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            Encoder encoder = Base64.getEncoder();
//            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?" + encoder.encodeToString(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
