package cz.spgsasoskladno.infosystem_client.controller;

import cz.spgsasoskladno.infosystem_client.dto.UserSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserSessionToModel(HttpSession session, Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");

        if (userSession != null) {
            model.addAttribute("userSession", userSession);
        }
    }
}

