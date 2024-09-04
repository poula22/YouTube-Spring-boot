package com.example.usermicroservice.controller;

import com.example.bussiness.model.ApiResponse;
import com.example.usermicroservice.domain.bussiness.model.login.LoginRequestDto;
import com.example.usermicroservice.domain.bussiness.model.signUp.SignUpRequestDto;
import com.example.usermicroservice.controller.utils.AuthControllerUtils;
import com.example.usermicroservice.domain.bussiness.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    private final AuthControllerUtils controllerUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody @Valid LoginRequestDto loginRequest,
            BindingResult bindingResult
    ) {
        return controllerUtils.handleRequest(bindingResult, () -> userService.login(loginRequest));
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(
            @RequestBody @Valid SignUpRequestDto user,
            BindingResult bindingResult
    ) {
       return controllerUtils.handleRequest(bindingResult,()-> userService.signUp(user));
    }
}