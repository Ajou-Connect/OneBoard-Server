package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    public void tearDown() throws Exception {
        lessonRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성하기")
    public void requestCreateLesson() {
        // given
        String lectureTitle = "lecture";
        String lecturePlan = "url";
        String semester = "2021-2";

        Long lectureId = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build()).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String note = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";

        LessonCreateRequestDto requestDto = LessonCreateRequestDto.builder()
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.postForEntity(url, requestDto, ResponseDto.class, lectureId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();

        LessonCreateResponseDto responseDto = mapper.convertValue(responseData, LessonCreateResponseDto.class);

        Lesson newLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();


        assertThat(newLesson.getLecture().getTitle()).isEqualTo(lectureTitle);
        assertThat(newLesson.getLecture().getId()).isEqualTo(lectureId);
        assertThat(newLesson.getNote()).isEqualTo(note);
        assertThat(newLesson.getType()).isEqualTo(type);
        assertThat(newLesson.getRoom()).isEqualTo(room);
        assertThat(newLesson.getMeeting_id()).isEqualTo(meeting_id);
        assertThat(newLesson.getVideo_url()).isEqualTo(video_url);
    }

    @Test
    @DisplayName("수업 조회 요청")
    void requestFindLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build());

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String note = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";

        Long lessonId = lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date).note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build()).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.getForEntity(url, ResponseDto.class, lecture.getId(), lessonId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();
        ObjectMapper mapper = new ObjectMapper();
        LessonFindResponseDto responseDto = mapper.convertValue(responseData, LessonFindResponseDto.class);


        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getLectureId()).isEqualTo(lecture.getId());
        assertThat(responseDto.getNote()).isEqualTo(note);
        assertThat(responseDto.getMeeting_id()).isEqualTo(meeting_id);
        assertThat(responseDto.getVideo_url()).isEqualTo(video_url);
        assertThat(responseDto.getRoom()).isEqualTo(room);
        assertThat(responseDto.getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("수업 삭제 요청")
    void requestDeleteLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String note = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";

        Long lessonId = lessonRepository.save(Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date).note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build()).getId();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        // when
        restTemplate.delete(url, lectureId, lessonId);

        // then
        assertThat(lessonRepository.findById(lessonId)).isEmpty();
    }
    @Test
    @DisplayName("수업 수정 요청")
    void requestUpdateLesson() {
        // given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = Lecture.builder()
                .title(lectureTitle)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build();

        Long lectureId = lectureRepository.save(lecture).getId();

        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String note = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meeting_id = "zoom meeting url";
        String video_url = "lesson video url";


        Lesson lesson = Lesson.builder()
                .lecture(lecture)
                .title(title)
                .date(date).note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build();

        Long lessonId = lessonRepository.save(lesson).getId();

        String update_title = "Test Title2";
        String update_date = LocalDateTime.now().toString();
        String update_note = "lesson note file url2";
        Integer update_type = 2;
        String update_room = "Paldal 411";
        String update_meeting_id = "zoom meeting url2";
        String update_video_url = "lesson video url2";

        LessonUpdateRequestDto requestDto = LessonUpdateRequestDto.builder()
                .title(update_title)
                .date(update_date)
                .note(update_note)
                .type(update_type)
                .room(update_room)
                .meeting_id(update_meeting_id)
                .video_url(update_video_url)
                .build();

        String url = "http://localhost:" + port + "/lecture/{lectureId}/lesson/{lessonId}";

        HttpEntity<LessonUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<ResponseDto> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseDto.class, lectureId, lessonId);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // responseData: LinkedHashMap
        Object responseData = responseEntity.getBody().getData();

        ObjectMapper mapper = new ObjectMapper();
        LessonUpdateResponseDto responseDto = mapper.convertValue(responseData, LessonUpdateResponseDto.class);

        Lesson updatedLesson = lessonRepository.findById(responseDto.getLessonId()).orElseThrow();

        assertThat(updatedLesson.getTitle()).isEqualTo(update_title);
        assertThat(updatedLesson.getType()).isEqualTo(update_type);
        assertThat(updatedLesson.getNote()).isEqualTo(update_note);
        assertThat(updatedLesson.getRoom()).isEqualTo(update_room);
        assertThat(updatedLesson.getMeeting_id()).isEqualTo(update_meeting_id);
        assertThat(updatedLesson.getVideo_url()).isEqualTo(update_video_url);
    }

}