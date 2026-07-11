package com.david.project.rest.api;

import com.david.project.entity.User;
import com.david.project.rest.api.vm.UserRequest;
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
    public void createUser(@RequestBody UserRequest userRequest) {
        User user = transfer(userRequest);
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
    public void updateUser(@RequestBody UserRequest userRequest) {
        User user = transfer(userRequest);
        userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    @Tag(name = "刪除成員服務", description = "依據 ID 刪除特定成員資料")
    public void deleteUserById(@RequestParam Integer id) {
        userService.deleteUserById(id);
    }

    private User transfer(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setGender(userRequest.getGender());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        return user;
    }
}
