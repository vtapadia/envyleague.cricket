package com.envyleague.cricket.repository;


import com.envyleague.cricket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);

    User findOneByEmail(String email);

    User findOneByFacebookUserId(String facebookUserId);
}
