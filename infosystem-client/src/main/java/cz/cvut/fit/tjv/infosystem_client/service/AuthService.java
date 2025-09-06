package cz.cvut.fit.tjv.infosystem_client.service;

import cz.cvut.fit.tjv.infosystem_client.dto.LoginRequest;
import cz.cvut.fit.tjv.infosystem_client.dto.RegisterRequest;
import cz.cvut.fit.tjv.infosystem_client.dto.UserDTO;
import cz.cvut.fit.tjv.infosystem_client.dto.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<UserSession> login(LoginRequest loginRequest) {
        return webClient.post()
                .uri("/auth/login")
                .bodyValue(loginRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody -> Mono.error(new RuntimeException("Client Side error: " + errBody))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody -> Mono.error(new RuntimeException("Server Side error: " + errBody))))
                .bodyToMono(UserSession.class) // UserSession with token, id, username and role
                .doOnSuccess(token -> System.out.println("Received token: " + token));
    }

    public Mono<UserDTO> register(RegisterRequest request) {
        return webClient.post()
                .uri("/auth/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

}

