package com.connect.oneboardserver.domain.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.livemeeting.LiveMeeting;
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

    @Column(length = 128)
    private String noteUrl;

    @Column(nullable = false)
    private Integer type;   // 0 : 녹화, 1 : 비대면, 2 : 대면

    @Column(length = 128)
    private String videoUrl;

    @OneToOne
    private LiveMeeting liveMeeting;

    @Column(length = 30)
    private String room;

    @Builder
    public Lesson(Lecture lecture, String title, String date, String noteUrl, Integer type,
                  String videoUrl, LiveMeeting liveMeeting, String room) {
        this.lecture = lecture;
        this.title = title;
        this.date = date;
        this.noteUrl = noteUrl;
        this.type = type;
        this.videoUrl = videoUrl;
        this.liveMeeting = liveMeeting;
        this.room = room;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public void update(String title, String date, Integer type) {
        this.title = title;
        this.date = date;
        this.type = type;
    }

    public void updateNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public void setRecordingLesson(String videoUrl) {
        this.videoUrl = videoUrl;
        this.liveMeeting = null;
        this.room = null;
    }

    public void setNonFaceToFaceLesson(LiveMeeting liveMeeting) {
        this.videoUrl = null;
        this.liveMeeting = liveMeeting;
        this.room = null;
    }

    public void setFaceToFaceLesson(String room) {
        this.videoUrl = null;
        this.liveMeeting = null;
        this.room = room;
    }
}
