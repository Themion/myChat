package themion7.my_chat.backend.service;

import java.util.List;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.domain.MemberChatroom;
import themion7.my_chat.backend.repository.ChatroomRepository;
import themion7.my_chat.backend.repository.MemberChatroomRepository;
import themion7.my_chat.backend.repository.MemberRepository;

@AllArgsConstructor
public class MemberChatroomService {

    private final MemberRepository memberRepository;
    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomRepository memberChatroomRepository;

    private void save(Member member, Chatroom chatroom) {
        this.memberChatroomRepository.save(
            MemberChatroom
                .builder()
                .member(member)
                .chatroom(chatroom)
                .build()
        );
    }

    public void save(String username, Long chatroomId) {
        if (memberRepository.isMember(username) && chatroomRepository.isChatroom(chatroomId))
            this.save(
                memberRepository.findByUsername(username),
                chatroomRepository.findById(chatroomId)
            );
    }

    private void delete(Member member, Chatroom chatroom) {
        this.memberChatroomRepository.delete(
            this.memberChatroomRepository
                .findByMemberIdAndChatroomId(
                    member.getId(), 
                    chatroom.getId()
                )
        );
    }

    public void delete(String username, Long chatroomId) {
        if (memberRepository.isMember(username) && chatroomRepository.isChatroom(chatroomId))
            this.delete(
                memberRepository.findByUsername(username),
                chatroomRepository.findById(chatroomId)
            );
    }

    public List<MemberChatroom> findByMemberId(Long memberId) {
        return this.memberChatroomRepository.findByMemberId(memberId);
    }

    public List<MemberChatroom> findByChatroomId(Long chatroomId) {
        return this.memberChatroomRepository.findByChatroomId(chatroomId);
    }
}
