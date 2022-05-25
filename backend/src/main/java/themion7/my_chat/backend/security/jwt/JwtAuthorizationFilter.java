package themion7.my_chat.backend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.service.MemberService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberService memberService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
        super(authenticationManager);
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain chain
    ) throws IOException, ServletException {
        String header = request.getHeader(JwtUtils.HEADER);

        if (header != null && header.startsWith(JwtUtils.PREFIX)) {
            Authentication auth = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(
            String header, 
            HttpServletResponse response
    ) {
        String username = JwtUtils.getUsernameFromHeader(header);
        Member member = memberService.findByUsername(username);

        return new UsernamePasswordAuthenticationToken(
            username, 
            null, 
            member.getAuthorities()
        );
    }
    
}
