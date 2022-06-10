package themion7.my_chat.backend.repository;

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
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public Member findByUsername(String username) {
        return em
            .createQuery(
                "select m from Member m where m.username = :username",
                Member.class
            )
            .setParameter("username", username)
            .getSingleResult();
    }

    @Override
    public boolean isMember(String username) {
        try {
            this.findByUsername(username);
        } catch (Exception e) {
            return false;
        } 

        return true;
    }

	@Override
	public void deleteByUsername(String username) {
		em.remove(this.findByUsername(username));
	}
    
}
