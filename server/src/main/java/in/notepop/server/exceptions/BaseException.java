package in.notepop.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class BaseException extends RuntimeException {

    private final ErrorCodes errorCode;
}
