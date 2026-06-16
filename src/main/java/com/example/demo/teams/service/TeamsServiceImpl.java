package com.example.demo.teams.service;

import com.example.demo.teams.dto.TeamResponseDto;
import com.example.demo.teams.entity.Team;
import com.example.demo.teams.repository.TeamsRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TeamsServiceImpl implements TeamsService {

    private static final List<String> WORLD_CUP_2026_TEAMS = List.of(
            // AFC
            "Japan", "Iran", "Uzbekistan", "Jordan", "South Korea", "Australia", "Qatar", "Saudi Arabia", "Iraq",
            // CAF
            "Morocco", "Tunisia", "Egypt", "Algeria", "Ghana", "Cape Verde", "Senegal", "South Africa",
            "Ivory Coast", "DR Congo",
            // CONCACAF
            "Panama", "Curacao", "Haiti", "Canada", "Mexico", "United States",
            // CONMEBOL
            "Argentina", "Brazil", "Ecuador", "Paraguay", "Uruguay", "Colombia",
            // OFC
            "New Zealand",
            // UEFA
            "England", "France", "Croatia", "Portugal", "Norway", "Germany", "Netherlands", "Switzerland",
            "Scotland", "Spain", "Austria", "Belgium", "Bosnia and Herzegovina", "Sweden", "Turkey", "Czech Republic"
    );

    private final TeamsRepository teamsRepository;

    public TeamsServiceImpl(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    @Override
    public List<TeamResponseDto> fetchTeams() {
        return teamsRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TeamResponseDto> seedTeams() {
        WORLD_CUP_2026_TEAMS.stream()
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
