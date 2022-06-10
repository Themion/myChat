package themion7.my_chat.backend.service;

import java.security.Principal;
import java.util.List;

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
            Chatroom chatroom = this.chatroomRepository.findById(id);
            if (chatroom != null && chatroom.getPopulation() == 0L) 
                this.chatroomRepository.deleteById(id);
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
        return this.chatroomRepository.findById(id);
    }

    public List<Chatroom> findAll() {
        return this.chatroomRepository.findAll();
    }

    public void join(final Long id, Principal principal) {
        this.chatroomRepository.increaseRoomPopulationById(id);

        if (memberRepository.isMember(principal.getName()))
            memberChatroomRepository.save(
                MemberChatroom.builder()
                    .member(memberRepository.findByUsername(principal.getName()))
                    .chatroom(this.findById(id))
                    .build()
            );
    }

    public void leave(final Long id, Principal principal) {
        Chatroom chatroom = this.chatroomRepository.decreaseRoomPopulationById(id);
        if (chatroom != null && chatroom.getPopulation() == 0L)
            this.chatroomRepository.deleteById(id);

        if (memberRepository.isMember(principal.getName()))
            memberChatroomRepository.delete(
                memberChatroomRepository.findByMemberIdAndChatroomId(
                    memberRepository.findByUsername(principal.getName()).getId(),
                    id
                )
            );
    }
}
