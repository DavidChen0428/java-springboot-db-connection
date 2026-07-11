package com.david.project.mapper;

import com.david.project.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();

    User findById(Integer id);

    int insert(User user);

    int update(User user);

    int deleteById(Integer id);
}
