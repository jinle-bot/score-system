package cz.spgsasoskladno.infosystem_client.service;

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
public class SubjectService {

    private final WebClient webClient;

    @Autowired
    public SubjectService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<SubjectDTO> getSubjects(String token) {
        return webClient.get()
                .uri("/subjects")
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
                .bodyToFlux(SubjectDTO.class);
    }

    public Mono<SubjectDTO> addSubject(SubjectDTO subjectDTO, String token) {
        return webClient.post()
                .uri("/subjects")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(subjectDTO)
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
                .bodyToMono(SubjectDTO.class);
    }

    public Mono<SubjectDTO> getSubjectById(Integer subjectId, String token) {
        return webClient.get()
                .uri("/subjects/{subjectId}", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                .bodyToMono(SubjectDTO.class);
    }

    public Mono<List<UserDTO>> getStudentsBySubjectId(Integer subjectId, String token) {
        return webClient.get()
                .uri("/subjects/{subjectId}/students", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            if ( response.statusCode() == HttpStatus.NO_CONTENT )
                                return Mono.empty();
                            return Mono.error(new RuntimeException("Failed to fetch activities for subject " + subjectId));
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errBody->Mono.error(
                                        new RuntimeException("Server side error: " + errBody))))
                .bodyToFlux(UserDTO.class).collectList();
    }

    public Mono<UserDTO> addStudentToSubject(  UserDTO student,
                                               Integer subjectId,
                                               String token) {
        return webClient.post()
                .uri("/subjects/{subjectId}/students", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(student)
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

}
