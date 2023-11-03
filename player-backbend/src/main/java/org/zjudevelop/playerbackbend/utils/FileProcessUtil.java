package org.zjudevelop.playerbackbend.utils;

import java.io.File;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/1 23:53
 */
public class FileProcessUtil {
    /**
     * 得到文件名称（不包含后缀名）
     * @param url
     * @return
     */
    public static String getFileName(String url){
        return new File(url).getName();
    }

    public static String getFileOriginName(String url){
        // 除去filepath前后空格
        String filename = getFileName(url);
        int ind = filename.indexOf('.');
        return filename.substring(0,ind);
    }

    public static String getFileSuffix(String url){
        String filepath = url.trim();
        int ind = filepath.indexOf('.');
        return (-1 == ind ? "" : filepath.substring(ind));
    }
}
