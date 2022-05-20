package themion7.my_chat.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.repository.ChatroomRepository;

@Transactional
@AllArgsConstructor
public class ChatroomService {
    
    private final ChatroomRepository chatroomRepository;

    public Long newChatroom(Chatroom chatroom) {
        return this.chatroomRepository.save(chatroom).getId();
    }

    public List<Chatroom> chatroomList() {
        return this.chatroomRepository.findAll();
    }

}
