package com.david.project.repository;

import com.david.project.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // 支援語意化命名查詢
    List<User> findByName(String name);

    // 支援原生 SQL
    @Query("SELECT * FROM USERS WHERE EMAIL LIKE CONCAT('%', :domain)")
    List<User> findByEmailDomain(@Param("domain") String domain);
}
