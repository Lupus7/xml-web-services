package team10.authenticationapp.security;

public class JwtProperties {
    public static final String SECRET = "SomeSecret";
    public static final int EXPIRATION_TIME = 3600000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
