package themion7.my_chat.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import themion7.my_chat.backend.domain.Chatroom;
import themion7.my_chat.backend.domain.Member;
import themion7.my_chat.backend.domain.MemberChatroom;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Chatroom> get(@AuthenticationPrincipal String username) {
        Member member = memberService.findByUsernameWithChatrooms(username);
        List<MemberChatroom> list = member.getChatrooms();
        List<Chatroom> ret = new ArrayList<>();

        for (MemberChatroom mc : list) ret.add(mc.getChatroom());

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@AuthenticationPrincipal String username) {
        this.memberService.deleteByUsername(username);
    }
}
