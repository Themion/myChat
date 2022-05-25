package themion7.my_chat.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.dto.SignupDTO;
import themion7.my_chat.backend.service.MemberService;

@RequestMapping("member")
@RestController
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody final SignupDTO dto) {
        this.memberService.save(dto);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteById(@AuthenticationPrincipal String username) {
        this.memberService.deleteByUsername(username);
    }
}
