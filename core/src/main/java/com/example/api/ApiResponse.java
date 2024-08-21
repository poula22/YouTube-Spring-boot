package com.example.api;

public sealed interface ApiResponse {
    record Success<T>(T data)  implements ApiResponse{}
    record Error(String code, String message) implements ApiResponse {}
}
