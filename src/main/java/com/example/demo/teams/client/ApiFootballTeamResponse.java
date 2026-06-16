package com.example.demo.teams.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiFootballTeamResponse(Team team) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Team(Long id, String name, String country) {
    }
}
