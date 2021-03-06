package themion7.my_chat.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.dto.SignupDTO;
import themion7.my_chat.backend.repository.MemberChatroomRepository;
import themion7.my_chat.backend.repository.MemberRepository;

@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @NonNull
    private final MemberRepository memberRepository;

    @NonNull
    private final MemberChatroomRepository memberChatroomRepository;

    private final PasswordEncoder encoder = 
        new themion7.my_chat.backend.security.PasswordEncoder();

    public Member save(SignupDTO dto) {
        Member member = Member.builder()
            .username(dto.getUsername())
            .password(encoder.encode(dto.getPassword()))
            .build();

        try {
            this.memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    public Member findByUsername(String username) {
        return this.memberRepository
            .findByUsername(username)
            .map(member -> member)
            .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public Member findByUsernameWithChatrooms(String username) {
        Member member = this.findByUsername(username);
        member.setChatrooms(
            this.memberChatroomRepository.findByMemberId(member.getId())
        );
        return member;
    }

    public void deleteByUsername(String username) {
        this.memberRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
