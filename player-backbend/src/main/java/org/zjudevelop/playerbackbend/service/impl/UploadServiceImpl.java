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
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.utils.FileProcessUtil;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:10
 */

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
//    @Override
//    public VideoInfoDTO uploadvideo(VideoUpdateDTO videoUpdateDTO, QNDataServer dataServer) {
//        Configuration cfg = new Configuration(Region.region0());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //...生成上传凭证，然后准备上传
//        String accessKey = dataServer.getAccessKey();
//        String secretKey = dataServer.getSecretKey();
//        String bucket = dataServer.getBucket();
//
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
////        String localFilePath = "/home/qiniu/test.png";
//
//
//        String videoUrl = videoUpdateDTO.getVideoUrl();
//        String videoName = StringUtils.join(UploadFileUtil.getFileName(videoUrl),
//                UploadFileUtil.getFileSuffix(videoUrl));
//
//        try {
//
//            Response videoResponse = uploadManager.put(videoUrl, videoName, upToken);
//
//
//            if(StringUtils.isNotBlank(videoUpdateDTO.getCoverUrl())){
//                Mat coverImg = opencv_imgcodecs.imread(videoUpdateDTO.getCoverUrl());
//                Response coverResponse = uploadManager.put(coverUrl,coverName,upToken);
//            }else {
//                uploadManager.p
//            }
//
//
//
//
//
//
//
//            //解析上传成功的结果
//            DefaultPutRet videoPutRet = JSON.parseObject(videoResponse.bodyString(), DefaultPutRet.class);
//
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//
//        } catch (QiniuException ex) {
//            ex.printStackTrace();
//            if (ex.response != null) {
//                System.err.println(ex.response);
//                try {
//                    String body = ex.response.toString();
//                    System.err.println(body);
//                } catch (Exception ignored) {
//                }
//            }
//        }
//
//        return Boolean.TRUE;
//    }

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

    @Override
    public UploadFileInfoDTO uploadfile(String fileName, byte[] data, QNDataServer dataServer) {
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
}
