package themion7.my_chat.backend.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;

@Transactional
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent((member) -> {em.remove(member);});
    }
    
}