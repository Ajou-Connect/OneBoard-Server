package com.connect.oneboardserver.domain.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
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

    @Column(length = 128, nullable = false)
    private String note;

    @Column(nullable = false)
    private Integer type;

    @Column(length = 30)
    private String room;

    @Column(length = 128)
    private String meetingId;

    @Column(length = 128)
    private String videoUrl;

    @Builder
    public Lesson(Lecture lecture, String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
        this.lecture = lecture;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public void update(String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }
    public void updateNote(String note) {
        this.note = note;
    }
}
