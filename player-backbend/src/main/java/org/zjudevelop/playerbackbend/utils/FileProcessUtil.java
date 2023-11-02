package org.zjudevelop.playerbackbend.utils;

import java.io.File;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:28
 */
public class FileProcessUtil {

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
