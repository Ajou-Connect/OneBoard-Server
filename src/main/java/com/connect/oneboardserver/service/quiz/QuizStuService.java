package com.connect.oneboardserver.service.quiz;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.config.socket.SocketService;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.domain.quiz.QuizProRepository;
import com.connect.oneboardserver.domain.quiz.QuizStu;
import com.connect.oneboardserver.domain.quiz.QuizStuRepository;
import com.connect.oneboardserver.web.dto.quiz.*;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizStuService {

    private final QuizProRepository quizProRepository;
    private final QuizStuRepository quizStuRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final SocketService socketService;

    @Transactional
    public ResponseDto createQuizStu(Long lectureId, Long lessonId, Long quizProId, String session, ServletRequest request, QuizStuCreateRequestDto requestDto) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        QuizPro quizPro = quizProRepository.findById(quizProId)
                .orElseThrow(()->new IllegalArgumentException("해당 퀴즈가 없습니다. id="+quizProId));

        if (!(quizPro.getLesson().getId().equals(lessonId) && quizPro.getLesson().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            QuizStu quizStu = requestDto.toEntity();
            quizStu.setQuizPro(quizPro);
            quizStu.setStudent(student);

            quizStuRepository.save(quizStu);
            socketService.sendQuizResponseEvent(session);
            QuizStuResponseDto responseDto = new QuizStuResponseDto(quizStu);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto findResultStu(Long lectureId, Long lessonId, Long quizProId, ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member student = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        QuizPro quizPro = quizProRepository.findById(quizProId)
                .orElseThrow(()->new IllegalArgumentException("해당 퀴즈가 없습니다. id="+quizProId));

        if (!(quizPro.getLesson().getId().equals(lessonId) && quizPro.getLesson().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            List<QuizStu> quizStuList = quizStuRepository.findAllByQuizProId(quizProId);
            List<QuizStuFindResponseDto> quizOList = new ArrayList<>();
            List<QuizStuFindResponseDto> quizXList = new ArrayList<>();

            for (int i = 0; i < quizStuList.size(); i++) {
                QuizStu quizStu = quizStuList.get(i);
                if (quizStu.getResponse().equals(quizPro.getAnswer())) {
                    quizOList.add(new QuizStuFindResponseDto(quizStu));
                    quizStu.setMark("O");
                } else {
                    quizXList.add(new QuizStuFindResponseDto(quizStu));
                    quizStu.setMark("X");
                }
            }
            quizPro.updateInfo(quizOList.size(), quizXList.size());

            QuizStu yourQuiz = quizStuRepository.findByStudentIdAndQuizProId(student.getId(), quizProId);
            if (yourQuiz == null) {
                ResultStuNullResponseDto responseDto = new ResultStuNullResponseDto(quizPro);
                return new ResponseDto("SUCCESS", responseDto);
            } else {
                ResultStuResponseDto responseDto = new ResultStuResponseDto(yourQuiz);
                return new ResponseDto("SUCCESS", responseDto);
            }

        }
    }

    @Transactional
    public ResponseDto findResultPro(Long lectureId, Long lessonId, Long quizProId) {
        QuizPro quizPro = quizProRepository.findById(quizProId)
                .orElseThrow(()->new IllegalArgumentException("해당 퀴즈가 없습니다. id="+quizProId));

        if (!(quizPro.getLesson().getId().equals(lessonId) && quizPro.getLesson().getLecture().getId().equals(lectureId))) {
            return new ResponseDto("FAIL");
        } else {
            List<QuizStu> quizStuList = quizStuRepository.findAllByQuizProId(quizProId);
            List<QuizStuFindResponseDto> quizOList = new ArrayList<>();
            List<QuizStuFindResponseDto> quizXList = new ArrayList<>();

            for (int i = 0; i < quizStuList.size(); i++) {
                QuizStu quizStu = quizStuList.get(i);
                if (quizStu.getResponse().equals(quizPro.getAnswer())) {
                    quizOList.add(new QuizStuFindResponseDto(quizStu));
                    quizStu.setMark("O");
                } else {
                    quizXList.add(new QuizStuFindResponseDto(quizStu));
                    quizStu.setMark("X");
                }
            }

            quizPro.updateInfo(quizOList.size(), quizXList.size());
            ResultProResponseDto responseDto = new ResultProResponseDto(quizPro, quizOList, quizXList);

            return new ResponseDto("SUCCESS", responseDto);
        }

    }
}
