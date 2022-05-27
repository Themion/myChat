package themion7.my_chat.backend.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import themion7.my_chat.backend.domain.Member;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtils {
    public static int ACCESS_TOKEN_LIFE_SPAN;
    public static int REFRESH_TOKEN_LIFE_SPAN;
    public static final String HEADER = "Authorization";
    public static final String ACCESS_TOKEN_HEADER = "access-token";
    public static final String REFRESH_TOKEN_HEADER = "refresh-token";
    public static String PREFIX = "Bearer ";
    private static String SECRET;

    public static Algorithm HMAC512() {
        return com.auth0.jwt.algorithms.Algorithm.HMAC512(JwtUtils.SECRET);
    }

    @Value("${jwt.token-life-span.access}")
    public void setAccessTokenLifeSpan(int accessTokenLifeSpan) {
        JwtUtils.ACCESS_TOKEN_LIFE_SPAN = accessTokenLifeSpan;
    }

    @Value("${jwt.token-life-span.refresh}")
    public void setRefreshTokenLifeSpan(int refreshTokenLifeSpan) {
        JwtUtils.REFRESH_TOKEN_LIFE_SPAN = refreshTokenLifeSpan;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtils.SECRET = secret;
    }

    public static String getUsernameFromHeader(String header) {
        return JWT.require(JwtUtils.HMAC512())
            .build()
            .verify(header.replace(JwtUtils.PREFIX, ""))
            .getSubject();
    }

    public static Builder getJwtBuilder(Member member) {
        return JWT.create()
            .withSubject(member.getUsername())
            .withIssuedAt(new Date(System.currentTimeMillis()))
            ;
    }

    public static String getAccessToken(Member member) {
        return JwtUtils.getJwtBuilder(member)
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtils.ACCESS_TOKEN_LIFE_SPAN))
            .sign(JwtUtils.HMAC512())
            ;
    }

    public static String getRefreshToken(Member member) {
        return JwtUtils.getJwtBuilder(member)
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtils.REFRESH_TOKEN_LIFE_SPAN))
            .sign(JwtUtils.HMAC512())
            ;
    }
}
