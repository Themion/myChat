package themion7.my_chat.backend.service;

import java.security.Principal;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.dto.ChatDTO;
import themion7.my_chat.backend.repository.MemberRepository;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    public void onPublish(final Long roomId, final Principal principal, final ChatDTO dto) {
        dto.setSender(getSender(principal));

        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString(), 
            dto
        );
    }

    public void onConnect(final Long roomId, final Principal principal) {
        String sender = getSender(principal);
        
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString() + "/connect", 
            ChatDTO.builder()
                .chat(sender + " has connected")
                .sender("")
                .build()
        );
    }

    public void onDisconnect(final Long roomId, final Principal principal) {
        String sender = getSender(principal);
        
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString() + "/disconnect", 
            ChatDTO.builder()
                .chat(sender + " has disconnected")
                .sender("")
                .build()
        );
    }

    public void sendSender(final Long roomId, final Principal principal) {
        messagingTemplate.convertAndSendToUser(
            principal.getName(), 
            "/queue/" + roomId.toString(), 
            getSender(principal)
        );
    }

    private String getSender(final Principal principal) {
        return this.memberRepository.findByUsername(principal.getName())
            .map(member -> member.getUsername())
            .orElse(principal.getName().split("-")[0]);
    }
}
