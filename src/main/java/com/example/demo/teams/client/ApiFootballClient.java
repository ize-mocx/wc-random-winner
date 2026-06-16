package com.example.demo.teams.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ApiFootballClient {

    private final RestClient restClient;

    public ApiFootballClient(
            @Value("${api-football.base-url}") String baseUrl,
            @Value("${api-football.key}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-apisports-key", apiKey)
                .build();
    }

    public List<ApiFootballTeamResponse> fetchTeams(int leagueId, int season) {
        ApiFootballTeamsListResponse body = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/teams")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .body(ApiFootballTeamsListResponse.class);

        return body == null || body.response() == null ? List.of() : body.response();
    }
}
