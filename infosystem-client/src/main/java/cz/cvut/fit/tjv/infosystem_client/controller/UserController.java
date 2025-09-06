package cz.cvut.fit.tjv.infosystem_client.controller;

import cz.cvut.fit.tjv.infosystem_client.dto.RegisterRequest;
import cz.cvut.fit.tjv.infosystem_client.dto.UserDTO;
import cz.cvut.fit.tjv.infosystem_client.dto.UserSession;
import cz.cvut.fit.tjv.infosystem_client.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Mono<String> showUsers(HttpSession session, Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }
        if ( ! userSession.getUserType().equals("TEACHER") ) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<List<UserDTO>> usersMono = userService.getUsers(userSession.getToken()).collectList();
        return usersMono.map(
                        users -> {
                            model.addAttribute("users", users);
                            return "users";
                        }
                )
                .onErrorResume( ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error");
                });
    }

    @PostMapping
    public Mono<String> addUser(
            @ModelAttribute RegisterRequest registerRequest,
            HttpSession session,
            Model model) {
        UserSession userSession = (UserSession) session.getAttribute("user");
        if (userSession == null) {
            return Mono.just("redirect:/auth/login");
        }

        if ( ! userSession.getUserType().equals("TEACHER")) {
            return Mono.just("redirect:/dashboard");
        }

        Mono<UserDTO> retUserMono = userService.addUser(registerRequest, userSession.getToken());
        return retUserMono.map  ( retUser -> {
                    model.addAttribute("user", retUser);
                    return "addUser"; } )
                .onErrorResume(ex -> {
                    model.addAttribute("errMsg", ex.getMessage());
                    return Mono.just("error"); // Return error view if subject fetch fails
                });
    }

}
