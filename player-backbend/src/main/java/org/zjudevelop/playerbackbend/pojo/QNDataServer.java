package org.zjudevelop.playerbackbend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 01:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QNDataServer {
    private String AccessKey;
    private String SecretKey;
    private String bucket;
}
