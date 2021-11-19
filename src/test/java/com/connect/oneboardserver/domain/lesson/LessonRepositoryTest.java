package com.connect.oneboardserver.domain.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
<<<<<<< HEAD
import lombok.ToString;
=======
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LessonRepositoryTest {

    @Autowired
    LessonRepository lessonRepository;

    @AfterEach
    public void cleanUp() {
        lessonRepository.deleteAll();
    }

    @Test
    @DisplayName("수업 생성 및 불러오기")
    public void createLesson() {
        // given
        String title = "Test Title";
        String date = LocalDateTime.now().toString();
        String note = "lesson note file url";
        Integer type = 1;
        String room = "Paldal 410";
        String meetingId = "zoom meeting url";
        String videoUrl = "lesson video url";

        lessonRepository.save(Lesson.builder()
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build());

        // when
        List<Lesson> lessonList = lessonRepository.findAll();

        // then
        Lesson lesson = lessonList.get(0);
        assertThat(lesson.getTitle()).isEqualTo(title);
//        assertThat(lesson.getDate()).isEqualTo(date);
    }
}