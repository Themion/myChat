package themion7.my_chat.backend.controller;

import com.auth0.jwt.JWT;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.security.jwt.JwtUtils;
import themion7.my_chat.backend.service.MemberService;

@RequestMapping("/token")
@RestController
@AllArgsConstructor
public class TokenController {

    private MemberService memberService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getAccessToken(@CookieValue(value = JwtUtils.REFRESH_TOKEN_HEADER) String refreshToken) {
        Member member = memberService.findByUsername(
            JWT
                .decode(refreshToken)
                .getSubject()
        );
        return JwtUtils.getAccessToken(member);
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public void removeAccessToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Cookie refreshToken = new Cookie(JwtUtils.REFRESH_TOKEN_HEADER, null);
        refreshToken.setMaxAge(0);

        response.addCookie(refreshToken);
    }

}
