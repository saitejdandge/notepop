package in.notepop.server.filters.exception_filter;

import in.notepop.server.Status;
import in.notepop.server.exceptions.BaseException;
import in.notepop.server.exceptions.ErrorCodes;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException runtimeException) {
        ErrorResponse errorResponse = getErrorResponse(runtimeException);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(RuntimeException e) {
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return new ErrorResponse(baseException.getErrorCode());
        } else
            return new ErrorResponse(ErrorCodes.STANDARD_ERROR);
    }

    @Data
    private static class ErrorResponse {
        private final String message;
        private final int opStatus, statusCode;
        private final ZonedDateTime timestamp;
        private final Status status;
        private final int result;

        public ErrorResponse(ErrorCodes errorCode) {
            this.timestamp = ZonedDateTime.now(ZoneId.of("Z"));
            this.message = errorCode.getMessage();
            this.statusCode = errorCode.getStatusCode();
            this.opStatus = errorCode.getOpStatus();
            this.status = Status.FAILED;
            this.result = 0;
        }

    }

}
