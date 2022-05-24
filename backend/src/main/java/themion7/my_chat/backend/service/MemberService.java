package themion7.my_chat.backend.service;

import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.repository.MemberRepository;

@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return this.memberRepository.save(member);
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
