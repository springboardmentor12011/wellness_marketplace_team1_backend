package com.wellness.backend.integration.openfda;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenFdaService {

    private final WebClient webClient;

    @Value("${openfda.api-key}")
    private String apiKey;

    public OpenFdaService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Get drug warnings using drug label API
     */
    public Mono<String> getDrugWarnings(String drugName) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/label.json")
                        .queryParam("api_key", apiKey)
                        .queryParam(
                                "search",
                                "openfda.generic_name:" + drugName.toLowerCase()
                        )
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Search drug adverse events
     */
    public Mono<String> searchDrugEvents(String searchQuery) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/drug/event.json")
                        .queryParam("api_key", apiKey)
                        .queryParam("search", searchQuery)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);
    }
}
