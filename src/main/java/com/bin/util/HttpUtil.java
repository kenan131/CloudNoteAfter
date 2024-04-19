package com.bin.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author: bin.jiang
 * @date: 2023/3/14 17:11
 **/

public class  HttpUtil {
    //传送文件流给前端
    public static void sendFileUtil(HttpServletResponse response,String text,String bookName) throws IOException {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setContentType("application/octet-stream");
        bookName = URLEncoder.encode(bookName, "UTF-8");
        response.setHeader("Content-Disposition","attachment; filename=\" "+bookName+".md\"");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}
