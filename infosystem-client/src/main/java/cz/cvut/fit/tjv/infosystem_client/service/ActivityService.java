package cz.cvut.fit.tjv.infosystem_client.service;

import cz.cvut.fit.tjv.infosystem_client.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;


@Service
public class ActivityService {

    private final WebClient webClient;

    @Autowired
    public ActivityService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ActivityDTO> createActivityBySubjectId(ActivityDTO activity,
                                                       Integer subjectId,
                                                       String token) {
        return webClient.post()
                .uri("/subjects/{subjectId}/activities", subjectId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(activity)
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
                .bodyToMono(ActivityDTO.class);
    }

    public Mono<List<ActivityDTO>> getActivitiesBySubjectId(Integer subjectId, String token) {
        return webClient.get()
                .uri("/subjects/{subjectId}/activities", subjectId)
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
                                        new RuntimeException("Server side error: " + errBody)
                                )))
                .bodyToFlux(ActivityDTO.class).collectList();
    }
}


