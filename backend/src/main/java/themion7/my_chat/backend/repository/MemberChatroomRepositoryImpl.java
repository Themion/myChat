package themion7.my_chat.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.MemberChatroom;

@Transactional
@RequiredArgsConstructor
public class MemberChatroomRepositoryImpl implements MemberChatroomRepository {

    @NonNull
    private final EntityManager em;

    private final String ql = "select mc from MemberChatroom mc ";
    private final String mql = "mc.member.id = :memberId";
    private final String cql = "mc.chatroom.id = :chatroomId";

    @Override
    public MemberChatroom save(MemberChatroom memberChatroom) {
        em.persist(memberChatroom);
        return memberChatroom;
    }

    @Override
    public List<MemberChatroom> findByMemberId(Long memberId) {
        String qlString = ql + "where " + mql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("memberId", memberId)
            .getResultList()
            ;
    }

    @Override
    public List<MemberChatroom> findByChatroomId(Long chatroomId) {
        String qlString = ql + "where " + cql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("chatroomId", chatroomId)
            .getResultList()
            ;
        }

    @Override
    public MemberChatroom findByMemberIdAndChatroomId(Long memberId, Long chatroomId) {
        String qlString = ql + "where " + mql + " and " + cql;
        return em
            .createQuery(qlString, MemberChatroom.class)
            .setParameter("memberId", memberId)
            .setParameter("chatroomId", chatroomId)
            .getSingleResult()
            ;
    }

    @Override
    public void delete(Long memberId, Long chatroomId) {
        em.remove(
            this.findByMemberIdAndChatroomId(memberId, chatroomId)
        );
    }

}
