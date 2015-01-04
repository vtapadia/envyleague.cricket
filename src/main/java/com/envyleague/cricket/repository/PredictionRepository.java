package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.PredictionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, PredictionKey> {
}
