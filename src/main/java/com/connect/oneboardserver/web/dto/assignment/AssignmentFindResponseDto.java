package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentFindResponseDto {

    private Long id;
    private String title;
    private String content;
    private String fileUrl;
    private String startDt;
    private String endDt;
    private String exposeDt;
    private String createdDt;
    private String updatedDt;

    public AssignmentFindResponseDto(Assignment entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.fileUrl = entity.getFileUrl();
        this.startDt = entity.getStartDt();
        this.endDt = entity.getEndDt();
        this.exposeDt = entity.getExposeDt();
        this.createdDt = entity.getCreatedDt();
        this.updatedDt = entity.getUpdatedDt();
    }

}
