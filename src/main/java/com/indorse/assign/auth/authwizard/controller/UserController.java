package com.indorse.assign.auth.authwizard.controller;

import com.indorse.assign.auth.authwizard.dto.UserDto;
import com.indorse.assign.auth.authwizard.dto.UserLoginDto;
import com.indorse.assign.auth.authwizard.model.Users;
import com.indorse.assign.auth.authwizard.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/signup")
    public String doSignUp(@RequestBody UserDto userDto, HttpServletRequest request) {
        String response = "";
        Users user = userService.findByEmail(userDto.getEmail());

        if (!ObjectUtils.isEmpty(user)) {
            response = "User already exists with provided email (={ "+userDto.getEmail()+" })";
        } else {
            userService.saveAndSendConfirmationMail(userDto);
            response = "User signedUp successfully. Please verify your email.";
        }
        return response;
    }

    @GetMapping(value = "/confirm")
    public String confirmRegistration(HttpServletRequest request,
                                      @RequestParam String token, @RequestParam String password) {
        String response = null;
        Users users = userService.findByConfirmationToken(token);
        if (ObjectUtils.isEmpty(users)){
            response = "Cannot verify your email as token doesn't exists";
        }
        userService.saveUserPassword(token, password);
        response = "Email verified";
        return response;
    }

    @PostMapping(value = "/login")
    public String doSignUp(UserLoginDto userLoginDto, HttpServletRequest request) {
        return userService.getUserStatus(userLoginDto);
    }
}
