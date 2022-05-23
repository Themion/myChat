package themion7.my_chat.backend.service;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Chatroom;

@Transactional
@SpringBootTest
public class ChatroomServiceTest {

    @Autowired ChatroomService service;

    @Test
    public void leave() {
        Long id;
        Chatroom chatroom = Chatroom.builder()
            .title("test")
            .build();

        service.save(chatroom);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isZero();

        id = chatroom.getId();

        service.join(id);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isEqualTo(1L);
        
        service.leave(id);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isZero();
        Assertions
            .assertThat(service.findById(id))
            .isEmpty();
    }
}
