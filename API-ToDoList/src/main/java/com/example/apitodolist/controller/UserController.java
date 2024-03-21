package com.example.apitodolist.controller;

import com.example.apitodolist.dto.BaseResponceDto;
import com.example.apitodolist.dto.UserLoginDto;
import com.example.apitodolist.dto.UserRegisterDto;
import com.example.apitodolist.model.User;
import com.example.apitodolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/todoapi/v1")
public class UserController {

    @Autowired
    UserService userService;

    // Endpoint POST pour l'enregistrement d'un nouvel utilisateur.
    @PostMapping("/register")
    public BaseResponceDto registerUser(@RequestBody UserRegisterDto userRegisterDto){
        if(userService.createUser(userRegisterDto)){
            return new BaseResponceDto("success");
        }else {
            return new BaseResponceDto("failed");
        }
    }

    // Endpoint POST pour la connexion d'un utilisateur.
    @PostMapping("/login")
    public BaseResponceDto loginUser(@RequestBody UserLoginDto loginDetails){
        if(userService.checkUserNameExists(loginDetails.getEmail())){
            if(userService.verifyUser(loginDetails.getEmail(), loginDetails.getPassword())){
                Map<String, Object> data = new HashMap<>();
                data.put("token", userService.generateToken(loginDetails.getEmail(), loginDetails.getPassword()));
                return new BaseResponceDto("success", data);
            }else {
                return new BaseResponceDto("wrong password");
            }
        }else {
            return new BaseResponceDto("user not exist");
        }
    }
}

