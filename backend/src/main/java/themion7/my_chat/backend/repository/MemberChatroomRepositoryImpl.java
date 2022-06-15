package themion7.my_chat.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.MemberChatroom;

@Transactional
@RequiredArgsConstructor
public class MemberChatroomRepositoryImpl implements MemberChatroomRepository {

    @NonNull
    private final EntityManager em;

    private final String ql = "select mc from MemberChatroom mc ";
    private final String mql = " mc.member.id = :memberId ";
    private final String cql = " mc.chatroom.id = :chatroomId ";

    @Override
    public MemberChatroom save(MemberChatroom memberChatroom) {
        System.out.println("MemberChatroomRepository.save");
        em.persist(memberChatroom);
        return memberChatroom;
    }

    @Override
    public List<MemberChatroom> findByMemberId(Long memberId) {
        System.out.println("MemberChatroomRepository.findByMemberId");
        String qlString = ql + "where" + mql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("memberId", memberId)
            .getResultList()
            ;
    }

    @Override
    public List<MemberChatroom> findByChatroomId(Long chatroomId) {
        System.out.println("MemberChatroomRepository.findByChatroomId");
        String qlString = ql + "where" + cql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("chatroomId", chatroomId)
            .getResultList()
            ;
        }

    @Override
    public MemberChatroom findByMemberIdAndChatroomId(Long memberId, Long chatroomId) {
        System.out.println("MemberChatroomRepository.findByMemberIdAndChatroomId");
        String qlString = ql + "where" + mql + "and" + cql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("memberId", memberId)
            .setParameter("chatroomId", chatroomId)
            .getSingleResult()
            ;
    }

    // POJO하지 않지만 영속성 컨텍스트? 위반으로 인해 id로 검색해 삭제
    @Override
    public void delete(Long memberId, Long chatroomId) {
        System.out.println("MemberChatroomRepository.delete");
        em.remove(this.findByMemberIdAndChatroomId(memberId, chatroomId));
    }

}
