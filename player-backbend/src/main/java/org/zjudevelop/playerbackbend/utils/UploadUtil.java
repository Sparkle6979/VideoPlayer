package org.zjudevelop.playerbackbend.utils;

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
import org.zjudevelop.playerbackbend.domain.dto.UploadFileInfoDTO;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/12/13 17:21
 */
@Slf4j
public class UploadUtil {
    public static final UploadFileInfoDTO uploadfile(String localFileUrl, QNDataServer dataServer){
        UploadManager uploadManager = configinit(dataServer.getAccessKey(), dataServer.getSecretKey(), dataServer.getBucket());
        Auth auth = Auth.create(dataServer.getAccessKey(), dataServer.getSecretKey());
        String upToken = auth.uploadToken(dataServer.getBucket());

        // ...根据文件url生成文件名称
        String fileName = FileProcessUtil.getFileName(localFileUrl);
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

        String serverFileUrl = StringUtils.join(dataServer.getDomain(), "/", fileName);
        UploadFileInfoDTO uploadFileInfoDTO = UploadFileInfoDTO.builder()
                .serverFileUrl(serverFileUrl)
                .serverFileName(fileName).build();

        return uploadFileInfoDTO;
    }


    public static final UploadFileInfoDTO uploadfile(String fileName, byte[] data, QNDataServer dataServer){
        UploadManager uploadManager = configinit(dataServer.getAccessKey(), dataServer.getSecretKey(), dataServer.getBucket());
        Auth auth = Auth.create(dataServer.getAccessKey(), dataServer.getSecretKey());
        String upToken = auth.uploadToken(dataServer.getBucket());

        try {
            Response response = uploadManager.put(data, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            fileName = StringUtils.isBlank(fileName) ? putRet.key : fileName;
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

        String serverFileUrl = StringUtils.join(dataServer.getDomain(), "/", fileName);
        UploadFileInfoDTO uploadFileInfoDTO = UploadFileInfoDTO.builder()
                .serverFileUrl(serverFileUrl)
                .serverFileName(fileName).build();

        return uploadFileInfoDTO;
    }


    public static final UploadManager configinit(String accessKey,String secretKey,String bucket){
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
//        String accessKey = a;
//        String secretKey = dataServer.getSecretKey();
//        String bucket = dataServer.getBucket();
        return uploadManager;
    }
}
