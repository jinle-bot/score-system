package cz.spgsasoskladno.infosystem.restController;

import cz.spgsasoskladno.infosystem.dto.grade.CourseGradesResponseDto;
import cz.spgsasoskladno.infosystem.service.GradeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/courses/{courseId}/students/{studentId}/grades")
@RequiredArgsConstructor
@Validated
public class GradeForStudentController {

    private final GradeService gradeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<CourseGradesResponseDto> getAllGrades(
            @PathVariable("courseId") @Positive Integer courseId,
            @PathVariable("studentId") @Positive Integer studentId) {
        CourseGradesResponseDto gradesDTO = gradeService.getCourseGradesByStudentAndCourse(studentId, courseId);
        return ResponseEntity.ok(gradesDTO);
    }

}
