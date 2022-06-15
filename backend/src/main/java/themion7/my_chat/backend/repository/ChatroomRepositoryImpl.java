package themion7.my_chat.backend.repository;

import java.util.List;

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
        System.out.println("ChatroomRepository.save");
        em.persist(chatroom);
        return chatroom;
    }

    @Override
    public Chatroom findById(Long id) {
        System.out.println("ChatroomRepository.findById");
        return em.find(Chatroom.class, id);
    }

    @Override
    public List<Chatroom> findAll() {
        System.out.println("ChatroomRepository.findAll");
        return em.createQuery("select c from Chatroom as c", Chatroom.class).getResultList();
    }

    @Override
    public Chatroom increaseRoomPopulationById(Long id) {
        System.out.println("ChatroomRepository.increaseRoomPopulationById");
        Chatroom chatroom = this.findById(id);
        if (chatroom != null) chatroom.setPopulation(chatroom.getPopulation() + 1);
        return chatroom;
    }

    @Override
    public Chatroom decreaseRoomPopulationById(Long id) {
        System.out.println("ChatroomRepository.decreaseRoomPopulationById");
        Chatroom chatroom = this.findById(id);
        if (chatroom != null) chatroom.setPopulation(chatroom.getPopulation() - 1);
        return chatroom;
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("ChatroomRepository.deleteById");
        em.remove(this.findById(id));
    }

}
