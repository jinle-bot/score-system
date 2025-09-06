package cz.cvut.fit.tjv.infosystem.restController;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.infosystem.domain.Grade;
import cz.cvut.fit.tjv.infosystem.domain.Subject;
import cz.cvut.fit.tjv.infosystem.domain.User;
import cz.cvut.fit.tjv.infosystem.domain.View;
import cz.cvut.fit.tjv.infosystem.dto.GradeDTO;
import cz.cvut.fit.tjv.infosystem.service.GradeService;
import cz.cvut.fit.tjv.infosystem.service.SubjectService;
import cz.cvut.fit.tjv.infosystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;


@RestController
@RequestMapping(ApiPaths.BASE_GRADES)
public class GradeForStudentController {

    private final UserService userService;
    private final GradeService gradeService;
    private final SubjectService subjectService;

    @Autowired
    public GradeForStudentController(GradeService gradeService, SubjectService subjectService, UserService userService) {
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or #studentId == authentication.principal.id")
    @JsonView(View.Detailed.class)
    public ResponseEntity<Collection<GradeDTO>> getAllGrades(
            @PathVariable("subjectId") Integer subjectId,
            @PathVariable("studentId") Integer studentId) {
        Subject subject = subjectService.readById(subjectId, "Subject does not exist");
        User student = userService.readById(studentId, "User does not exist");
        if ( ! subject.getStudents().contains(student) ) {
            ResponseEntity.unprocessableEntity().build();
        }
        Collection<Grade> grades = gradeService.getAllGivenGradesBySubjectIdAndStudentId(subjectId, studentId);

        Collection<GradeDTO> gradesDTO = new ArrayList<>();
        for (Grade grade : grades) {
            GradeDTO gradeDTO = new GradeDTO(grade.getId(),
                    grade.getStudent().getUsername(),
                    grade.getActivity(),
                    grade.getScore(),
                    grade.getDate());
            System.out.println(gradeDTO.getUsername());
            gradesDTO.add(gradeDTO);
        }

        return ResponseEntity.ok(gradesDTO);
    }

}
