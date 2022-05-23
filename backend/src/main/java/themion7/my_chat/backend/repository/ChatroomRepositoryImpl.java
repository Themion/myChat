package themion7.my_chat.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;

@Transactional
@AllArgsConstructor
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
    public void increaseRoomPopulationById(Long id) {
        Chatroom chatroom = this.findById(id).orElseThrow();
        chatroom.setPopulation(chatroom.getPopulation() + 1);
    }

    @Override
    public void decreaseRoomPopulationById(Long id) {
        Chatroom chatroom = this.findById(id).orElseThrow();
        chatroom.setPopulation(chatroom.getPopulation() - 1);
        if (chatroom.getPopulation() <= 0) this.deleteById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent((chatroom) -> { em.remove(chatroom); });
    }
    
}
