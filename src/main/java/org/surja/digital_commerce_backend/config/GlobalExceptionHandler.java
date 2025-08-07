package org.surja.digital_commerce_backend.config;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> notFoundException(NotFoundException exception) {
        ResponseDTO response = new ResponseDTO();
        response.setCode("Error 1234");
        response.setMessage(exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
}
