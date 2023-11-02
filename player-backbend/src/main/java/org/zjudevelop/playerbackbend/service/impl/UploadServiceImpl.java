package org.zjudevelop.playerbackbend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dto.UploadFileInfoDTO;
import org.zjudevelop.playerbackbend.pojo.LocalFile;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.utils.LocalFileUtil;
import org.zjudevelop.playerbackbend.utils.UploadFileUtil;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:10
 */

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Override
    public UploadFileInfoDTO uploadfile(String localFileUrl, QNDataServer dataServer) {
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = dataServer.getAccessKey();
        String secretKey = dataServer.getSecretKey();
        String bucket = dataServer.getBucket();

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        // ...根据文件url生成文件名称
        String fileName = StringUtils.join(UploadFileUtil.getFileName(localFileUrl),
                UploadFileUtil.getFileSuffix(localFileUrl));
        try {
            Response response = uploadManager.put(localFileUrl, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
            log.info(putRet.key);
            log.info(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                log.error(ex.response.toString());
            }
        }
        // 生成云服务器外链路径
        String serverFileUrl = StringUtils.join(dataServer.getDomain(), "/", fileName);

        UploadFileInfoDTO uploadFileInfoDTO = UploadFileInfoDTO.builder()
                .serverFileUrl(serverFileUrl)
                .serverFileName(fileName).build();

        return uploadFileInfoDTO;
    }
}
