package com.connect.oneboardserver.web.controller.assignment;

import com.connect.oneboardserver.service.assignment.SubmitService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.assignment.SubmitCheckRequestDto;
import com.connect.oneboardserver.web.dto.assignment.SubmitCreateRequestDto;
import com.connect.oneboardserver.web.dto.assignment.SubmitUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class SubmitApiController {

    private final SubmitService submitService;

    //과제 제출
    @PostMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/ver1")
    public ResponseDto createSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                    ServletRequest request, @RequestBody SubmitCreateRequestDto requestDto) {
        return submitService.createSubmit(lectureId, assignmentId, request, requestDto);
    }

    //과제 제출(파일)
    @PostMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit")
    public ResponseDto createSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                    ServletRequest request, @ModelAttribute SubmitCreateRequestDto requestDto,
                                    @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        return submitService.createSubmitFile(lectureId, assignmentId, request, requestDto, file);
    }

    //과제 피드백 입력
    @PostMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/{submitId}")
    public ResponseDto checkSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                   @PathVariable Long submitId, @RequestBody SubmitCheckRequestDto requestDto) {
        return submitService.checkSubmit(lectureId, assignmentId, submitId, requestDto);
    }

    //과제 결과물 확인 (강의자)
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/{submitId}")
    public ResponseDto findSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                  @PathVariable Long submitId) {
        return submitService.findSubmit(lectureId, assignmentId, submitId);
    }

    //과제 결과물 확인 (학생)
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit")
    public ResponseDto findSubmitStu(@PathVariable Long lectureId, @PathVariable Long assignmentId, ServletRequest request) {
        return submitService.findSubmitStu(lectureId, assignmentId, request);
    }

    //과제 결과물 수정
    @PutMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/ver1")
    public ResponseDto updateSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                    ServletRequest request, @RequestBody SubmitUpdateRequestDto requestDto) {
        return submitService.updateSubmit(lectureId, assignmentId, request, requestDto);
    }

    //과제 결과물 수정(파일)
    @PutMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit")
    public ResponseDto updateSubmit(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                    ServletRequest request, @ModelAttribute SubmitUpdateRequestDto requestDto,
                                    @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        return submitService.updateSubmitFile(lectureId, assignmentId, request, requestDto, file);
    }

    //과제 제출 리스트 조회
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}/submits")
    public ResponseDto findSubmitList(@PathVariable Long lectureId, @PathVariable Long assignmentId) {
        return submitService.findSubmitList(lectureId, assignmentId);
    }

    //과제 제출물 파일 로그
    @GetMapping("/lecture/{lectureId}/assignment/{assignmentId}/submit/{submitId}/file")
    public ResponseEntity<Resource> loadSubmitFile(@PathVariable Long lectureId, @PathVariable Long assignmentId,
                                                   @PathVariable Long submitId) throws Exception{
        return submitService.loadSubmitFile(lectureId, assignmentId, submitId);
    }

}
