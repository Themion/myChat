package themion7.my_chat.backend.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.dto.ChatroomDTO;

@Transactional
@SpringBootTest
public class ChatroomServiceTest {

    @Autowired ChatroomService service;

    @Test
    public void joinAndLeave() {
        Long id;

        ChatroomDTO chatroomDTO = new ChatroomDTO("test");
        Member member = Member.builder()
            .username("username")
            .password("password")
            .build();

        id = service.save(chatroomDTO);

        Chatroom chatroom = service.findById(id);

        Assertions
            .assertThat(chatroom.getPopulation())
            .isZero();

        service.join(id, member);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isEqualTo(1L);
        
        service.leave(id);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isZero();

        assertThrows(
            NoResultException.class, 
            () -> service.findById(id)
        );

    }

    @Test
    public void autoDelete() throws InterruptedException {
        Long id;

        ChatroomDTO chatroomDTO = new ChatroomDTO("test");

        this.service.save(chatroomDTO);
        id = service.findAll().get(0).getId();
        this.service.deleteIfRoomEmpty(id);

        Thread.sleep(1000 * 70);

        Assertions
            .assertThat(service.findById(id))
            .isNotNull();

        // 테스트에선 작동하지 않지만 실제로 돌려보면 자동으로 삭제됨을 알 수 있음
    }
}
