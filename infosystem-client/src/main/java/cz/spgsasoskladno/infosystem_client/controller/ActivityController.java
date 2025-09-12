package cz.spgsasoskladno.infosystem_client.controller;

import cz.spgsasoskladno.infosystem_client.dto.ActivityDTO;
import cz.spgsasoskladno.infosystem_client.dto.SubjectDTO;
import cz.spgsasoskladno.infosystem_client.dto.UserSession;
import cz.spgsasoskladno.infosystem_client.service.ActivityService;
import cz.spgsasoskladno.infosystem_client.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/subjects/{subjectId}/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final SubjectService subjectService;

    @Autowired
    public ActivityController(ActivityService activityService, SubjectService subjectService) {
        this.activityService = activityService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public Mono<String> showActivitiesAndActivityForm(@PathVariable("subjectId") Integer subjectId,
                                                HttpSession session,
                                                Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");

        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        // Fetch subject details reactively
        Mono<SubjectDTO> subjectMono = subjectService.getSubjectById(subjectId, userSession.getToken());

        return subjectMono.flatMap(subject ->
                        activityService.getActivitiesBySubjectId(subjectId, userSession.getToken())
                                .map(activities -> {
                                    model.addAttribute("subject", subject);
                                    model.addAttribute("activities", activities);
                                    return "activities"; // View name to render
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
    public Mono<String> addActivity(@PathVariable("subjectId") Integer subjectId,
                              @ModelAttribute ActivityDTO activity,
                              HttpSession session,
                              Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        System.out.println(session.getId());
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<ActivityDTO> retActivityMono = activityService.createActivityBySubjectId(
                                        activity,
                                        subjectId,
                                        userSession.getToken());
        return retActivityMono.map  ( retActivity -> {
            model.addAttribute("activity", retActivity);
            return "addActivity"; } )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });
    }

}
