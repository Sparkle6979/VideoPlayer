package org.zjudevelop.playerbackbend.utils;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/12/18 20:12
 */
public class RedisKeyUtil {

    public static final String PREFIX_USER = "video";
    public static final String SPILIT = "-";

    public static String getVideoKey(long videoId){
        return PREFIX_USER + SPILIT + videoId;
    }
}
