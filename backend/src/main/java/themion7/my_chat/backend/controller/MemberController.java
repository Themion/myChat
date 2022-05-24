package themion7.my_chat.backend.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.service.MemberService;

@RequestMapping("member")
@RestController
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody final Member member) {
        this.memberService.save(member);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteById(@RequestBody final Long id) {
        this.memberService.deleteById(id);
    }
}
