package org.zjudevelop.playerbackbend.service;


import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;
import org.zjudevelop.playerbackbend.dto.VideoUpdateDTO;
import org.zjudevelop.playerbackbend.pojo.LocalFile;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 00:55
 */
public interface UploadService {
    VideoInfoDTO uploadfile(VideoUpdateDTO file, QNDataServer dataServer);
}
