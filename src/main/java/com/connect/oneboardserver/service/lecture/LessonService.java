package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.service.attendance.AttendanceService;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
    private final LessonRepository lessonRepository;
    private final LectureRepository lectureRepository;
    private final AttendanceService attendanceService;

    public ResponseDto findLessonList(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        List<Lesson> lessonList = lessonRepository.findAllByLectureIdOrderByDateDesc(lecture.getId());
        List<LessonListFindResponseDto> lessonListFindResponseDtoList = new ArrayList<>();
        for (int i = 0; i < lessonList.size(); i++) {
            lessonListFindResponseDtoList.add(LessonListFindResponseDto.toResponseDto(lessonList.get(i)));
        }
        return new ResponseDto("SUCCESS", lessonListFindResponseDtoList);
    }

    @Transactional
    public ResponseDto createLesson(Long lectureId, LessonCreateRequestDto requestDto) {
        Lecture lecture = null;
        Lesson savedLesson = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        Integer Type = requestDto.getType();
        if (Type == 0) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .videoUrl(requestDto.getVideoUrl())
                    .build());
        }
        if (Type == 1) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .meetingId(requestDto.getMeetingId())
                    .build());
        }
        if (Type == 2) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .room(requestDto.getRoom())
                    .build());
        }
        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);

        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(savedLesson.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto createLessonFile(Long lectureId, LessonCreateRequestDto requestDto, MultipartFile file) throws Exception {
        Lecture lecture = null;
        String uploadedFile = null;
        Lesson savedLesson = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        Integer Type = requestDto.getType();
        if (Type == 0) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .videoUrl(requestDto.getVideoUrl())
                    .build());
        } else if (Type == 1) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .meetingId(requestDto.getMeetingId())
                    .build());
        } else if (Type == 2) {
            savedLesson = lessonRepository.save(Lesson.builder()
                    .lecture((lecture))
                    .title(requestDto.getTitle())
                    .date(requestDto.getDate())
                    .noteUrl(requestDto.getNoteUrl())
                    .type(requestDto.getType())
                    .room(requestDto.getRoom())
                    .build());
        } else {
            return new ResponseDto("FAIL");
        }
        if (!savedLesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        }
        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);
        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(savedLesson.getId())
                .build();

        if (file != null) {

            String path = "/lecture_" + lectureId + "/lesson_" + savedLesson.getId() + "/note";
            uploadedFile = storageService.store(path, file);

            savedLesson.updateNoteUrl(uploadedFile);
        }
        return new ResponseDto("SUCCESS", responseDto);
    }

    public ResponseDto findLesson(Long lectureId, Long lessonId) {
        Lesson lesson = null;
        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            LessonFindResponseDto responseDto = LessonFindResponseDto.toResponseDto(lesson);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto deleteLesson(Long lectureId, Long lessonId) throws IOException {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            if (lesson.getNoteUrl() != null) {
                System.out.println(lesson.getNoteUrl());
                storageService.delete(lesson.getNoteUrl());
            }
            attendanceService.deleteLessonAttendance(lesson.getId());
            lessonRepository.deleteById(lessonId);
            return new ResponseDto("SUCCESS");
        }
    }

    @Transactional
    public ResponseDto updateLesson(Long lectureId, long lessonId, LessonUpdateRequestDto requestDto) {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            lessonUpdate(requestDto, lesson);
            LessonUpdateResponseDto responseDto = LessonUpdateResponseDto.builder()
                    .lessonId(lesson.getId())
                    .build();
            return new ResponseDto("SUCCESS", responseDto);
        }
    }
    public ResponseDto findLessonDefaultInfo(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()-> new IllegalArgumentException("해당 과목이 없습니다 : id = " + lectureId));

        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lectureId);
        String lessonDefaultTitle = lecture.getTitle() + " 수업 " + (lessonList.size() + 1);

        List<String[]> defaultDateTimeList = parseDefaultDateTime(lecture.getDefaultDateTime());
        String nextLessonDateTime = getNextLessonDateTime(defaultDateTimeList);

        LessonFindDefaultResponseDto responseDto = LessonFindDefaultResponseDto.builder()
                .defaultTitle(lessonDefaultTitle)
                .defaultDateTime(nextLessonDateTime)
                .defaultRoom(lecture.getDefaultRoom())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    private int getDayOfWeekValue(String day) {
        String[] dayOfWeek = {"월", "화", "수", "목", "금", "토", "일"};
        return Arrays.asList(dayOfWeek).indexOf(day) + 1;
    }

    private List<String[]> parseDefaultDateTime(String defaultDateTime) {
        String[] defaultDateTimes = defaultDateTime.split(", ");

        List<String[]> defaultDateTimeList = new ArrayList<>();
        for(int i = 0; i < defaultDateTimes.length; i++) {
            String[] splitStr = defaultDateTimes[i].split("[ :-]");     // 월 12:00-13:30
            defaultDateTimeList.add(splitStr);
        }
        return defaultDateTimeList;
    }

    private String getNextLessonDateTime(List<String[]> defaultDateTimeList) {
        LocalDateTime nextLessonDateTime = null;

        LocalDateTime now = LocalDateTime.now();
        int todayOfWeek = now.getDayOfWeek().getValue();

        for(int i = 0; i < defaultDateTimeList.size(); i++) {
            String[] defaultDateTime = defaultDateTimeList.get(i);

            int dateGap = getDayOfWeekValue(defaultDateTime[0]) - todayOfWeek;
            boolean isNowBefore = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond())
                    .isBefore(LocalTime.of(Integer.valueOf(defaultDateTime[1]), Integer.valueOf(defaultDateTime[2])));
            if((dateGap > 0) || (dateGap == 0 && isNowBefore)) {
                nextLessonDateTime = now.plusDays(dateGap);
                nextLessonDateTime = nextLessonDateTime
                        .withHour(Integer.valueOf(defaultDateTime[1]))
                        .withMinute(Integer.valueOf(defaultDateTime[2]))
                        .withSecond(0);
                break;
            }
        }
        if(nextLessonDateTime == null) {
            String[] defaultDateTime = defaultDateTimeList.get(0);
            int dateGap = getDayOfWeekValue(defaultDateTime[0]) - todayOfWeek;

            nextLessonDateTime = now.plusDays(7 - Math.abs(dateGap));
            nextLessonDateTime = nextLessonDateTime
                    .withHour(Integer.valueOf(defaultDateTime[1]))
                    .withMinute(Integer.valueOf(defaultDateTime[2]))
                    .withSecond(0);
        }

        return nextLessonDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}