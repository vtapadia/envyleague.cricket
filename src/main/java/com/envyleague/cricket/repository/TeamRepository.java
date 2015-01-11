package com.envyleague.cricket.repository;


import com.envyleague.cricket.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
}
