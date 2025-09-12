package cz.spgsasoskladno.infosystem_client.controller;

import cz.spgsasoskladno.infosystem_client.dto.SubjectDTO;
import cz.spgsasoskladno.infosystem_client.dto.UserSession;
import cz.spgsasoskladno.infosystem_client.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");

        if (userSession == null) {
            return "redirect:/auth/login";
        }

        if ( userSession.getUserType().equals("STUDENT") ) {
            List<SubjectDTO> subjects = userService.getSubjectsByStudentId(
                                            userSession.getId(),
                                            userSession.getToken() ).block();
            model.addAttribute("subjects", subjects);
        } else if ( userSession.getUserType().equals("TEACHER") ) {
            model.addAttribute("showTeacherOptions", true);
        }

        return "dashboard";
    }

}
