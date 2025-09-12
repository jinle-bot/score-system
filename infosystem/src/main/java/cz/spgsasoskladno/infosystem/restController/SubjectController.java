package cz.spgsasoskladno.infosystem.restController;

import com.fasterxml.jackson.annotation.JsonView;
import cz.spgsasoskladno.infosystem.domain.Subject;
import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.domain.View;
import cz.spgsasoskladno.infosystem.service.SubjectService;
import cz.spgsasoskladno.infosystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(ApiPaths.BASE_SUBJECTS)
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;

    @Autowired
    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.readAll();

        if (subjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subjects);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        if ( subject.getId() != null ) {
            return ResponseEntity.badRequest().build();
        }
        Subject createdSubject = subjectService.create(subject);
        URI location = URI.create("/api/v1/subjects/" + createdSubject.getId());
        return ResponseEntity.created(location).body(createdSubject);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Subject> getSubjectById(@PathVariable Integer id) {
        System.out.println("read Subject " + id.toString());
        Subject subject = subjectService.readById(id, "Subject does not exist");
        System.out.println("ok, mel by se vratit " + id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Set<User>> getStudentsBySubject(@PathVariable Integer id) {
        Subject subject = subjectService.readById(id, "Subject does not exist");
        if (subject.getStudents().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subject.getStudents());
    }

    @PostMapping("/{id}/students")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<User> addStudentToSubject(@RequestBody User student, @PathVariable Integer id) {
        if ( student.getUsername() == null ) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.readByUsername(student.getUsername());
        Subject subject = subjectService.addStudentIntoSubject(user.getId(), id);
        return ResponseEntity.ok(user);
    }

}
