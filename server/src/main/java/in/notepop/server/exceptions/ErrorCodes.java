package in.notepop.server.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {
    INVALID_ADMIN_CREDS(401, 1, "Invalid Admin Creds"),
    USER_DISABLED(401, 2, "User is disabled"),
    USER_TOKEN_EXPIRED(401, 3, "Token has expired"),
    STANDARD_ERROR(404, 4, "Standard error code"),
    INVALID_USER_TOKEN(401, 5, "Invalid user token"),
    NO_USER_FOUND(404, 6, "No user found."),
    UNAUTHORIZED_ACCESS(401, 7, "User has accessed unauthorized id"),
    NOTES_NOT_FOUND(404, 8, "Trying to access invalid notes");

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
