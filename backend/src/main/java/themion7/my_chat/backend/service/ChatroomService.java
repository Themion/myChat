package themion7.my_chat.backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.repository.ChatroomRepository;

@Transactional
@AllArgsConstructor
public class ChatroomService {
    
    private final ChatroomRepository chatroomRepository;

    public Long save(Chatroom chatroom) {
        return this.chatroomRepository.save(chatroom).getId();
    }

    public Optional<Chatroom> findById(Long id) {
        return this.chatroomRepository.findById(id);
    }

    public List<Chatroom> findAll() {
        return this.chatroomRepository.findAll();
    }

    public void join(final Long id) {
        this.chatroomRepository.increaseRoomPopulationById(id);
    }

    public void leave(final Long id) {
        var chatroom = this.chatroomRepository.decreaseRoomPopulationById(id);
        chatroom.ifPresent(room -> {
            if (room.getPopulation() <= 0)
                this.chatroomRepository.deleteById(id);
        });
    }

}
