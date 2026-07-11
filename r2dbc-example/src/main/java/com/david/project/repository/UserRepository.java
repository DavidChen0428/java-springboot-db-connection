package com.david.project.repository;

import com.david.project.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    // 響應式查詢
    Flux<User> findByIdBetween(Integer start, Integer end);
}
