package com.bestrongkids.authorizationserver.controller;

import com.bestrongkids.authorizationserver.dto.UserDto;
import com.bestrongkids.authorizationserver.result.user.RegisterUserResult;
import com.bestrongkids.authorizationserver.result.user.UpdateUserResult;
import com.bestrongkids.authorizationserver.services.UserService;
import com.bestrongkids.authorizationserver.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/user")
    public ApiUtils.ApiResult<RegisterUserResult> userRegister(@RequestBody UserDto user){
        return userService.registerUser(user);
    }

    @PutMapping("/user")
    public ApiUtils.ApiResult<UpdateUserResult> userUpdate(@RequestBody UserDto user) {
        return userService.updateUser(user);
    }
}
