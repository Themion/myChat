package themion7.my_chat.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
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
        return this.chatroomService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long makeChatroom(@RequestBody @Valid ChatroomDTO dto) {
        Long id = this.chatroomService.save(dto);
        this.chatroomService.deleteIfRoomEmpty(id);
        return id;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public Chatroom getChatroom(@PathVariable Long id) {
        return this.chatroomService.findById(id);
    }
}
