package cz.spgsasoskladno.infosystem.restController;

import com.fasterxml.jackson.annotation.JsonView;
import cz.spgsasoskladno.infosystem.domain.Subject;
import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.domain.View;
import cz.spgsasoskladno.infosystem.service.SubjectService;
import cz.spgsasoskladno.infosystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_USERS)
public class UserController {

    private final UserService userService;
    private final SubjectService subjectService;

    @Autowired
    public UserController(UserService userService, SubjectService subjectService) {
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.readAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if ( user.getId() != null ) {
            return ResponseEntity.badRequest().build();
        }
        User createdUser = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping("/batch")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        if (users == null || users.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Call the service method to save all users in a batch
        List<User> createdUsers = userService.createUsers(users);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or #id == authentication.principal.id")
    @JsonView(View.Summary.class)
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.readById(id, "User does not exist");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/subjects")
    @PreAuthorize("hasAuthority('ROLE_TEACHER') or #id == authentication.principal.id")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Subject>> getSubjectsByUser(@PathVariable Integer id) {
        List<Subject> subjects = subjectService.getSubjectsByStudentId(id);
        if (subjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subjects);
    }


}
