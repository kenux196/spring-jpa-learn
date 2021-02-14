package com.study.datajpa.repository.custom;

import com.study.datajpa.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
