package themion7.my_chat.backend.repository;

import java.util.List;
import java.util.Optional;

import themion7.my_chat.backend.domain.Chatroom;

public interface ChatroomRepository {
    public Chatroom save(Chatroom chatroom);
    public Optional<Chatroom> findById(Long id);
    public List<Chatroom> findAll();
    public void deleteById(Long id);
}