package com.connect.oneboardserver.web.lecture.lesson;

import com.connect.oneboardserver.service.lecture.lesson.LessonService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LessonApiController {

    private final LessonService lessonService;

    // 수업 목록 조회
    @GetMapping("/lecture/{lectureId}/lessons")
    public ResponseDto findLessonList(@PathVariable Long lectureId) {
        return lessonService.findLessonList(lectureId);
    }

    // 수업 생성
    @PostMapping("/lecture/{lectureId}/lesson")
    public ResponseDto createLesson(@PathVariable Long lectureId, @RequestBody LessonCreateRequestDto requestDto) {
        return lessonService.createLesson(lectureId, requestDto);
    }
    // 수업 조회
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}")
    public ResponseDto findLesson(@PathVariable Long lectureId, @PathVariable Long lessonId) {
        return lessonService.findLesson(lectureId, lessonId);
    }
    // 수업 삭제
    @DeleteMapping("/lecture/{lectureId}/lesson/{lessonId}")
    public ResponseDto deleteNotice(@PathVariable Long lectureId, @PathVariable Long lessonId) {
        return lessonService.deleteLesson(lectureId, lessonId);
    }

}