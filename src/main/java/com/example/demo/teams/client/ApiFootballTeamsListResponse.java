package com.example.demo.teams.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiFootballTeamsListResponse(List<ApiFootballTeamResponse> response) {
}
