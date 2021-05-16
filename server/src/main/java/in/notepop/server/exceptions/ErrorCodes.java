package in.notepop.server.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {
    INVALID_ADMIN_CREDS(100, 401, "Invalid Admin Creds"),
    USER_DISABLED(102, 401, "User is disabled"),
    USER_TOKEN_EXPIRED(102, 401, "Token has expired"),
    STANDARD_ERROR(102, 401, "Standard error code"),
    INVALID_USER_TOKEN(102, 401, "Invalid User Token");
    private final int statusCode;
    private final int opStatus;
    private final String message;

    public int getStatusCode() {
        return statusCode;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public String getMessage() {
        return message;
    }
}
