package themion7.my_chat.backend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
            String username = JwtUtils.getUsernameFromHeader(header);
            System.out.println("\nauthorization");

            // 인가 과정에서는 credentials(password)를 확인할 필요 없음
            //     -> 어차피 password를 memberService에서 가져오므로 항상 맞는 credential이 된다
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    username, 
                    null,
                    memberService.findByUsername(username).getAuthorities()
                )
            );
        }

        chain.doFilter(request, response);
    }
    
}
