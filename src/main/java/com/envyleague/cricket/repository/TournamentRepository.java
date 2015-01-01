package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, String> {
    public Tournament findOneByStatus(Status status);
}
