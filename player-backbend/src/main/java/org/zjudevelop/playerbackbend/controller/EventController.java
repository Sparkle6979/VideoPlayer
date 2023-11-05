package org.zjudevelop.playerbackbend.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.domain.MessagePO;
import org.zjudevelop.playerbackbend.dto.MessageInfoDTO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.FollowService;
import org.zjudevelop.playerbackbend.service.LikeService;
import org.zjudevelop.playerbackbend.service.MessageService;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/event")
@Api(tags = "消息通知")
public class EventController extends MessageConstant {

    @Autowired
    private MessageService messageService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ApiOperation("获取当前用户收到的消息通知")
    public RestResult<List<MessageInfoDTO>> getEventInfo(){
        log.info("进入eventdetail");
        Long currentUserId = BaseContext.getCurrentUserId();

        List<MessagePO> collect = messageService.getUnreadReceiveMessageByUserId(currentUserId);

        List<MessageInfoDTO> result = new ArrayList<>();
        for (MessagePO messagePO : collect) {
            Integer noticeId = (Integer) (JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId"));

            if(EVENT_VIDEO_LIKE.equals(messagePO.getConversationType())){
                Likes likesById = likeService.getLikesById((long) noticeId.intValue());
                result.add(DTOUtil.makeMessageInfoDTO(messagePO,likesById));
            }else if(EVENT_USER_FOLLOW.equals(messagePO.getConversationType())){
                Follows followById = followService.getFollowById((long) noticeId.intValue());
                result.add(DTOUtil.makeMessageInfoDTO(messagePO,followById));
            }
        }

        return RestResult.success(result);
    }
}
