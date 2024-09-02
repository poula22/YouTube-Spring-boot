package com.example.usermicroservice.controller;

import com.example.bussiness.model.ApiResponse;
import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.bussiness.exception.CustomAuthException;
import com.example.usermicroservice.bussiness.model.login.LoginRequestDto;
import com.example.usermicroservice.bussiness.model.AuthResponseDto;
import com.example.usermicroservice.bussiness.model.signUp.SignUpRequestDto;
import com.example.usermicroservice.domain.bussiness.AuthService;
import com.example.usermicroservice.domain.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/auth")
@RestController
public class AuthController {
    private final AuthService userService;

    public AuthController(AuthService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody @Valid LoginRequestDto loginRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getAllErrors().get(0);
            return new ResponseEntity<>(
                    new ApiResponse.Error("400", error.getDefaultMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            AuthResponseDto responseDto = userService.login(loginRequest);

            return new ResponseEntity<>(
                    new ApiResponse.Success<>(responseDto),
                    HttpStatus.OK
            );

        } catch (CustomEntityException | CustomAuthException exception) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", exception.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("400", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(
            @RequestBody @Valid SignUpRequestDto user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getAllErrors().get(0);
            return new ResponseEntity<>(
                    new ApiResponse.Error("400", error.getDefaultMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            AuthResponseDto responseDto = userService.signUp(user);
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(responseDto),
                    HttpStatus.OK
            );
        } catch (CustomEntityException | CustomAuthException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("400", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PostMapping("/checkToken")
//    public ResponseEntity<?> checkToken(@RequestBody String token) {
//        String user = jwtUtil.extractUsername(token);
//
//    }
}
