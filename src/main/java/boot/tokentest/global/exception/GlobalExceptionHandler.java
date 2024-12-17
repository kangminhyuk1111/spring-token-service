package boot.tokentest.global.exception;

import boot.tokentest.global.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(ApplicationException e) {
        System.out.println("Exception caught in GlobalExceptionHandler: " + e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_USER);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
