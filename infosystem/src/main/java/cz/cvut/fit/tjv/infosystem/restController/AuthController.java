package cz.cvut.fit.tjv.infosystem.restController;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.infosystem.domain.User;
import cz.cvut.fit.tjv.infosystem.domain.View;
import cz.cvut.fit.tjv.infosystem.dto.LoginRequest;
import cz.cvut.fit.tjv.infosystem.dto.RegisterRequest;
import cz.cvut.fit.tjv.infosystem.dto.UserSession;
import cz.cvut.fit.tjv.infosystem.security.JwtUtil;
import cz.cvut.fit.tjv.infosystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiPaths.BASE_AUTH)
public class AuthController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(
            UserService userService,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil ) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<UserSession> login(@RequestBody LoginRequest request) {

        User user = (User) userDetailsService.loadUserByUsername(request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String jwtToken = jwtUtil.generateToken(user.getUsername());
        UserSession userSession = new UserSession ( user.getId(),
                                                    user.getUsername(),
                                                    user.getUserType().toString(),
                                                    jwtToken );

        return ResponseEntity.ok(userSession);
    }

    @PostMapping("/register")
    @JsonView(View.Summary.class)
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User registeredUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
