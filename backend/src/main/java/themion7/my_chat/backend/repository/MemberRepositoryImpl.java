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
        System.out.println("MemberChatroomRepository.save");
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        System.out.println("MemberChatroomRepository.findById");
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        System.out.println("MemberChatroomRepository.findByUsername");
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
        System.out.println("MemberChatroomRepository.deleteByUsername");
        em.remove(this.findByUsername(username));
    }
    
}
