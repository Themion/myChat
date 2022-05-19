package themion7.my_chat.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.dto.ChatroomDTO;
import themion7.my_chat.backend.service.ChatroomService;

@RestController
@RequestMapping("room")
@AllArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Chatroom> chatroomList() {
        return this.chatroomService.chatroomList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void makeChatroom(@RequestBody ChatroomDTO dto) {
        this.chatroomService.newChatroom(
            Chatroom.builder()
                .title(dto.getTitle())  
                .build()
        );
    }
}
