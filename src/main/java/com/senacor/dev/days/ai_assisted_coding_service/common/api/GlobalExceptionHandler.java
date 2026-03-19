package com.senacor.dev.days.ai_assisted_coding_service.common.api;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.service.RecipeNotFoundException;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.service.RecipeValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());

        String message = messages.isEmpty() ? "Validation failed" : String.join("; ", messages);

        ApiResponse<Void> body = ApiResponse.error(ErrorDetail.builder()
                .code("VALIDATION_ERROR")
                .message(message)
                .build());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RecipeValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecipeValidation(RecipeValidationException ex) {
        ApiResponse<Void> body = ApiResponse.error(ErrorDetail.builder()
                .code("VALIDATION_ERROR")
                .message(ex.getMessage())
                .build());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecipeNotFound(RecipeNotFoundException ex) {
        ApiResponse<Void> body = ApiResponse.error(ErrorDetail.builder()
                .code("RECIPE_NOT_FOUND")
                .message(ex.getMessage())
                .build());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        ApiResponse<Void> body = ApiResponse.error(ErrorDetail.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .build());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String formatFieldError(FieldError error) {
        String field = error.getField();
        String defaultMessage = error.getDefaultMessage();
        return field + ": " + defaultMessage;
    }
}
