package cz.spgsasoskladno.infosystem.restController;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.infosystem.domain.*;
import cz.spgsasoskladno.infosystem.domain.*;
import cz.spgsasoskladno.infosystem.dto.GradeDTO;
import cz.spgsasoskladno.infosystem.service.ActivityService;
import cz.spgsasoskladno.infosystem.service.GradeService;
import cz.spgsasoskladno.infosystem.service.SubjectService;
import cz.spgsasoskladno.infosystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(ApiPaths.BASE_ACTIVITIES)
public class ActivityController {

    private final ActivityService activityService;
    private final SubjectService subjectService;
    private final GradeService gradeService;
    private final UserService userService;

    @Autowired
    public ActivityController(ActivityService activityService, SubjectService subjectService, GradeService gradeService, UserService userService) {
        this.activityService = activityService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Activity>> getActivitiesBySubject(@PathVariable("subjectId") Integer id) {
        List<Activity> activities = activityService.getActivitiesBySubjectId(id);
        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Activity> createActivity(@PathVariable("subjectId") Integer id, @RequestBody Activity activity) {
        Subject subject = subjectService.readById(id, "Subject does not exist");
        activity.setSubject(subject);
        Activity createdActivity = activityService.create(activity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{activityId}")
                .buildAndExpand(createdActivity.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdActivity);
    }

    @GetMapping("/{activityId}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Activity> getActivity(
            @PathVariable("subjectId") Integer subjectId,
            @PathVariable("activityId") Integer activityId) {
        Activity activity = activityService.getActivityByActivityIdAndSubjectId(activityId, subjectId);
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/{activityId}/grades")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<Collection<GradeDTO>> getAllGradesByActivityId(
            @PathVariable("subjectId") Integer subjectId,
            @PathVariable("activityId") Integer activityId) {

        Subject subject = subjectService.readById(subjectId, "Subject does not exist");
        Activity activity = activityService.readById(activityId, "Activity does not exist");

        if ( ! activity.getSubject().getId().equals(subject.getId())) {
            throw new IllegalArgumentException("Activity does not belong to this subject");
        }

        Collection<Grade> grades = gradeService.getAllGivenGradesByActivityId(activityId);
        if (grades.isEmpty())
            return ResponseEntity.noContent().build();

        Collection<GradeDTO> gradesDTO = new ArrayList<>();
        for (Grade grade : grades) {
            gradesDTO.add(grade.toDTO());
        }

        return ResponseEntity.ok(gradesDTO);
    }

    @PostMapping("/{activityId}/grades")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<GradeDTO> addGradeByActivityIdForStudent(
            @PathVariable("subjectId") Integer subjectId,
            @PathVariable("activityId") Integer activityId,
            @RequestBody GradeDTO grade) {

        Subject subject = subjectService.readById(subjectId, "Subject does not exist");
        Activity activity = activityService.readById(activityId, "Activity does not exist");

        if ( ! activity.getSubject().getId().equals(subject.getId())) {
            throw new IllegalArgumentException("Activity does not belong to this subject");
        }

        String username = grade.getUsername();
        if ( username == null ) {
            throw new IllegalArgumentException("Student username is null");
        }

        User user = userService.readByUsername(username);

        Grade createdGrade = new Grade();
        createdGrade.setStudent(user);
        createdGrade.setId(grade.getId());
        createdGrade.setScore(grade.getScore());
        createdGrade.setActivity(activity);
        createdGrade.setDate(grade.getDate());

        createdGrade = gradeService.create(createdGrade);
        GradeDTO responseGradeDTO = createdGrade.toDTO();

        return ResponseEntity.ok(responseGradeDTO);

    }

}
