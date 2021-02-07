package jpabook.jpashop.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.CreateMemberRequest;
import jpabook.jpashop.dto.CreateMemberResponse;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 문제가 있다
     * @param member Member Entity -> 좋은 방법 아님.
     * @return
     */
    @PostMapping(value = "/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { // 엔티티를 외부에 노출하는 케이스 --> 좋지 않음.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 엔티티를 직접 사용하는 것이 아닌, DTO를 통해 외부와 데이터를 전달한다.
     * 무조건 이렇게 하자.
     * @param request Request DTO
     * @return
     */
    @PostMapping(value = "/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
//        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
//        member.setAddress(address);

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
}
