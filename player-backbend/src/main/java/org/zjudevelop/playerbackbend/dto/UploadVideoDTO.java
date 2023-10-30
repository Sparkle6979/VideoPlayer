package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> feature/videopart
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zjudevelop.playerbackbend.pojo.LocalFile;
import org.zjudevelop.playerbackbend.pojo.QNDataServer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/26 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadVideoDTO {
    LocalFile localFile;
    QNDataServer qnDataServer;
}
