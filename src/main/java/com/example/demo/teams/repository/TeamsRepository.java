package com.example.demo.teams.repository;

import com.example.demo.teams.entity.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamsRepository extends JpaRepository<Team, Long> {
    boolean existsByCountry(String country);

    @Query(value = "SELECT * FROM teams ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<Team> findRandomTeam();
}
