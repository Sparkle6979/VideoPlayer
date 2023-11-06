package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.*;
import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.dto.VideoInfoDTO;

import java.util.List;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    User registry(UserRegisterDTO userRegisterDTO);

    int update(User user);

    User getUserById(Long id);

    int like(Likes likes);

    int unlike(Likes likes);

    List<Likes> getLikes(Long userId);

    int follow(Follows follows);

    int unfollow(Follows follows);

    Long comment(CommentPO commentPO);

    int create(Creates creates);

    List<Creates> getCreates(Long userId);

    List<Follows> getFollowings(Long userId);

    List<Follows> getFollowers(Long userId);

    List<VideoInfoDTO> getOwnVideosById(Long userId);

    boolean isAlreadyLiked(Long userId, Long videoId);

    boolean isAlreadyFollowed(Long followerId, Long followingId);
}
