package org.example.chattrilogy.util.error;

import org.example.chattrilogy.domain.RestResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdException(IdInvalidException idException) {
        RestResponse<Object> result = new RestResponse<>();
        result.setError(idException.getMessage());
        result.setMessage("Id is invalid");
        result.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestResponse<Object>> handleDuplicateEmailException(DataIntegrityViolationException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setError(ex.getMessage());
        res.setMessage("Email already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }


    @ExceptionHandler(value = {
        BadCredentialsException.class,
        UsernameNotFoundException.class
    })
    public ResponseEntity<RestResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setError(ex.getMessage());
        res.setMessage("Username or password is incorrect");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getBody().getDetail());

        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).toList();
        res.setMessage(errors.size() > 1 ? errors : errors.get(0));
        return ResponseEntity.badRequest().body(res);
    }
}
