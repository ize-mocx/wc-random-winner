package com.example.demo.teams;

import com.example.demo.config.OpenApiConfig;
import com.example.demo.teams.dto.TeamResponseDto;
import com.example.demo.teams.service.TeamsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Teams")
@SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
public class TeamsController {

    private final TeamsService teamService;

    public TeamsController(TeamsService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public List<TeamResponseDto> fetchTeams() {
        return teamService.fetchTeams();
    }

    @GetMapping("/teams/predict")
    public TeamResponseDto fetchRandomTeam() {
        return teamService.fetchRandomTeam();
    }

    @PostMapping("/teams/seed")
    public List<TeamResponseDto> seedTeams() {
        return teamService.seedTeams();
    }
}
