package com.example.demo.teams.service;

import com.example.demo.teams.client.ApiFootballClient;
import com.example.demo.teams.client.ApiFootballTeamResponse;
import com.example.demo.teams.dto.TeamResponseDto;
import com.example.demo.teams.entity.Team;
import com.example.demo.teams.repository.TeamsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;
    private final ApiFootballClient apiFootballClient;
    private final int worldCupLeagueId;
    private final int worldCupSeason;

    public TeamsServiceImpl(
            TeamsRepository teamsRepository,
            ApiFootballClient apiFootballClient,
            @Value("${api-football.world-cup.league-id}") int worldCupLeagueId,
            @Value("${api-football.world-cup.season}") int worldCupSeason) {
        this.teamsRepository = teamsRepository;
        this.apiFootballClient = apiFootballClient;
        this.worldCupLeagueId = worldCupLeagueId;
        this.worldCupSeason = worldCupSeason;
    }

    @Override
    public List<TeamResponseDto> fetchTeams() {
        return teamsRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TeamResponseDto> seedTeams() {
        apiFootballClient.fetchTeams(worldCupLeagueId, worldCupSeason).stream()
                .map(ApiFootballTeamResponse::team)
                .map(ApiFootballTeamResponse.Team::name)
                .filter(country -> !teamsRepository.existsByCountry(country))
                .map(this::newTeam)
                .forEach(teamsRepository::save);

        return fetchTeams();
    }

    @Override
    public TeamResponseDto fetchRandomTeam() {
        return teamsRepository.findRandomTeam()
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No teams found"));
    }

    private Team newTeam(String country) {
        Team team = new Team();
        team.setCountry(country);
        return team;
    }

    private TeamResponseDto toDto(Team team) {
        return TeamResponseDto.builder()
                .id(team.getId().toString())
                .country(team.getCountry())
                .build();
    }
}
