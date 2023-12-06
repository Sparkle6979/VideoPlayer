package org.zjudevelop.playerbackbend.utils;

import lombok.extern.slf4j.Slf4j;
import org.zjudevelop.playerbackbend.domain.po.CategoryPO;
import org.zjudevelop.playerbackbend.domain.po.MessagePO;
import org.zjudevelop.playerbackbend.domain.po.User;
import org.zjudevelop.playerbackbend.domain.po.VideoPO;
import org.zjudevelop.playerbackbend.domain.dto.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 20:50
 */
@Slf4j
public class DTOUtil {

    /**
     * 日期格式
     */
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static VideoInfoDTO makeVideoInfoDTO(VideoPO videoPO, CategoryPO categoryPO) {
        String datetime = dateFormat.format(videoPO.getCreateTime());

        VideoInfoDTO videoInfoDTO = VideoInfoDTO.builder()
                .videoId(videoPO.getId())
                .title(videoPO.getTitle())
                .categoryId(videoPO.getCategoryId())
                .categoryName(categoryPO.getCategoryName())
                .description(videoPO.getDescription())
                .likeCount(videoPO.getLikeCount())
                .videoUrl(videoPO.getVideoUrl())
                .coverUrl(videoPO.getCoverUrl())
                .createTime(datetime)
                .build();

        return videoInfoDTO;
    }

    public static VideoInfoDTO makeVideoInfoDTO(VideoPO videoPO, String categoryName) {
        String datetime = dateFormat.format(videoPO.getCreateTime());

        VideoInfoDTO videoInfoDTO = VideoInfoDTO.builder()
                .videoId(videoPO.getId())
                .title(videoPO.getTitle())
                .categoryId(videoPO.getCategoryId())
                .categoryName(categoryName)
                .description(videoPO.getDescription())
                .likeCount(videoPO.getLikeCount())
                .videoUrl(videoPO.getVideoUrl())
                .coverUrl(videoPO.getCoverUrl())
                .createTime(datetime)
                .build();

        return videoInfoDTO;
    }

    public static CategoryInfoDTO makeCategoryInfoDTO(CategoryPO categoryPO){
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO().builder()
                .categoryId(categoryPO.getId())
                .categoryName(categoryPO.getCategoryName())
                .build();
        return categoryInfoDTO;
    }

    public static VideoSearchInfoDTO makeVideoSearchInfoDTO(String keyword, VideoPO videoPO, String categoryName){
        VideoInfoDTO videoInfoDTO = makeVideoInfoDTO(videoPO, categoryName);
        VideoSearchInfoDTO videoSearchInfoDTO = VideoSearchInfoDTO.builder()
                .keyword(keyword)
                .videoId(videoInfoDTO.getVideoId())
                .title(videoInfoDTO.getTitle())
                .categoryId(videoInfoDTO.getCategoryId())
                .categoryName(videoInfoDTO.getCategoryName())
                .createTime(videoInfoDTO.getCreateTime())
                .description(videoInfoDTO.getDescription())
                .likeCount(videoInfoDTO.getLikeCount())
                .coverUrl(videoInfoDTO.getCoverUrl())
                .videoUrl(videoInfoDTO.getVideoUrl())
                .build();
        return videoSearchInfoDTO;
    }

    public static VideoSearchInfoDTO makeVideoSearchInfoDTO(String keyword, VideoPO videoPO, CategoryPO categoryPO){
        return makeVideoSearchInfoDTO(keyword,videoPO,categoryPO.getCategoryName());
    }

    public static UserInfoDTO makeUserInfoDTO(User user){
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarPath(user.getAvatarPath())
                .build();
        return userInfoDTO;
    }

//    public static MessageInfoDTO makeMessageInfoDTO(MessagePO messagePO, Likes likes){
//
//        MessageInfoDTO noticeId = MessageInfoDTO.builder()
//                .MessageId(messagePO.getId())
//                .currentUserId(messagePO.getToId())
//                .conversationType(messagePO.getConversationType())
//                .EventUserId(messagePO.getFromId())
//                .EventEntityId(likes.getVideoId())
////                .EventEntityId((long)((Integer)JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId")).intValue())
//                .build();
//        return noticeId;
//    }
//
//
//    public static MessageInfoDTO makeMessageInfoDTO(MessagePO messagePO, Follows follows){
//
//        MessageInfoDTO noticeId = MessageInfoDTO.builder()
//                .MessageId(messagePO.getId())
//                .currentUserId(messagePO.getToId())
//                .conversationType(messagePO.getConversationType())
//                .EventUserId(messagePO.getFromId())
//                .EventEntityId(follows.getFollowingId())
////                .EventEntityId((long)((Integer)JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId")).intValue())
//                .build();
//        return noticeId;
//    }

    public static MessageInfoDTO makePartialMessageInfoDTO(MessagePO messagePO, Long entityId){

        MessageInfoDTO noticeId = MessageInfoDTO.builder()
                .messageId(messagePO.getId())
                .currentUserId(messagePO.getToId())
                .conversationType(messagePO.getConversationType())
                .eventUserId(messagePO.getFromId())
                .eventEntityId(entityId)
//                .EventEntityId((long)((Integer)JSONObject.parseObject(messagePO.getContent(), Map.class).get("noticeId")).intValue())
                .build();
        return noticeId;
    }
}
