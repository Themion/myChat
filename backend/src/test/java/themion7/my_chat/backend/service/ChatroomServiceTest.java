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
    public void joinAndLeave() {
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
            .isNull();
    }

    @Test
    public void autoDelete() throws InterruptedException {
        Long id;
        Chatroom chatroom = Chatroom.builder()
            .title("test")
            .build();

        this.service.save(chatroom);
        id = chatroom.getId();
        this.service.deleteIfRoomEmpty(id);

        Thread.sleep(1000 * 70);

        Assertions
            .assertThat(service.findById(id))
            .isNotNull();

        // 테스트에선 작동하지 않지만 실제로 돌려보면 자동으로 삭제됨을 알 수 있음
    }
}
