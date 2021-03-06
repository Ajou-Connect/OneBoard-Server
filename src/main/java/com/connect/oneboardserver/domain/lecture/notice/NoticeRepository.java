package com.connect.oneboardserver.domain.lecture.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByLectureId(Long lectureId);

    List<Notice> findAllByLectureIdOrderByExposeDtDesc(Long lectureId);
}
