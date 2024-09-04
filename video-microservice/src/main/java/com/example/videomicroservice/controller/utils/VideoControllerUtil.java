package com.example.videomicroservice.controller.utils;

import com.example.bussiness.model.ApiResponse;
import com.example.controller.exception.HttpRequestException;
import com.example.videomicroservice.domain.bussiness.exception.VideoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VideoControllerUtil {
    public static <T> ResponseEntity<ApiResponse> handleRequest(VideoControllerHandler<T> apiHandler) {
        try {
            T response = apiHandler.handle();
            return ResponseEntity.ok(new ApiResponse.Success<>(response));
        } catch (VideoException e) {
            HttpStatus httpStatus = HttpStatus.resolve(e.getCode());
            if (httpStatus == null) httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(
                    new ApiResponse.Error(e.getCode(), e.getMessage()),
                    httpStatus
            );
        } catch (HttpRequestException e){
            HttpStatus httpStatus = HttpStatus.resolve(e.getCode());
            if (httpStatus == null) httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(
                    new ApiResponse.Error(e.getCode(), e.getMessage()),
                    httpStatus
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error(403, e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        }
    }
}