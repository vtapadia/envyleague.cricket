package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, String> {
    public League findOneByName(String name);

    public List<League> findByStatusInOrderByStatusDesc(Status... status);

    public List<League> findByOwnerOrderByStatusAsc(User owner);
}
