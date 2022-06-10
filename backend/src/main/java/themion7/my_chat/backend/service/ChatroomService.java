package themion7.my_chat.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.util.HtmlUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.dto.ChatroomDTO;
import themion7.my_chat.backend.repository.ChatroomRepository;

@RequiredArgsConstructor
public class ChatroomService {
    
    @NonNull
    private final ChatroomRepository chatroomRepository;

    @Value("${chat.lifespan}")
    private Long lifespan;

    @Async
    public void deleteIfRoomEmpty(Long id) {

        try {
            Thread.sleep(lifespan);
            Chatroom chatroom = this.chatroomRepository.findById(id);
            if (chatroom != null && chatroom.getPopulation() == 0L) 
                this.chatroomRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long save(ChatroomDTO chatroomDTO) {
        return this.chatroomRepository.save(
            Chatroom.builder()
                .title(HtmlUtils.htmlEscape(chatroomDTO.getTitle()))  
                .population(0L)
                .build()
        ).getId();
    }

    public Chatroom findById(Long id) {
        return this.chatroomRepository.findById(id);
    }

    public List<Chatroom> findAll() {
        return this.chatroomRepository.findAll();
    }

    public void join(final Long id) {
        this.chatroomRepository.increaseRoomPopulationById(id);
    }

    public void leave(final Long id) {
        Chatroom chatroom = this.chatroomRepository.decreaseRoomPopulationById(id);
        if (chatroom != null && chatroom.getPopulation() == 0L)
            this.chatroomRepository.deleteById(id);
    }
}
