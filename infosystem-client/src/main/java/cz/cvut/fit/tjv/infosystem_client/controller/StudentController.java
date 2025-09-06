package cz.cvut.fit.tjv.infosystem_client.controller;

import cz.cvut.fit.tjv.infosystem_client.dto.SubjectDTO;
import cz.cvut.fit.tjv.infosystem_client.dto.UserDTO;
import cz.cvut.fit.tjv.infosystem_client.dto.UserSession;
import cz.cvut.fit.tjv.infosystem_client.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/subjects/{subjectId}/students")
public class StudentController {

    private final SubjectService subjectService;

    @Autowired
    public StudentController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public Mono<String> showStudentsAndStudentForm(   @PathVariable("subjectId") Integer subjectId,
                                                      HttpSession session,
                                                      Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");

        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER") ) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<SubjectDTO> subjectMono = subjectService.getSubjectById(subjectId, userSession.getToken());

        return subjectMono.flatMap(subject ->
                        subjectService.getStudentsBySubjectId(subjectId, userSession.getToken())
                                .map(students -> {
                                    model.addAttribute("subject", subject);
                                    model.addAttribute("students", students);
                                    return "students"; // View name to render
                                })
                                .onErrorResume(ex -> {
                                    model.addAttribute("errMsg", ex.getMessage());
                                    return Mono.just("error"); // Return error view
                                })
                )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });
    }

    @PostMapping
    public Mono<String> addStudent(@PathVariable("subjectId") Integer subjectId,
                                    @ModelAttribute UserDTO student,
                                    HttpSession session,
                                    Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        System.out.println(student.getUsername());

        Mono<UserDTO> retStudentMono = subjectService.addStudentToSubject(
                student,
                subjectId,
                userSession.getToken());
        return retStudentMono.map  ( retStudent -> {
                    model.addAttribute("student", retStudent);
                    model.addAttribute("subjectId", subjectId);
                    return "addStudent"; } )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });
    }


}
