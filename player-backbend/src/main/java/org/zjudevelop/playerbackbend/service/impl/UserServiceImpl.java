package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjudevelop.playerbackbend.dao.FollowsMapper;
import org.zjudevelop.playerbackbend.dao.LikesMapper;
import org.zjudevelop.playerbackbend.dao.UserMapper;
import org.zjudevelop.playerbackbend.domain.Follows;
import org.zjudevelop.playerbackbend.domain.Likes;
import org.zjudevelop.playerbackbend.dto.UserLoginDTO;
import org.zjudevelop.playerbackbend.dto.UserRegisterDTO;
import org.zjudevelop.playerbackbend.domain.User;
import org.zjudevelop.playerbackbend.pojo.exception.AccountNotFoundException;
import org.zjudevelop.playerbackbend.pojo.exception.BaseException;
import org.zjudevelop.playerbackbend.pojo.exception.PasswordErrorException;
import org.zjudevelop.playerbackbend.service.UserService;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private FollowsMapper followsMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO){
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        log.info("user: " + user);
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException();
        }
        return user;
    }

    @Override
    public User registry(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        User user = new User().builder()
                .username(username)
                .password(password)
                .build();
        int registerResult = userMapper.insert(user);
        log.info("registerResult: " + registerResult);
        // TODO: fix the bug that id still auto-increment when insertion failure
        if (registerResult != 1) {
            // TODO: add specific exception class
            throw new BaseException("注册失败");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        return user;
    }

    @Override
    public int like(Likes likes) {
        // TODO: 当用户对同一个视频进行多次点暂时，不重复添加
        return likesMapper.insert(likes);
    }

    @Override
    public int unlike(Likes likes) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", likes.getUserId());
        wrapper.eq("video_id", likes.getVideoId());
        return likesMapper.delete(wrapper);
    }

    @Override
    public List<Likes> getLikes(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Likes> likesList = likesMapper.selectList(wrapper);
        return likesList;
    }

    @Override
    public int follow(Follows follows) {
        return followsMapper.insert(follows);
    }

    @Override
    public int unfollow(Follows follows) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", follows.getFollowerId());
        wrapper.eq("following_id", follows.getFollowingId());
        return followsMapper.delete(wrapper);
    }

    @Override
    public List<Follows> getFollowings(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", userId);
        List<Follows> followsList = followsMapper.selectList(wrapper);
        return followsList;
    }

    @Override
    public List<Follows> getFollowers(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("following_id", userId);
        List<Follows> followsList = followsMapper.selectList(wrapper);
        return followsList;
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }
}
