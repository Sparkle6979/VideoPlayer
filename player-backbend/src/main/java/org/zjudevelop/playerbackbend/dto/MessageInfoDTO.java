package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageInfoDTO {

    Long messageId;

    Long currentUserId;

    String currentUserName;

    String conversationType;

    Long eventUserId;

    String eventUserName;

    Long eventEntityId;


}
