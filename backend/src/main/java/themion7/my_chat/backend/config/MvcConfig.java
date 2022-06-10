package themion7.my_chat.backend.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.repository.ChatroomRepository;
import themion7.my_chat.backend.repository.ChatroomRepositoryImpl;
import themion7.my_chat.backend.repository.MemberChatroomRepository;
import themion7.my_chat.backend.repository.MemberChatroomRepositoryImpl;
import themion7.my_chat.backend.repository.MemberRepository;
import themion7.my_chat.backend.repository.MemberRepositoryImpl;
import themion7.my_chat.backend.service.ChatroomService;
import themion7.my_chat.backend.service.MemberService;

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
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(em);
    }

    @Bean
    public MemberChatroomRepository memberChatroomRepository() {
        return new MemberChatroomRepositoryImpl(em);
    }

    @Bean
    public ChatroomService chatroomService() {
        return new ChatroomService(
            this.memberRepository(),
            this.chatroomRepository(),
            this.memberChatroomRepository()
        );
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(
            this.memberRepository(), 
            this.memberChatroomRepository()
        );
    }

}
