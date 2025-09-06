package cz.cvut.fit.tjv.infosystem_client.controller;

import cz.cvut.fit.tjv.infosystem_client.dto.GradeDTO;
import cz.cvut.fit.tjv.infosystem_client.dto.UserSession;
import cz.cvut.fit.tjv.infosystem_client.service.GradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/subjects/{subjectId}/grades")
    public Mono<String> showGradesBySubjectIdForStudent(@PathVariable("subjectId") Integer subjectId,
                                        HttpSession session,
                                        Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( userSession.getUserType().equals("STUDENT")) {
            Mono<List<GradeDTO>> gradesMono = gradeService.getGradesBySubjectIdAndStudentId(
                                     subjectId,
                                     userSession.getId(),
                                     userSession.getToken() );
            return gradesMono.map(
                    grades -> {
                        model.addAttribute("grades", grades);
                        return "gradesBySubjectIdAndStudentId";
                    }
            ).onErrorResume(
                    ex -> {
                        model.addAttribute("errMsg", ex.getMessage());
                        return Mono.just("gradesBySubjectIdAndStudentId");
                    }
            );
        } else {
            model.addAttribute("grades", Collections.emptyList());
            return Mono.just("gradesBySubjectIdAndStudentId");
        }
    }

    @GetMapping("/subjects/{subjectId}/activities/{activityId}/grades")
    public Mono<String> showGradesAndGradeFormByActivityIdForTeacher(
                                                        @PathVariable("subjectId") Integer subjectId,
                                                        @PathVariable("activityId") Integer activityId,
                                                        HttpSession session,
                                                        Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<List<GradeDTO>> gradesMono = gradeService.getGradesBySubjectIdAndActivityId(
                subjectId,
                activityId,
                userSession.getToken() );
        return gradesMono.map(
                grades -> {
                    model.addAttribute("grades", grades);
                    model.addAttribute("activity", activityId);
                    model.addAttribute("subject", subjectId);
                    return "gradesBySubjectIdAndActivityId";
                }).onErrorResume(
                    ex -> {
                        model.addAttribute("errMsg", ex.getMessage());
                        return Mono.just("error");
                    });
    }

    @PostMapping("/subjects/{subjectId}/activities/{activityId}/grades")
    public Mono<String> addGrade(   @PathVariable("subjectId") Integer subjectId,
                                    @PathVariable("activityId") Integer activityId,
                                    @ModelAttribute GradeDTO grade,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        System.out.println(session.getId());
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        String path = request.getRequestURI();  // Gets full path

        Mono<GradeDTO> retGradeMono = gradeService.createGradeBySubjectIdAndActivityId(
                grade,
                subjectId,
                activityId,
                userSession.getToken());
        return retGradeMono.map  ( (retGrade) -> "redirect:" + path )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });

    }

}

