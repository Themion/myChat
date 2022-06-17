package themion7.my_chat.backend.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;

@AllArgsConstructor
@Transactional
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
    public Optional<Member> findByUsername(String username) {
        return em
            .createQuery(
                "select m from Member m where m.username = :username",
                Member.class
            )
            .setParameter("username", username)
            .getResultList().stream().findFirst();
    }

    @Override
    public void deleteByUsername(String username) {
        em.remove(this.findByUsername(username));
    }
    
}
