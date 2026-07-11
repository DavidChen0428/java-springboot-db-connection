package com.david.project.rest.api;

import com.david.project.entity.User;
import com.david.project.rest.api.vm.CreateUserRequest;
import com.david.project.rest.api.vm.UpdateUserRequest;
import com.david.project.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = transfer(createUserRequest);
        userService.createUser(user);
    }

    @GetMapping("/all")
    @Tag(name = "取得所有成員服務", description = "取得所有成員資料")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get")
    @Tag(name = "依據 ID 取得成員服務", description = "依據 ID 取得特定成員資料")
    public User getUserById(@RequestParam Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    @Tag(name = "更新成員服務", description = "更新特定成員資料")
    public void updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        User user = transfer(updateUserRequest);
        userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    @Tag(name = "刪除成員服務", description = "依據 ID 刪除特定成員資料")
    public void deleteUserById(@RequestParam Integer id) {
        userService.deleteUserById(id);
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
