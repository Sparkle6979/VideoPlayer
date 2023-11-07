package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/1 23:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadFileInfoDTO {
    // 存储在云服务器的文件名称
    String serverFileName;
    // 云服务器文件所在外链Url
    String serverFileUrl;
}
