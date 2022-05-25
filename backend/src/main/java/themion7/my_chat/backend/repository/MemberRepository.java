package themion7.my_chat.backend.repository;

import java.util.Optional;

import themion7.my_chat.backend.domain.Member;

public interface MemberRepository {
    public Member save(Member member);
    public Optional<Member> findById(Long id);
    public Optional<Member> findByUsername(String username);
    public void deleteById(Long id);
}
