package themion7.my_chat.backend.repository;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Chatroom;

@SpringBootTest
@Transactional
public class ChatroomRepositoryTest {

    @Autowired ChatroomRepository chatroomRepository;

    @Test
    public void create() {
        Chatroom chatroom = Chatroom.builder()
            .title("chatroomTest")
            .build();

        chatroomRepository.save(chatroom);

        Assertions.assertThat(chatroom.getId()).isNotNull();
    }

    @Test
    public void read() {
        Chatroom chatroom = Chatroom.builder()
            .title("chatroomTest")
            .build();

        chatroomRepository.save(chatroom);
        Assertions
            .assertThat(chatroomRepository.findById(chatroom.getId()))
            .isNotEmpty();
    }

    @Test
    public void readAll() {
        Chatroom c1 = Chatroom.builder()
            .title("chatroomTest")
            .build();
        Chatroom c2 = Chatroom.builder()
            .title("test123123")
            .build();

        chatroomRepository.save(c1);
        chatroomRepository.save(c2);

        Assertions
            .assertThat(chatroomRepository.findAll().size())
            .isEqualTo(2);
    }

    @Test
    public void delete() {
        Chatroom chatroom = Chatroom.builder()
            .title("chatroomTest")
            .build();
        
        chatroomRepository.save(chatroom);
        chatroomRepository.deleteById(chatroom.getId());
        Assertions
            .assertThat(chatroomRepository.findById(chatroom.getId()))
            .isEmpty();
    }
}
