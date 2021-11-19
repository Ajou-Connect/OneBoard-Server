package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LessonListFindResponseDto {

    private Long lectureId;
    private Long id;
    private String title;
    private String date;
    private String note;
    private Integer type;
    private String room;
<<<<<<< HEAD
    private String meeting_id;
    private String video_url;

    @Builder
    public LessonListFindResponseDto(Long id, Long lectureId, String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
=======
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonListFindResponseDto(Long id, Long lectureId, String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        this.id = id;
        this.lectureId = lectureId;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
<<<<<<< HEAD
        this.meeting_id = meeting_id;
        this.video_url = video_url;
=======
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    }

    public static LessonListFindResponseDto toResponseDto(Lesson entity) {
        return LessonListFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .lectureId(entity.getLecture().getId())
                .note(entity.getNote())
                .type(entity.getType())
                .room(entity.getRoom())
<<<<<<< HEAD
                .meeting_id(entity.getMeeting_id())
                .video_url(entity.getVideo_url())
=======
                .meetingId(entity.getMeetingId())
                .videoUrl(entity.getVideoUrl())
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
                .build();
    }
}


