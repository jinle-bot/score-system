package cz.spgsasoskladno.infosystem_client.controller;

import cz.spgsasoskladno.infosystem_client.dto.SubjectDTO;
import cz.spgsasoskladno.infosystem_client.dto.UserSession;
import cz.spgsasoskladno.infosystem_client.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public Mono<String> showSubjects(HttpSession session, Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }
        if ( ! userSession.getUserType().equals("TEACHER") ) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<List<SubjectDTO>> subjectsMono = subjectService.getSubjects(userSession.getToken()).collectList();
        return subjectsMono.map(
                subjects -> {
                    model.addAttribute("subjects", subjects);
                    return "subjects";
                }
                                )
                .onErrorResume( ex -> {
                   model.addAttribute("errMsg", ex.getMessage());
                   return Mono.just("error");
                });
    }

    @PostMapping
    public Mono<String> addSubject(
                                    @ModelAttribute SubjectDTO subject,
                                    HttpSession session,
                                    Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<SubjectDTO> retSubjectMono = subjectService.addSubject(subject, userSession.getToken());
        return retSubjectMono.map  ( retSubject -> {
                    model.addAttribute("subject", retSubject);
                    return "addSubject"; } )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });
    }


    @GetMapping("/{subjectId}")
    public Mono<String> showSubjectById(@PathVariable("subjectId") Integer subjectId,
                                  HttpSession session,
                                  Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<SubjectDTO> subjectMono = subjectService.getSubjectById(subjectId, userSession.getToken());
        return subjectMono.map(
                subject -> {
                    model.addAttribute("subject", subject);
                    return "subject";
                }
        ).onErrorResume( ex -> {
            model.addAttribute("errMsg", ex.getMessage());
            return Mono.just("error");
        });
    }

}
