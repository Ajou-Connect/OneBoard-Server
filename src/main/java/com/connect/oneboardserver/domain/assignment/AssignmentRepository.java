package com.connect.oneboardserver.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByLectureId(Long lectureId);
}
