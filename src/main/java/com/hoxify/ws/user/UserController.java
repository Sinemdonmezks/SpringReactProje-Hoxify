package com.hoxify.ws.user;

import com.hoxify.ws.errors.ApiError;
import com.hoxify.ws.shared.GeneratedResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private static final Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @PostMapping("/api/1.0/users")
    public GeneratedResponse createUser(@Valid @RequestBody User user){
    userService.save(user);
    return new GeneratedResponse("User created");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        error.setValidationErrors(validationErrors);
        return error;
    }
}
