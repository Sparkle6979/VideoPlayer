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
    @Value("${dataserver.qndataserver.domain}")
    private String Domain;
    @Bean
    public QNDataServer qnDataServer(){

        return QNDataServer.builder()
                .AccessKey(AccessKey)
                .SecretKey(SecretKey)
                .Bucket(Bucket)
                .Domain(Domain)
                .build();
    }
}
