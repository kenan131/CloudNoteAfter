package com.bin.model.mapper;

import com.bin.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByAccount(String account);

    User selectByEmail(String email);

    int insertUser(User user);

    int updatePassword(int id, String password);

    int updateActive(int id,int active);
}
