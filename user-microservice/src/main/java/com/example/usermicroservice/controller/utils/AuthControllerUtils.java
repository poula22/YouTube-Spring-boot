package com.example.usermicroservice.controller.utils;

import com.example.bussiness.model.ApiResponse;
import com.example.controller.ControllerRequestHandler;
import com.example.controller.exception.HttpRequestException;
import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.bussiness.exception.CustomAuthException;
import com.example.usermicroservice.bussiness.model.AuthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class AuthControllerUtils {
    public ResponseEntity<ApiResponse> handleRequest(
            BindingResult bindingResult,
            AuthHandler<AuthResponseDto> apiHandler
    ) {
        try {
            ControllerRequestHandler.handleRequest(bindingResult);
            AuthResponseDto responseDto = apiHandler.handle();
            return ResponseEntity.ok(new ApiResponse.Success<>(responseDto));

        } catch (CustomEntityException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(404, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );

        } catch (HttpRequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(e.getCode(), e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (CustomAuthException authException) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(401, authException.getMessage()),
                    HttpStatus.UNAUTHORIZED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(400, e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
