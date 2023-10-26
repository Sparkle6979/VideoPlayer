package org.zjudevelop.playerbackbend.utils;

import org.zjudevelop.playerbackbend.pojo.LocalFile;

import java.io.File;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:28
 */
public class LocalFileUtil {
    public static String getFileName(LocalFile file){
        // 除去filepath前后空格
        String filepath = file.getFilepath().trim();
        return new File(filepath).getName();
    }

    public static String getFileSuffix(LocalFile file){
        String filepath = file.getFilepath().trim();
        int ind = filepath.indexOf('.');
        return (-1 == ind ? "" : filepath.substring(ind+1));
    }
}
