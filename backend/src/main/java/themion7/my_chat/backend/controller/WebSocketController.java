package themion7.my_chat.backend.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.dto.ChatDTO;
import themion7.my_chat.backend.service.WebSocketService;

@Controller
@AllArgsConstructor
public class WebSocketController {
    
    private final WebSocketService webSocketService;

    @MessageMapping("/{roomId}")
    public void chat(@DestinationVariable final Long roomId, final ChatDTO dto) {
        webSocketService.publish(roomId, dto);
    }

    @MessageMapping("/{roomId}/disconnect")
    public void onDisconnect(@DestinationVariable final Long roomId) {
        System.out.println("debug");
        webSocketService.onDisconnect(roomId);
    }
}
