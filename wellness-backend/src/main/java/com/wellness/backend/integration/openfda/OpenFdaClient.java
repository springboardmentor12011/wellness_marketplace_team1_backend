package com.wellness.backend.integration.openfda;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class OpenFdaClient {

    private final WebClient webClient;

    @Value("${openfda.api-key}")
    private String apiKey;

    public OpenFdaClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<OpenFdaResponse> searchDrug(String symptom) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/label.json")
                        .queryParam("api_key", apiKey)
                        .queryParam(
                                "search",
                                "indications_and_usage:" + symptom.toLowerCase()
                        )
                        .build()
                )
                .retrieve()
                .bodyToMono(OpenFdaResponse.class)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(ex -> Mono.empty());
    }
}
