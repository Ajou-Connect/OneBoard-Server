package com.connect.oneboardserver.domain.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

<<<<<<< HEAD
@NoArgsConstructor
@Entity
@Getter
=======
@Getter
@NoArgsConstructor
@Entity
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Lecture lecture;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(nullable = false)
    private String date;

<<<<<<< HEAD
    @Column(length = 128)
=======
    @Column(length = 128, nullable = false)
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    private String note;

    @Column(nullable = false)
    private Integer type;

    @Column(length = 30)
    private String room;

    @Column(length = 128)
<<<<<<< HEAD
    private String meeting_id;

    @Column(length = 128)
    private String video_url;

    @Builder
    public Lesson(Lecture lecture, String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
=======
    private String meetingId;

    @Column(length = 128)
    private String videoUrl;

    @Builder
    public Lesson(Lecture lecture, String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        this.lecture = lecture;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
<<<<<<< HEAD
        this.meeting_id = meeting_id;
        this.video_url = video_url;
    }
=======
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

<<<<<<< HEAD
    public void update(String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
=======
    public void update(String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
<<<<<<< HEAD
        this.meeting_id = meeting_id;
        this.video_url = video_url;
    }
    public void updateNote(String note) {
        this.note = note;
=======
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    }
}
