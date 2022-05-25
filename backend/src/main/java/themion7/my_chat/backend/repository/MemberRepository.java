package themion7.my_chat.backend.repository;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import themion7.my_chat.backend.domain.Member;

public interface MemberRepository {
    public Member save(Member member);
    public Optional<Member> findById(Long id);
    public Optional<Member> findByUsername(String username);
    public void deleteById(Long id);
    public void deleteByUsername(String username);
    public UsernameNotFoundException idNotFoundException(Long id);
    public UsernameNotFoundException usernameNotFoundException(String username);
}
