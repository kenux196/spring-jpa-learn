package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Register Member
     * @param member Member Object
     * @return Registered member id
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 중복 체크
     * @param member Member Object
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * Find one member by member Id
     * @param id Member Id
     * @return Member
     */
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    /**
     * Find all members
     * @return All members
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * Find member by member name
     * @param name member name
     * @return members
     */
    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }
}
