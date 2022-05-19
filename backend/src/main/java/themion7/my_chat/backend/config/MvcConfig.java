package themion7.my_chat.backend.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.repository.MemberRepository;
import themion7.my_chat.backend.repository.MemberRepositoryImpl;

@Configuration
@AllArgsConstructor
public class MvcConfig {
    
    private final EntityManager em;

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(em);
    }

}
