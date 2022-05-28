package themion7.my_chat.backend.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.service.MemberService;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final MemberService memberService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
            .setApplicationDestinationPrefixes("/ws")
            .enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/websocket")
            .setAllowedOriginPatterns("*")
            .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Authentication user = null;
                    List<String> result = accessor.getNativeHeader("username");

                    if (result != null) {
                        String username = result.get(0);
                        Member member = memberService.findByUsername(username);
                        user = new UsernamePasswordAuthenticationToken(username, null, member.getAuthorities());
                    }
                    else {
                        final String randomId = UUID.randomUUID().toString();
                        System.out.println(randomId);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        user = new AnonymousAuthenticationToken(randomId, randomId, authorities);
                    }

                    accessor.setUser(user);
                }

                return message;
            }
        });
    }
    
}
