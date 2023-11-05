package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.Creates;
import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.dto.*;
import org.zjudevelop.playerbackbend.domain.User;
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

    PageResult getFollowings(FollowingsPageQueryDTO followingsPageQueryDTO);

    PageResult getFollowers(FollowersPageQueryDTO followersPageQueryDTO);

    List<VideoInfoDTO> getOwnVideosById(Long userId);

    boolean isAlreadyLiked(Long userId, Long videoId);

    boolean isAlreadyFollowed(Long followerId, Long followingId);
}
