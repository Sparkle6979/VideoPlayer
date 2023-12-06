package org.zjudevelop.playerbackbend.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;
import org.zjudevelop.playerbackbend.domain.dto.MessageInfoDTO;
import org.zjudevelop.playerbackbend.pojo.MessageConstant;
import org.zjudevelop.playerbackbend.service.*;
import org.zjudevelop.playerbackbend.utils.DTOUtil;
import org.zjudevelop.playerbackbend.utils.RestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/5 15:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/message")
@Api(tags = "消息通知")
public class MessageController extends MessageConstant {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/like",method = RequestMethod.GET)
    @ApiOperation("当前用户收到的点赞消息通知")
    public RestResult<List<MessageInfoDTO>> getLikeEventInfo(){

        Long currentUserId = BaseContext.getCurrentUserId();

        List<MessagePO> collect = messageService.getUnreadReceiveLikeMessageByUserId(currentUserId);

        List<MessageInfoDTO> result = new ArrayList<>();
        for (MessagePO messagePO : collect) {
            Integer noticeId = (Integer) (JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId"));

            Long entityId = likeService.getLikesById((long) noticeId.intValue()).getVideoId();
            MessageInfoDTO messageInfoDTO = DTOUtil.makePartialMessageInfoDTO(messagePO, entityId);
            messageInfoDTO.setCurrentUserName(userService.getUserById(messagePO.getToId()).getUsername());
            messageInfoDTO.setEventUserName(userService.getUserById(messageInfoDTO.getEventUserId()).getUsername());
            result.add(messageInfoDTO);
        }

        return RestResult.success(result);
    }


    @RequestMapping(value = "/follow",method = RequestMethod.GET)
    @ApiOperation("当前用户收到的关注消息通知")
    public RestResult<List<MessageInfoDTO>> getFollowEventInfo(){

        Long currentUserId = BaseContext.getCurrentUserId();

        List<MessagePO> collect = messageService.getUnreadReceiveFollowMessageByUserId(currentUserId);

        List<MessageInfoDTO> result = new ArrayList<>();
        for (MessagePO messagePO : collect) {
            Integer noticeId = (Integer) (JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId"));

            Long entityId = followService.getFollowById((long) noticeId.intValue()).getFollowingId();
            MessageInfoDTO messageInfoDTO = DTOUtil.makePartialMessageInfoDTO(messagePO, entityId);
            messageInfoDTO.setCurrentUserName(userService.getUserById(messagePO.getToId()).getUsername());
            messageInfoDTO.setEventUserName(userService.getUserById(messageInfoDTO.getEventUserId()).getUsername());
            result.add(messageInfoDTO);
        }

        return RestResult.success(result);
    }

    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    @ApiOperation("当前用户收到的评论消息通知")
    public RestResult<List<MessageInfoDTO>> getCommentEventInfo(){

        Long currentUserId = BaseContext.getCurrentUserId();

        List<MessagePO> collect = messageService.getUnreadReceiveCommentMessageByUserId(currentUserId);

        List<MessageInfoDTO> result = new ArrayList<>();
        for (MessagePO messagePO : collect) {
            Integer noticeId = (Integer) (JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId"));

            Long entityId = commentService.getCommentById((long) noticeId.intValue()).getId();
            MessageInfoDTO messageInfoDTO = DTOUtil.makePartialMessageInfoDTO(messagePO, entityId);
            messageInfoDTO.setCurrentUserName(userService.getUserById(messagePO.getToId()).getUsername());
            messageInfoDTO.setEventUserName(userService.getUserById(messageInfoDTO.getEventUserId()).getUsername());
            result.add(messageInfoDTO);
        }

        return RestResult.success(result);
    }

    @GetMapping("/read")
    @ApiOperation("读取消息通知")
    public RestResult readMessage(@RequestParam Long messageId) {
        messageService.readMessage(messageId);
        return RestResult.success();
    }
}
