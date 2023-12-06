package org.zjudevelop.playerbackbend.service;


import org.zjudevelop.playerbackbend.domain.dto.UploadFileInfoDTO;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 00:55
 */
public interface UploadService {
    /**
     * 上传对应文件至云服务器
     * @param localFileUrl 本地文件路径
     * @param dataServer   云服务器相关信息
     * @return
     */
    UploadFileInfoDTO uploadfile(String localFileUrl, QNDataServer dataServer);

    UploadFileInfoDTO uploadfile(String fileName, byte[] data, QNDataServer dataServer);
}
