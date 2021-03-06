package themion7.my_chat.backend.repository;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Chatroom;

@Transactional
@SpringBootTest
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
            .isPresent();
    }

    @Test
    public void readAll() {
        Chatroom c1 = Chatroom.builder()
            .title("chatroomTest")
            .build();
        Chatroom c2 = Chatroom.builder()
            .title("test123123")
            .build();

        int size = chatroomRepository.findAll().size();

        chatroomRepository.save(c1);
        chatroomRepository.save(c2);

        Assertions
            .assertThat(chatroomRepository.findAll().size())
            .isEqualTo(size + 2);
    }

    @Test
    public void population() {
        Long id, population = 1L;
        Chatroom chatroom = Chatroom.builder()
            .title("chatroomTest")
            .population(population)
            .build();

        chatroomRepository.save(chatroom);
        id = chatroom.getId();

        chatroomRepository.increaseRoomPopulationById(id);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isEqualTo(population + 1);

        chatroomRepository.decreaseRoomPopulationById(id);
        Assertions
            .assertThat(chatroom.getPopulation())
            .isEqualTo(population);
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
