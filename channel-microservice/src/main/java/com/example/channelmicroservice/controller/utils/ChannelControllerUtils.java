package com.example.channelmicroservice.controller.utils;

import com.example.bussiness.model.ApiResponse;
import com.example.channelmicroservice.domain.bussiness.exception.ChannelException;
import com.example.controller.exception.HttpRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public class ChannelControllerUtils {
    public static  <T> ResponseEntity<ApiResponse> handleRequest(ChannelHandler<T> apiHandler) {
        try {
            T response = apiHandler.handle();
            return ResponseEntity.ok( new ApiResponse.Success<>(response) );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(e.getCode(), e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }catch (HttpRequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(e.getCode(), e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(403, "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }
}
