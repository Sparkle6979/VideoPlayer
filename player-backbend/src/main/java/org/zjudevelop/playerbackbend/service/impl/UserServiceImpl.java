package org.zjudevelop.playerbackbend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.zjudevelop.playerbackbend.dao.*;
import org.zjudevelop.playerbackbend.domain.po.*;
import org.zjudevelop.playerbackbend.domain.dto.*;
import org.zjudevelop.playerbackbend.service.*;
import org.zjudevelop.playerbackbend.utils.PageResult;
import org.zjudevelop.playerbackbend.pojo.exception.AccountNotFoundException;
import org.zjudevelop.playerbackbend.pojo.exception.BaseException;
import org.zjudevelop.playerbackbend.pojo.exception.PasswordErrorException;
import org.zjudevelop.playerbackbend.utils.DTOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private LikesMapper likesMapper;
    @Autowired
    private LikeService likeService;

//    @Autowired
//    private FollowsMapper followsMapper;
    @Autowired
    private FollowService followService;

//    @Autowired
//    private CreatesMapper createsMapper;
    @Autowired
    private CreateService createService;

//    @Autowired
//    private VideoMapper videoMapper;
    @Autowired
    private VideoService videoService;

//    @Autowired
//    private CategoryMapper categoryMapper;
    private CategoryService categoryService;

//    @Autowired
//    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @Override
    public User login(UserLoginDTO userLoginDTO){
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new AccountNotFoundException();
        }
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int like(Likes likes) {
        Long userId = likes.getUserId();
        Long videoId = likes.getVideoId();

        // check if already liked
        if (this.isAlreadyLiked(userId, videoId)) {
            return 0;
        }

        // TODO: update likeCount
//        VideoPO videoPO = videoMapper.selectByIdWithLock(videoId);
        VideoPO videoPO = videoService.getById(videoId);
        videoPO.setLikeCount(videoPO.getLikeCount() + 1);
        videoService.updateById(videoPO);
//        videoMapper.updateById(videoPO);

//        return likesMapper.insert(likes);
        return Boolean.TRUE == likeService.save(likes) ? 1 : 0;
    }

    @Override
    public int unlike(Likes likes) {
        Long userId = likes.getUserId();
        Long videoId = likes.getVideoId();

        // check if already liked
        if (!this.isAlreadyLiked(userId, videoId)) {
            return 0;
        }

        // update likeCount
        VideoPO videoPO = videoMapper.selectByIdWithLock(videoId);
        videoPO.setLikeCount(videoPO.getLikeCount() - 1);
        videoMapper.updateById(videoPO);

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
    public PageResult getLikes(Long userId, LikesPageQueryDTO likesPageQueryDTO) {
        Page<Likes> page = new Page<>(likesPageQueryDTO.getPage(), likesPageQueryDTO.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Page<Likes> pageResult = likesMapper.selectPage(page, queryWrapper);
        List<Long>  likesList = pageResult.getRecords().stream().map(Likes::getVideoId).collect(Collectors.toList());
        return new PageResult(pageResult.getTotal(), likesList);
    }

    @Override
    public int create(Creates creates) {
        return createsMapper.insert(creates);
    }

    @Override
    public List<Creates> getCreates(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Creates> createsList = createsMapper.selectList(wrapper);
        return createsList;
    }

    @Override
    public int follow(Follows follows) {
        if (this.isAlreadyFollowed(follows.getFollowerId(), follows.getFollowingId())) {
            return 0;
        }
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
    public Long comment(CommentPO commentPO) {
         commentMapper.insert(commentPO);
         return commentPO.getId();
    }

    @Override
    public List<Follows> getFollowings(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", userId);
        List<Follows> followsList = followsMapper.selectList(wrapper);
        return followsList;
    }

    @Override
    public PageResult getFollowings(FollowingsPageQueryDTO followingsPageQueryDTO) {
        Page<Follows> page = new Page<>(followingsPageQueryDTO.getPage(), followingsPageQueryDTO.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id", followingsPageQueryDTO.getId());
        Page<Follows> pageResult = followsMapper.selectPage(page, queryWrapper);
        List<Long> followingsList = pageResult.getRecords().stream().map(Follows::getFollowingId).collect(Collectors.toList());
        return new PageResult(pageResult.getTotal(), followingsList);
    }

    @Override
    public List<Follows> getFollowers(Long userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("following_id", userId);
        List<Follows> followsList = followsMapper.selectList(wrapper);
        return followsList;
    }

    @Override
    public PageResult getFollowers(FollowersPageQueryDTO followersPageQueryDTO) {
        Page<Follows> page = new Page<>(followersPageQueryDTO.getPage(), followersPageQueryDTO.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("following_id", followersPageQueryDTO.getId());
        Page<Follows> pageResult = followsMapper.selectPage(page, queryWrapper);
        List<Follows> records = pageResult.getRecords();
        List<Long> followersList = records.stream().map(Follows::getFollowerId).collect(Collectors.toList());
        return new PageResult(pageResult.getTotal(), followersList);
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }


    @Override
    public List<VideoInfoDTO> getOwnVideosById(Long userId) {
        QueryWrapper createsWrapper = new QueryWrapper<>();
        createsWrapper.eq("user_id", userId);
        List<Creates> createsList = createsMapper.selectList(createsWrapper);


        List<VideoInfoDTO> result = new ArrayList<>();
        for (Creates creates : createsList) {
            VideoPO videoPO = videoMapper.selectById(creates.getVideoId());
            CategoryPO categoryPO = categoryMapper.selectById(videoPO.getCategoryId());
            VideoInfoDTO videoInfoDTO = DTOUtil.makeVideoInfoDTO(videoPO,categoryPO);
            result.add(videoInfoDTO);
        }
        return result;
    }

    @Override
    public boolean isAlreadyLiked(Long userId, Long videoId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("video_id", videoId);

        Long count = likesMapper.selectCount(wrapper);
        return count > 0;
    }

    @Override
    public boolean isAlreadyFollowed(Long followerId, Long followingId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", followerId);
        wrapper.eq("following_id", followingId);

        Long count = followsMapper.selectCount(wrapper);
        return count > 0;
    }
}
