package com.example.demo.teams.service;

import com.example.demo.teams.dto.TeamResponseDto;
import java.util.List;

public interface TeamsService {
    List<TeamResponseDto> fetchTeams();

    List<TeamResponseDto> seedTeams();

    TeamResponseDto fetchRandomTeam();
}
