package com.connect.oneboardserver.service.livemeeting;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.livemeeting.LiveMeeting;
import com.connect.oneboardserver.domain.livemeeting.LiveMeetingRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class LiveMeetingService {

    private final LiveMeetingRepository liveMeetingRepository;
    private final LessonRepository lessonRepository;
    private final UserDetailsService userDetailsService;

    public LiveMeeting createLiveMeeting(Long lectureId) {
        String session = "session_" + lectureId + "_" + Instant.now().getEpochSecond();

        return liveMeetingRepository.save(LiveMeeting.builder()
                .session(session)
                .build());
    }

    public void deleteLiveMeeting(Long liveMeetingId) {
        LiveMeeting liveMeeting = liveMeetingRepository.findById(liveMeetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 비대면 수업이 없습니다 : id = " + liveMeetingId));
        liveMeetingRepository.deleteById(liveMeeting.getId());
    }

    @Transactional
    public ResponseDto enterLiveMeeting(String email, Long lectureId, Long lessonId, String session) throws Exception {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다 : id = " + lessonId));

        if(!lesson.getLecture().getId().equals(lectureId)) {
            throw new IllegalArgumentException("올바른 과목 id와 수업 id가 아닙니다 : lecture id = " + lectureId
                    + " lesson id = " + lessonId);
        }

        if(!lesson.getLiveMeeting().getSession().equals(session)) {
            throw new IllegalArgumentException("올바른 세션이 아닙니다 : session = " + session);
        }

        if(lesson.getLiveMeeting().getEndDt() != null) {
            throw new Exception("이미 종료된 비대면 수업입니다");
        }

        Member member = (Member) userDetailsService.loadUserByUsername(email);

        if(lesson.getLiveMeeting().getStartDt() == null) {      // 수업 시작 전
            if(member.getRoles().get(0).equals("ROLE_S")) {     // 학생
                throw new Exception("아직 시작되지 않은 비대면 수업입니다");
            } else {    // 강의자
                lesson.getLiveMeeting().startLiveMeeting();
                return new ResponseDto("SUCCESS");
            }
        } else {    // 수업 시작 후
            return new ResponseDto("SUCCESS");
        }
    }

    @Transactional
    public ResponseDto exitLiveMeeting(String email, Long lectureId, Long lessonId, String session) throws Exception {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수업이 없습니다 : id = " + lessonId));

        if(!lesson.getLecture().getId().equals(lectureId)) {
            throw new IllegalArgumentException("올바른 과목 id와 수업 id가 아닙니다 : lecture id = " + lectureId
                    + " lesson id = " + lessonId);
        }

        if(!lesson.getLiveMeeting().getSession().equals(session)) {
            throw new IllegalArgumentException("올바른 세션이 아닙니다 : session = " + session);
        }

        if(lesson.getLiveMeeting().getStartDt() == null) {
            throw new Exception("아직 시작되지 않은 비대면 수업입니다");
        }

        Member member = (Member) userDetailsService.loadUserByUsername(email);

        if(member.getRoles().get(0).equals("ROLE_T")) {     // 강의자
            lesson.getLiveMeeting().closeLiveMeeting();
        }

        return new ResponseDto("SUCCESS");
    }
}