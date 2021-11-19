package com.connect.oneboardserver.web.dto.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureFindAllResponseDto {

    private Long id;
    private String title;
    private String semester;
<<<<<<< HEAD
    private String lecturePlan;

    @Builder
    public LectureFindAllResponseDto(Long id, String title, String semester, String lecturePlan) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.lecturePlan = lecturePlan;
=======
    private String lecturePlanUrl;

    @Builder
    public LectureFindAllResponseDto(Long id, String title, String semester, String lecturePlanUrl) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.lecturePlanUrl = lecturePlanUrl;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
    }

    public static LectureFindAllResponseDto toResponseDto(Lecture entity) {
        return LectureFindAllResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
<<<<<<< HEAD
                .lecturePlan(entity.getLecturePlan())
=======
                .lecturePlanUrl(entity.getLecturePlanUrl())
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
                .build();
    }
}
