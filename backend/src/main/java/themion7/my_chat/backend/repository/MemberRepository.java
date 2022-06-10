package themion7.my_chat.backend.repository;

import themion7.my_chat.backend.domain.Member;

public interface MemberRepository {
    public Member save(Member member);
    public Member findById(Long id);
    public Member findByUsername(String username);
    public boolean isUsername(String username);
    public void deleteById(Long id);
    public void deleteByUsername(String username);
}
