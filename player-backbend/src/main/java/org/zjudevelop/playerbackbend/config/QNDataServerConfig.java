package org.zjudevelop.playerbackbend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 10:44
 */
@Configuration
public class QNDataServerConfig {
    @Value("${dataserver.qndataserver.accesskey}")
    private String AccessKey;
    @Value("${dataserver.qndataserver.secretkey}")
    private String SecretKey;
    @Value("${dataserver.qndataserver.bucket}")
    private String Bucket;
    @Bean
    public QNDataServer qnDataServer(){
        QNDataServer qnDataServer = new QNDataServer();
        qnDataServer.setAccessKey(AccessKey);
        qnDataServer.setSecretKey(SecretKey);
        qnDataServer.setBucket(Bucket);
        return qnDataServer;
    }
}
