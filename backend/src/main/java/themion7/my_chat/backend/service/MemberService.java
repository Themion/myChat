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
        System.out.println("MemberService.save");
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

    private void setChatrooms(Member member) {
        System.out.println("MemberService.setChatrooms");
        member.setChatrooms(
            this.memberChatroomRepository.findByMemberId(member.getId())
        );
    }

    public Member findByUsername(String username) {
        System.out.println("MemberService.findByUsername");
        Member member = this.memberRepository.findByUsername(username);
        this.setChatrooms(member);
        return member;
    }

    public void deleteByUsername(String username) {
        System.out.println("MemberService.deleteByUsername");
        this.memberRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
