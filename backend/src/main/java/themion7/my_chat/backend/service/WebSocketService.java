package themion7.my_chat.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.dto.ChatDTO;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    
    public void publish(final Long roomId, final ChatDTO dto) {
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString(), 
            dto
        );
    }

    public void onDisconnect(final Long roomId) {
        messagingTemplate.convertAndSend(
            "/topic/" + roomId.toString() + "/disconnect", 
            "disconnect"
        );
    }
}
