package themion7.my_chat.backend.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.domain.MemberChatroom;

@Transactional
@SpringBootTest
public class MemberChatroomRepositoryTest {
    @Autowired MemberChatroomRepository memberChatroomRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ChatroomRepository chatroomRepository;
    
    @Test
    public void create() {
        Member member = Member.builder()
            .username("usernameTest")
            .password("passwordTest")
            .build();
        
        Chatroom chatroom = Chatroom.builder()
            .title("title")
            .build();
        Chatroom chatroom2 = Chatroom.builder()
            .title("title2")
            .build();

        MemberChatroom memberChatroom = MemberChatroom.builder()
            .member(member)
            .chatroom(chatroom2)
            .build();

        memberRepository.save(member);
        chatroomRepository.save(chatroom);
        chatroomRepository.save(chatroom2);
        memberChatroomRepository.save(memberChatroom);

        Assertions
            .assertThat(memberChatroom.getId())
            .isNotNull();
        Assertions
            .assertThat(memberChatroom.getMember().getId())
            .isNotNull();
    }

    @Test
    public void read() {
        Member member = Member.builder()
            .username("usernameTester")
            .password("password")
            .build();
        
        Chatroom chatroom = Chatroom.builder()
            .title("title")
            .build();

        MemberChatroom memberChatroom = MemberChatroom.builder()
            .member(member)
            .chatroom(chatroom)
            .build();

        memberRepository.save(member);
        chatroomRepository.save(chatroom);
        memberChatroomRepository.save(memberChatroom);

        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByMemberId(member.getId())
            ).isNotEmpty();
        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByChatroomId(chatroom.getId())
            ).isNotEmpty();

        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByMemberId(member.getId() + 1)
            ).isEmpty();
        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByChatroomId(chatroom.getId() + 1)
            ).isEmpty();

        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByMemberIdAndChatroomId(30L, 30L)
            ).isEmpty();
    }

    @Test
    public void delete() {
        Member member = Member.builder()
            .username("usernameTest")
            .password("passwordTest")
            .build();
        
        Chatroom chatroom = Chatroom.builder()
            .title("title")
            .build();

        MemberChatroom memberChatroom = MemberChatroom.builder()
            .member(member)
            .chatroom(chatroom)
            .build();

        memberRepository.save(member);
        chatroomRepository.save(chatroom);
        memberChatroomRepository.save(memberChatroom);

        memberChatroomRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroom.getId());

        Assertions
            .assertThat(
                memberChatroomRepository
                    .findByMemberId(member.getId())
            ).isEmpty();
    }

    @Test
    public void UniqueConstraint() {
        Member member = Member.builder()
            .username("usernameTest")
            .password("passwordTest")
            .build();

        Chatroom chatroom = Chatroom.builder()
            .title("title")
            .build();

        memberRepository.save(member);
        chatroomRepository.save(chatroom);

        memberChatroomRepository.save(
            MemberChatroom.builder()
                .member(member)
                .chatroom(chatroom)
                .build()
        );

        assertThrows(
            Exception.class, 
            () -> {
                memberChatroomRepository.save(
                    MemberChatroom.builder()
                        .member(member)
                        .build()
                );
            }
        );

        assertThrows(
            Exception.class, 
            () -> {
                memberChatroomRepository.save(
                    MemberChatroom.builder()
                        .member(member)
                        .chatroom(chatroom)
                        .build()
                );
            }
        );
    }
}
