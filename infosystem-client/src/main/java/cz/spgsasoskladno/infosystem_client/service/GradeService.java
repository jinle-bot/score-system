package cz.spgsasoskladno.infosystem_client.service;

import cz.spgsasoskladno.infosystem_client.dto.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;


@Service
public class GradeService {

    private final WebClient webClient;

    @Autowired
    public GradeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<GradeDTO>> getGradesBySubjectIdAndStudentId(Integer subjectId, Integer StudentId, String token) {
        return webClient.get()
                .uri("/subjects/{subjectId}/{studentId}/grades", subjectId, StudentId)
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
                .bodyToFlux(GradeDTO.class).collectList();
    }

    public Mono<List<GradeDTO>> getGradesBySubjectIdAndActivityId(Integer subjectId, Integer activityId, String token) {
        return webClient.get()
                .uri("/subjects/{subjectId}/activities/{activityId}/grades", subjectId, activityId)
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
                .bodyToFlux(GradeDTO.class).collectList();

    }

    public Mono<GradeDTO> createGradeBySubjectIdAndActivityId(GradeDTO grade,
                                                       Integer subjectId,
                                                       Integer activityId,
                                                       String token) {
        return webClient.post()
                .uri("/subjects/{subjectId}/activities/{activityId}/grades", subjectId, activityId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(grade)
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
                .bodyToMono(GradeDTO.class);
    }

}
