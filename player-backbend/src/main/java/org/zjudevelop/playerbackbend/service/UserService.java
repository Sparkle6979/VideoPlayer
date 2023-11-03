package org.zjudevelop.playerbackbend.service;

import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.domain.User;

import java.util.List;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);

    User registry(UserRegisterDTO userRegisterDTO);

    User getUserById(Long id);

    int like(Likes likes);

    int unlike(Likes likes);

    List<Likes> getLikes(Long userId);

    int follow(Follows follows);

    int unfollow(Follows follows);

    List<Follows> getFollowings(Long userId);

    List<Follows> getFollowers(Long userId);
}
