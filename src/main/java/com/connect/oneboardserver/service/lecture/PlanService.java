package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.plan.PlanUploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PlanService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;

    private final LectureRepository lectureRepository;

    @Transactional
    public ResponseDto uploadLecturePlan(Long lectureId, MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseDto("FAIL");
        }

        Lecture lecture = null;
<<<<<<< HEAD
        String uploadedFile = null;
=======
        String uploadedFilePath = null;
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);

            // 강의계획서 파일이 있으면 파일 삭제
<<<<<<< HEAD
            if(lecture.getLecturePlan() != null) {
                if(storageService.delete(lecture.getLecturePlan())) {
                    lecture.updateLecturePlan(null);
=======
            if(lecture.getLecturePlanUrl() != null) {
                if(storageService.delete(lecture.getLecturePlanUrl())) {
                    lecture.updateLecturePlanUrl(null);
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
                }
            }

            String path = "/lecture_" + lectureId + "/plan";
<<<<<<< HEAD
            uploadedFile = storageService.store(path, file);
=======
            uploadedFilePath = storageService.store(path, file);
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

<<<<<<< HEAD
        lecture.updateLecturePlan(uploadedFile);
=======
        lecture.updateLecturePlanUrl(uploadedFilePath);
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8

        PlanUploadResponseDto responseDto = PlanUploadResponseDto.builder()
                .lectureId(lecture.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto deleteLecturePlan(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);

<<<<<<< HEAD
            if(lecture.getLecturePlan() != null) {
                System.out.println(lecture.getLecturePlan());
                if(storageService.delete(lecture.getLecturePlan())) {
                    lecture.updateLecturePlan(null);
=======
            if(lecture.getLecturePlanUrl() != null) {
                System.out.println(lecture.getLecturePlanUrl());
                if(storageService.delete(lecture.getLecturePlanUrl())) {
                    lecture.updateLecturePlanUrl(null);
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
                    return new ResponseDto("SUCCESS");
                } else {
                    throw new Exception("No file to delete");
                }
            } else {
                throw new Exception("No plan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
    }

<<<<<<< HEAD
    public ResponseEntity<Resource> loadLecturePlan(Long lectureId) {
=======
    public ResponseEntity<Resource> loadLecturePlan(Long lectureId) throws Exception {
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        Lecture lecture = null;
        Resource resource = null;
        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
<<<<<<< HEAD
            String filePath = lecture.getLecturePlan();
            resource = storageService.load(filePath);
            String contentDisposition = "attachment; filename=\"" +
                    lecture.getTitle() + "_plan\"";
=======
            String filePath = lecture.getLecturePlanUrl();
            resource = storageService.load(filePath);
            String contentDisposition = "attachment; filename=\"" +
                    lecture.getTitle() + "_plan" + filePath.substring(filePath.lastIndexOf(".")) + "\"";
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
<<<<<<< HEAD
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
=======
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
            throw new Exception("Fail to load lecture plan : lectureId = " + lectureId);
>>>>>>> 5971c9a47de4c33af7e338fead5026229ae9caa8
        }
    }
}
