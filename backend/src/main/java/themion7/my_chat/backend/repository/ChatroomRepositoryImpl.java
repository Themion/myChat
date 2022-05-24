package themion7.my_chat.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.var;
import themion7.my_chat.backend.domain.Chatroom;

@AllArgsConstructor
@Transactional
public class ChatroomRepositoryImpl implements ChatroomRepository {

    private final EntityManager em;

    @Override
    public Chatroom save(Chatroom chatroom) {
        em.persist(chatroom);
        return chatroom;
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        return Optional.ofNullable(em.find(Chatroom.class, id));
    }

    @Override
    public List<Chatroom> findAll() {
        return em.createQuery("select c from Chatroom as c", Chatroom.class).getResultList();
    }

    @Override
    public Optional<Chatroom> increaseRoomPopulationById(Long id) {
        var chatroom = this.findById(id);
        chatroom.ifPresent(room -> {
            room.setPopulation(room.getPopulation() + 1);
        });
        return chatroom;
    }

    @Override
    public Optional<Chatroom> decreaseRoomPopulationById(Long id) {
        var chatroom = this.findById(id);
        chatroom.ifPresent(room -> {
            room.setPopulation(room.getPopulation() - 1);
        });
        return chatroom;
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent((chatroom) -> { em.remove(chatroom); });
    }

}
