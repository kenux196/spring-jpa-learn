package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.CreateMemberRequest;
import jpabook.jpashop.dto.CreateMemberResponse;
import jpabook.jpashop.dto.PatchMemberRequest;
import jpabook.jpashop.dto.PatchMemberResponse;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
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
        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
        member.setAddress(address);

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PatchMapping(value = "/api/v2/members/{id}")
    public PatchMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid PatchMemberRequest request) {

        log.info("[kenux debug]" + request);

        memberService.update(id, request);
        Member findMember = memberService.findOne(id);
        return PatchMemberResponse.create(findMember);
    }

    /**
     * 엔티티 그대로 노출되어서 좋지 않음.
     * 후속 처리가 더 골치 아픔.
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    /**
     * 조회 V2 : 응답 값으로 엔티티가 아닌 별도의 DTO를 반환
     */
    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        // Entity -> DTO
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static
    class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static
    class MemberDto {
        private String name;
    }
}
