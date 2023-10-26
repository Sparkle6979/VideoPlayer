package org.zjudevelop.playerbackbend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.pojo.LocalFile;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;
import org.zjudevelop.playerbackbend.service.UploadService;
import org.zjudevelop.playerbackbend.utils.LocalFileUtil;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:10
 */

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public Boolean uploadfile(LocalFile file, QNDataServer dataServer) {
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = dataServer.getAccessKey();
        String secretKey = dataServer.getSecretKey();
        String bucket = dataServer.getBucket();
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "/home/qiniu/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = LocalFileUtil.getFileName(file);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getFilepath(), key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }

        return Boolean.TRUE;
    }
}
