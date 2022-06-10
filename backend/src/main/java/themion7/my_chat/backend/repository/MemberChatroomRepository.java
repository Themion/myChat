package themion7.my_chat.backend.repository;

import java.util.List;

import themion7.my_chat.backend.domain.MemberChatroom;

public interface MemberChatroomRepository {
    public MemberChatroom save(MemberChatroom memberChatroom);
    public List<MemberChatroom> findByMemberId(Long memberId); 
    public List<MemberChatroom> findByChatroomId(Long chatroomId);
    public MemberChatroom findByMemberIdAndChatroomId(Long memberId, Long chatroomId);
    public void delete(Long memberId, Long chatroomId);
}
