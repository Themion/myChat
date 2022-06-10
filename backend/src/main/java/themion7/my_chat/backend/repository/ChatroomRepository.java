package themion7.my_chat.backend.repository;

import java.util.List;

import themion7.my_chat.backend.domain.Chatroom;

public interface ChatroomRepository {
    public Chatroom save(Chatroom chatroom);
    public Chatroom findById(Long id);
    public List<Chatroom> findAll();
    public Chatroom increaseRoomPopulationById(Long id);
    public Chatroom decreaseRoomPopulationById(Long id);
    public void deleteById(Long id);
}
