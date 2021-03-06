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
    private String defaultDatetime;
    private String defaultRoom;
    private String lecturePlanUrl;

    @Builder
    public LectureFindAllResponseDto(Long id, String title, String semester, String defaultDatetime, String defaultRoom, String lecturePlanUrl) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.defaultDatetime = defaultDatetime;
        this.defaultRoom = defaultRoom;
        this.lecturePlanUrl = lecturePlanUrl;
    }

    public static LectureFindAllResponseDto toResponseDto(Lecture entity) {
        return LectureFindAllResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
                .defaultDatetime(entity.getDefaultDateTime())
                .defaultRoom(entity.getDefaultRoom())
                .lecturePlanUrl(entity.getLecturePlanUrl())
                .build();
    }
}
