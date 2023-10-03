package com.github.edulook.look.clients;

import com.github.edulook.look.responses.CourseResponse;
import com.github.edulook.look.responses.ListOfCourses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class GoogleClassroomClient {

    private final WebClient webClient;

    public GoogleClassroomClient(WebClient.Builder builder) {
        webClient = builder.baseUrl("https://classroom.googleapis.com").build();
    }


    public Mono<CourseResponse> findCourseInfoById(String id) {
        log.info("Buscando o curso com o id [{}]", id);
        return webClient.get()
                .uri("/v1/courses/{courseId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os parâmetros informados")))
                .bodyToMono(CourseResponse.class);
    }


    public Flux<ListOfCourses> getAllCourses (){
        log.info("Listando todos os cursos");
        return webClient
                .get()
                .uri("/v1/courses")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os parâmetros informados")))
                .bodyToFlux(ListOfCourses.class);
    }


}
