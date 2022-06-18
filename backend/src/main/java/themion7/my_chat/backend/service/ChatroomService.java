package themion7.my_chat.backend.service;

import java.security.Principal;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.util.HtmlUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.domain.MemberChatroom;
import themion7.my_chat.backend.dto.ChatroomDTO;
import themion7.my_chat.backend.repository.ChatroomRepository;
import themion7.my_chat.backend.repository.MemberChatroomRepository;
import themion7.my_chat.backend.repository.MemberRepository;

@RequiredArgsConstructor
public class ChatroomService {

    @NonNull
    private final MemberRepository memberRepository;

    @NonNull
    private final ChatroomRepository chatroomRepository;

    @NonNull
    private final MemberChatroomRepository memberChatroomRepository;

    @Value("${chat.lifespan}")
    private Long lifespan;

    @Async
    public void deleteIfRoomEmpty(Long id) {
        try {
            Thread.sleep(lifespan);
            this.chatroomRepository.findById(id).ifPresent(chatroom -> 
                chatroomRepository.deleteIfEmpty(chatroom)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long save(ChatroomDTO chatroomDTO) {
        return this.chatroomRepository.save(
            Chatroom.builder()
                .title(HtmlUtils.htmlEscape(chatroomDTO.getTitle()))
                .population(0L)
                .build()
        ).getId();
    }

    public Chatroom findById(Long id) {
        return this.chatroomRepository.findById(id)
            .map(member -> member)
            .orElseThrow(() -> new NoResultException(""));
    }

    public List<Chatroom> findAll() {
        return this.chatroomRepository.findAll();
    }

    public void join(final Long id, Principal principal) {
        this.chatroomRepository.increaseRoomPopulationById(id);

        memberRepository.findByUsername(principal.getName()).ifPresent(member -> {
            try {
                memberChatroomRepository.findByMemberIdAndChatroomId(member.getId(), id);
            } catch (NoResultException e) {
                memberChatroomRepository.save(
                    MemberChatroom.builder()
                        .member(member)
                        .chatroom(this.findById(id))
                        .build()
                );
            }
        });
    }

    public void leave(final Long id) {
        this.chatroomRepository.findById(id).ifPresent(chatroom -> {
            chatroomRepository.decreaseRoomPopulationById(id);
            chatroomRepository.deleteIfEmpty(chatroom);
        });
    }
}
