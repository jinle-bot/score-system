package cz.spgsasoskladno.infosystem_client.service;

import cz.spgsasoskladno.infosystem_client.dto.RegisterRequest;
import cz.spgsasoskladno.infosystem_client.dto.SubjectDTO;
import cz.spgsasoskladno.infosystem_client.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class UserService {

    private final WebClient webClient;

    @Autowired
    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<UserDTO> addUser(RegisterRequest user, String token) {
        return webClient.post()
                .uri("/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class).
                                flatMap(errBody->Mono.error(
                                        new RuntimeException("Client side error: " + errBody))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody->Mono.error(
                                        new RuntimeException("Server side error: " + errBody)
                                )))
                .bodyToMono(UserDTO.class);
    }

    public Flux<UserDTO> getUsers(String token) {
        return webClient.get()
                .uri("/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            if ( response.statusCode() == HttpStatus.NO_CONTENT )
                                return Mono.empty();
                            return response.bodyToMono(String.class).
                                    flatMap(errBody->Mono.error(
                                            new RuntimeException("Client side error: " + errBody)));
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody->Mono.error(
                                        new RuntimeException("Server side error: " + errBody)
                                )))
                .bodyToFlux(UserDTO.class);
    }

    public Mono<UserDTO> getUserById(Integer userId, String token) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    public Mono<List<SubjectDTO>> getSubjectsByStudentId(Integer studentId, String token) {
        return webClient.get()
                .uri("/users/{id}/subjects", studentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            if ( response.statusCode() == HttpStatus.NO_CONTENT )
                                return Mono.empty();
                            return Mono.error(new RuntimeException("Client Side error"));
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody->Mono.error(
                                        new RuntimeException("Server side error: " + errBody))))
                .bodyToFlux(SubjectDTO.class).collectList();
    }

}
