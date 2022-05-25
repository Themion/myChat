package themion7.my_chat.backend.service;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.dto.SignupDTO;
import themion7.my_chat.backend.repository.MemberRepository;

@RequiredArgsConstructor
public class MemberService {

    @NonNull
    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder = 
        new themion7.my_chat.backend.security.PasswordEncoder();

    public Member save(SignupDTO dto) {
        return this.memberRepository.save(
            Member.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .build()
        );
    }

    public Member findById(Long id) {
        return this.memberRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Member not found with id: " + id)
        );
    }

    public void deleteById(Long id) {
        this.memberRepository.deleteById(id);
    }
}
