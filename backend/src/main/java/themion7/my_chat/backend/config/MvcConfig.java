package themion7.my_chat.backend.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.repository.ChatroomRepository;
import themion7.my_chat.backend.repository.ChatroomRepositoryImpl;
import themion7.my_chat.backend.repository.MemberRepository;
import themion7.my_chat.backend.repository.MemberRepositoryImpl;
import themion7.my_chat.backend.service.ChatroomService;

@Configuration
@AllArgsConstructor
public class MvcConfig {
    
    @PersistenceContext
    private final EntityManager em;

    @Bean
    public ChatroomRepository chatroomRepository() {
        return new ChatroomRepositoryImpl(em);
    }

    @Bean
    public ChatroomService chatroomService() {
        return new ChatroomService(this.chatroomRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(em);
    }

}
