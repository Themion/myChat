package themion7.my_chat.backend.repository;

import java.util.List;
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
        List<Member> result = em.createQuery(
                "select m from Member m where m.username = :username",
                Member.class
            ).setParameter("username", username).getResultList();

        return result.stream().findAny();
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent((member) -> em.remove(member));
    }
    
}
