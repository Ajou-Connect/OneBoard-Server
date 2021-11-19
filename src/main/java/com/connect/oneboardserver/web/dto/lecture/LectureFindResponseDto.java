package com.connect.oneboardserver.web.dto.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureFindResponseDto {

    private Long id;
    private String title;
    private String semester;
<<<<<<< HEAD
    private String lecturePlan;
    private String professor;

    @Builder
    public LectureFindResponseDto(Long id, String title, String semester, String lecturePlan, String professor) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.lecturePlan = lecturePlan;
=======
    private String professor;

    @Builder
    public LectureFindResponseDto(Long id, String title, String semester, String professor) {
        this.id = id;
        this.title = title;
        this.semester = semester;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        this.professor = professor;
    }

    public static LectureFindResponseDto toResponseDto(Lecture entity) {
        return LectureFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
<<<<<<< HEAD
                .lecturePlan(entity.getLecturePlan())
=======
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
                .build();
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
