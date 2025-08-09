package org.surja.digital_commerce_backend.config;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.surja.digital_commerce_backend.dto.OrderDetailDto;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.exception.OutOfStocksException;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> notFoundException(NotFoundException exception) {
        ResponseDTO response = new ResponseDTO();
        response.setCode("Error 1234");
        response.setMessage(exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(OutOfStocksException.class)
    public ResponseEntity<ResponseDTO> outofStocks(OutOfStocksException exception){
        ResponseDTO response = new ResponseDTO();
        response.setCode("Error 404");
        response.setMessage(exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
}
