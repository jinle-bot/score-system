package cz.spgsasoskladno.infosystem_client.controller;

import cz.spgsasoskladno.infosystem_client.dto.LoginRequest;
import cz.spgsasoskladno.infosystem_client.dto.UserSession;
import cz.spgsasoskladno.infosystem_client.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public Mono<String> login(@ModelAttribute LoginRequest loginRequest, HttpSession session, Model model) {
        System.out.println(session.getId());
        Mono<UserSession> userSessionMono = authService.login(loginRequest); // Calls WebClient to authenticate
        return userSessionMono.flatMap(userSession -> {session.setAttribute("user", userSession);
                                            return Mono.just("redirect:/dashboard");}
                            )
                .onErrorResume(ex->{ model.addAttribute("errMsg", ex.getMessage());
                                     return Mono.just("login");}
                                );
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Remove user session
        return "redirect:/auth/login";
    }


}
