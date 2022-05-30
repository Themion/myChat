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
        memberRepository.findByUsername(principal.getName()).ifPresentOrElse(
            member -> { dto.setSender(member.getUsername()); }, 
            () -> { dto.setSender(principal.getName().split("-")[0]); });

        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString(), 
            dto
        );
    }

    public void onConnect(final Long roomId) {
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString() + "/connect", 
            ChatDTO.builder()
                .chat("someone has connected")
                .sender("")
                .build()
        );
    }

    public void onDisconnect(final Long roomId) {
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString() + "/disconnect", 
            ChatDTO.builder()
                .chat("someone has disconnected")
                .sender("")
                .build()
        );
    }
}
