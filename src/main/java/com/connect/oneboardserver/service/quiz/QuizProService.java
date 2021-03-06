package com.connect.oneboardserver.service.quiz;

import com.connect.oneboardserver.config.socket.SocketService;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.domain.quiz.QuizProRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.quiz.QuizProCreateRequestDto;
import com.connect.oneboardserver.web.dto.quiz.QuizProResponseDto;
import com.connect.oneboardserver.web.dto.quiz.QuizSocketRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class QuizProService {

    private final QuizProRepository quizProRepository;
    private final LessonRepository lessonRepository;
    private final SocketService socketService;

    @Transactional
    public ResponseDto createQuizPro(Long lectureId, Long lessonId, String session, QuizProCreateRequestDto requestDto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()->new IllegalArgumentException("해당 수업이 없습니다. id="+lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            QuizPro quizPro = requestDto.toEntity();
            quizPro.setLesson(lesson);

            quizProRepository.save(quizPro);
            socketService.sendQuizRequestEvent(session, new QuizSocketRequestDto(quizPro));
            QuizProResponseDto responseDto = new QuizProResponseDto(quizPro);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

}
