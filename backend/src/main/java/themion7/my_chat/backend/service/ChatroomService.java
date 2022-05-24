package themion7.my_chat.backend.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.repository.ChatroomRepository;

@Transactional
@AllArgsConstructor
public class ChatroomService {
    
    private final ChatroomRepository chatroomRepository;

    @Async
    public void deleteIfRoomEmpty(Long id) {

        try {
            Thread.sleep(1000 * 60 * 10);
            this.chatroomRepository.findById(id).ifPresentOrElse(
                room -> {
                    if (room.getPopulation() <= 0L)
                        this.chatroomRepository.deleteById(id);
                },
                () -> new NoSuchElementException("Chatroom not found with id: " + id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long save(Chatroom chatroom) {
        return this.chatroomRepository.save(chatroom).getId();
    }

    public Chatroom findById(Long id) {
        return this.chatroomRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Chatroom not found with id: " + id)
        );
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
