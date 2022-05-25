package themion7.my_chat.backend.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
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
        Member user = (Member) authResult.getPrincipal();

        // Create JWT Tokens
        Map<String, String> tokens = new HashMap<>();
        tokens.put(JwtUtils.ACCESS_TOKEN_HEADER, JwtUtils.getAccessToken(request, user));
        tokens.put(JwtUtils.REFRESH_TOKEN_HEADER, JwtUtils.getRefreshToken(request, user));

        // Add tokens in response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
