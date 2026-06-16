package com.example.demo.teams.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeamResponseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("country")
    private String country;
}
