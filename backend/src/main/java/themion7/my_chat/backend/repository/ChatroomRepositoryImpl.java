package themion7.my_chat.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
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
        Optional<Chatroom> chatroom = this.findById(id);
        chatroom.ifPresent(c -> c.setPopulation(c.getPopulation() + 1));
        return chatroom;
    }

    @Override
    public Optional<Chatroom> decreaseRoomPopulationById(Long id) {
        Optional<Chatroom> chatroom = this.findById(id);
        chatroom.ifPresent(c -> c.setPopulation(c.getPopulation() - 1));
        return chatroom;
    }

    @Override
    public void deleteById(Long id) {
        em.remove(this.findById(id));
    }

    @Override
    public void deleteIfEmpty(Chatroom chatroom) {
        if (chatroom.getPopulation() == 0L) this.deleteById(chatroom.getId());
    }

}
