package com.connect.oneboardserver.domain.login;

import com.connect.oneboardserver.repository.login.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    /**
     * @return null이면 로그인 실패
     */
    public Member login(String student_num, String password) {
        return memberRepository.findByLoginId(student_num)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
