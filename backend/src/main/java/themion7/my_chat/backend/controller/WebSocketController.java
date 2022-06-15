package themion7.my_chat.backend.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.dto.ChatDTO;
import themion7.my_chat.backend.service.ChatroomService;
import themion7.my_chat.backend.service.WebSocketService;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final ChatroomService chatroomService;
    private final WebSocketService webSocketService;

    @MessageMapping("/{roomId}")
    public void onPublish(
            @DestinationVariable final Long roomId,
            final Principal principal,
            final ChatDTO dto
    ) {
        System.out.println("WebSocketController.onPublish");
        webSocketService.onPublish(roomId, principal, dto);
    }
    
    @MessageMapping("/{roomId}/connect")
    public void onConnect(
        @DestinationVariable final Long roomId,
        final Principal principal
        ) {
        System.out.println("WebSocketController.onConnect");
        chatroomService.join(roomId, principal);
        webSocketService.onConnect(roomId, principal);
    }

    @MessageMapping("/{roomId}/disconnect")
    public void onDisconnect(
        @DestinationVariable final Long roomId,
        final Principal principal
        ) {
        System.out.println("WebSocketController.onDisconnect");
        chatroomService.leave(roomId);
        webSocketService.onDisconnect(roomId, principal);
    }
}
