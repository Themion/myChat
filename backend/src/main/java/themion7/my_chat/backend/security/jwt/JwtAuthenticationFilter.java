package themion7.my_chat.backend.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.dto.LoginDTO;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager manager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        Authentication auth = null;

        try {
            // Get LoginDTO from request using ObjectMapper
            LoginDTO loginDTO = new ObjectMapper().readValue(
                    request.getInputStream(), 
                    LoginDTO.class
            );

            // Create login token using LoginDTO
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword(),
                    new ArrayList<>()
                );

            // Return authenticated user by authenticating login token
            auth = manager.authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return auth;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        // Get UserDetails
        Member member = (Member) authResult.getPrincipal();

        // Create JWT Tokens
        Cookie refreshToken = new Cookie(JwtUtils.REFRESH_TOKEN_HEADER, JwtUtils.getAccessToken(member));
        // lifeSpan은 millisecond 단위이므로 second로 변환
        refreshToken.setMaxAge(JwtUtils.REFRESH_TOKEN_LIFE_SPAN / 1000);
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);
        refreshToken.setPath("/token");

        response.addCookie(refreshToken);
    }
}
