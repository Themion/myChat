package themion7.my_chat.backend.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Member;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void create() {
        Member member = Member.builder()
            .username("test123")
            .password("password123")
            .build();
        
        memberRepository.save(member);
        Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    public void read() {
        Member member = Member.builder()
            .username("test123")
            .password("password123")
            .build();
        
        memberRepository.save(member);
        Assertions
            .assertThat(memberRepository.findById(member.getId()))
            .isNotNull();
        
        assertThrows(
            Exception.class, 
            () -> { memberRepository.findByUsername(member.getUsername() + 'a'); }
        );
    }

    @Test
    public void delete() {
        Member member = Member.builder()
            .username("test123")
            .password("password123")
            .build();
        
        memberRepository.save(member);
        memberRepository.deleteByUsername(member.getUsername());
        
        assertThrows(
            Exception.class, 
            () -> { memberRepository.findByUsername(member.getUsername()); }
        );

        Assertions
            .assertThat(memberRepository.findById(member.getId()))
            .isNull();
    }
}
