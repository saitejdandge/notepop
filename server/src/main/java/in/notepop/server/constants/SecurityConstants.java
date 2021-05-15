package in.notepop.server.constants;

public class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_URL = "/user/login";
    public static final String ADMIN_USER_NAME = "admin";
    public static final String ADMIN_PASSWORD = "1425d006";
}
