package com.connect.oneboardserver.web.dto.lecture.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonUpdateRequestDto {
    private String title;
    private String date;
    private String note;
    private Integer type;
    private String room;
<<<<<<< HEAD
    private String meeting_id;
    private String video_url;

    @Builder
    public LessonUpdateRequestDto(String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
=======
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonUpdateRequestDto(String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
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

}
