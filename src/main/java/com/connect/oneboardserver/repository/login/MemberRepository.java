package com.connect.oneboardserver.repository.login;

import com.connect.oneboardserver.domain.login.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    Optional<Member> findByLoginId(String student_num);
    List<Member> findAll();
}
