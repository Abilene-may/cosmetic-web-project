package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUserNameOrEmail(String username, String email);

    @Query(value =
            "SELECT * FROM users " +
                    "WHERE (LOWER(username) LIKE LOWER(CONCAT('%', :username, '%')) " +
                    "OR LOWER(email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
                    "AND account_status = :status",
            nativeQuery = true)
    Optional<User> findUserByUserNameOrEmail(String username, String email, String status);
}
