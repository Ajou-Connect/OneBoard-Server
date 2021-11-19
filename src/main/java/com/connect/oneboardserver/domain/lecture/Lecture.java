package com.connect.oneboardserver.domain.lecture;

import com.connect.oneboardserver.domain.lecture.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 30)
    private String semester;
<<<<<<< HEAD

    @Column(length = 128)
    private String lecturePlan;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();
=======
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8

    @Column(length = 128)
    private String lecturePlanUrl;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.REMOVE)
    private List<Notice> notices = new ArrayList<>();

    @Builder
<<<<<<< HEAD
    public Lecture(String title, String semester, String lecturePlan, List<Notice> notices) {
        this.title = title;
        this.semester = semester;
        this.lecturePlan = lecturePlan;
        this.notices = notices;
    }

    public void updateLecturePlan(String lecturePlan) {
        this.lecturePlan = lecturePlan;
=======
    public Lecture(String title, String semester, String lecturePlanUrl, List<Notice> notices) {
        this.title = title;
        this.semester = semester;
        this.lecturePlanUrl = lecturePlanUrl;
        this.notices = notices;
    }

    public void updateLecturePlanUrl(String lecturePlanUrl) {
        this.lecturePlanUrl = lecturePlanUrl;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    }

}
