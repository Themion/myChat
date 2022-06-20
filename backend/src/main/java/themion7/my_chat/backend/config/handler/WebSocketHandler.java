package themion7.my_chat.backend.config.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.service.MemberService;

@AllArgsConstructor
public class WebSocketHandler implements ChannelInterceptor {

    private final MemberService memberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        switch (accessor.getCommand()) {
            case CONNECT:
                Principal principal = null;
                try {
                    List<String> result = accessor.getNativeHeader("username");
                    principal = memberService.findByUsername(result.get(0));
                } catch (Exception e) {
                    principal = createNewAnonymousAuthentication();
                } finally {
                    accessor.setUser(principal);
                }
                break;
        
            default:
                break;
        }

        return message;
    }

    private Authentication createNewAnonymousAuthentication() {
        final String randomId = UUID.randomUUID().toString();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new AnonymousAuthenticationToken(randomId, randomId, authorities);
    }
}
