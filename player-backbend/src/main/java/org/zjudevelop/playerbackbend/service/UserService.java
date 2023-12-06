package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.po.*;
import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.utils.PageResult;

import java.util.List;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    User registry(UserRegisterDTO userRegisterDTO);

    int update(User user);

    User getUserById(Long id);

    int like(Likes likes);

    int unlike(Likes likes);

    List<Likes> getLikes(Long userId);

    PageResult getLikes(Long userId, LikesPageQueryDTO likesPageQueryDTO);

    int follow(Follows follows);

    int create(Creates creates);

    List<Creates> getCreates(Long userId);

    int unfollow(Follows follows);

    Long comment(CommentPO commentPO);

    List<Follows> getFollowings(Long userId);

    PageResult getFollowings(FollowingsPageQueryDTO followingsPageQueryDTO);

    List<Follows> getFollowers(Long userId);

    PageResult getFollowers(FollowersPageQueryDTO followersPageQueryDTO);

    List<VideoInfoDTO> getOwnVideosById(Long userId);

    boolean isAlreadyLiked(Long userId, Long videoId);

    boolean isAlreadyFollowed(Long followerId, Long followingId);
}
