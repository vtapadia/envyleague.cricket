package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.PersistentToken;
import com.envyleague.cricket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    PersistentToken findOneBySeries(String series);

    Long deleteByUser(User user);

}
