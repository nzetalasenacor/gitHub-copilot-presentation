package com.senacor.dev.days.ai_assisted_coding_service.common.api;

public record ApiResponse<T>(T data, ErrorDetail error) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    public static ApiResponse<Void> error(ErrorDetail error) {
        return new ApiResponse<>(null, error);
    }
}
