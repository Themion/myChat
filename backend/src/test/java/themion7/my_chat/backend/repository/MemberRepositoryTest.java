package themion7.my_chat.backend.repository;

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
            .isNotEmpty();
    }

    @Test
    public void delete() {
        Member member = Member.builder()
            .username("test123")
            .password("password123")
            .build();
        
        memberRepository.save(member);
        memberRepository.deleteById(member.getId());
        Assertions
            .assertThat(memberRepository.findById(member.getId()))
            .isEmpty();

    }
}
