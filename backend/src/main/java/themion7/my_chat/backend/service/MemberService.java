package themion7.my_chat.backend.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.dto.SignupDTO;
import themion7.my_chat.backend.repository.MemberRepository;

@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @NonNull
    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder = 
        new themion7.my_chat.backend.security.PasswordEncoder();

    public Member save(SignupDTO dto) {
        Member member = Member.builder()
            .username(dto.getUsername())
            .password(encoder.encode(dto.getPassword()))
            .build();

        this.memberRepository.findByUsername(member.getUsername()).ifPresentOrElse(
            m -> new DuplicateKeyException("Member already exists with username: " + m.getUsername()),
            () -> { this.memberRepository.save(member); }
        );

        return member;
    }

    public Member findById(Long id) {
        return this.memberRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("Member not found with id: " + id)
        );
    }

    public Member findByUsername(String username) {
        return this.memberRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Member not found with username: " + username)
        );
    }

    public void deleteById(Long id) {
        this.memberRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
