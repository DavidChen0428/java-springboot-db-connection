package com.david.project.web.api;

import com.david.project.entity.User;
import com.david.project.service.UserService;
import com.david.project.web.api.vm.CreateUserRequest;
import com.david.project.web.api.vm.UpdateUserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Tag(name = "新增成員服務", description = "新增一個新成員")
    public Mono<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = transfer(createUserRequest);
        return userService.createUser(user);
    }

    @GetMapping("/all")
    @Tag(name = "取得所有成員服務", description = "取得所有成員資料")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get")
    @Tag(name = "依據 ID 取得成員服務", description = "依據 ID 取得特定成員資料")
    public Mono<ResponseEntity<User>> getUserById(@RequestParam Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getRange")
    @Tag(name = "依據 ID 範圍取得成員服務", description = "依據 ID 範圍取得區間段成員資料")
    public Flux<User> getUserByIdRange(@RequestParam Integer start, @RequestParam Integer end) {
        return userService.getUserByIdRange(start, end);
    }

    @PutMapping("/update")
    @Tag(name = "更新成員服務", description = "更新特定成員資料")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        User user = transfer(updateUserRequest);
        return userService.updateUser(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    @Tag(name = "刪除成員服務", description = "依據 ID 刪除特定成員資料")
    public Mono<ResponseEntity<Void>> deleteUserById(@RequestParam Integer id) {
        return userService.getUserById(id)
                .flatMap(existingUser -> userService.deleteUserById(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private User transfer(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setId(updateUserRequest.getId());
        user.setName(updateUserRequest.getName());
        user.setGender(updateUserRequest.getGender());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        user.setEmail(updateUserRequest.getEmail());
        user.setAddress(updateUserRequest.getAddress());
        return user;
    }

    private User transfer(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setGender(createUserRequest.getGender());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setEmail(createUserRequest.getEmail());
        user.setAddress(createUserRequest.getAddress());
        return user;
    }
}
