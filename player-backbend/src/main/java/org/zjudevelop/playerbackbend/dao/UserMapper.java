package org.zjudevelop.playerbackbend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zjudevelop.playerbackbend.domain.po.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select(" select * from user where username = #{username}")
    User selectByUsername(@Param("username") String username);
}
