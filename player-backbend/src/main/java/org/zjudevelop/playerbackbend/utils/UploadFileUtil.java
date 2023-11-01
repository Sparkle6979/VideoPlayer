package org.zjudevelop.playerbackbend.utils;

import org.zjudevelop.playerbackbend.pojo.LocalFile;

import java.io.File;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:28
 */
public class UploadFileUtil {
    public static String getFileName(String url){
        // 除去filepath前后空格
        String filename = new File(url).getName();
        int ind = filename.indexOf('.');
        return filename.substring(0,ind);
    }

    public static String getFileSuffix(String url){
        String filepath = url.trim();
        int ind = filepath.indexOf('.');
        return (-1 == ind ? "" : filepath.substring(ind));
    }
}
