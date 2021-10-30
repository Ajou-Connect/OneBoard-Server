package com.connect.oneboardserver;

import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.repository.login.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        Member member = new Member();
        member.setStudent_num("201823787");
        member.setPassword("0000");
        member.setName("공민정");
        member.setEmail("kong1301@ajou.ac.kr");
        member.setUser_type(2);
        member.setUniversity("아주대학교");
        member.setMajor("소프트웨어학과");
        member.setLecture_id("F0132");

        memberRepository.save(member);

    }

}
