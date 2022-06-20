package themion7.my_chat.backend.config.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.security.jwt.JwtUtils;
import themion7.my_chat.backend.service.MemberService;

@AllArgsConstructor
public class WebSocketHandler implements ChannelInterceptor {

    private final MemberService memberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        switch (accessor.getCommand()) {
            case CONNECT:
                Optional<String> auth = accessor.getNativeHeader("authentication").stream().findAny();
                accessor.setUser(auth.isPresent() ? 
                    memberService.findByUsername(JwtUtils.getUsernameFromHeader(auth.get())) : 
                    createNewAnonymousAuthentication()
                );
                break;
        
            default:
                break;
        }

        return message;
    }

    private Principal createNewAnonymousAuthentication() {
        final String randomId = UUID.randomUUID().toString();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new AnonymousAuthenticationToken(randomId, randomId, authorities);
    }
}
