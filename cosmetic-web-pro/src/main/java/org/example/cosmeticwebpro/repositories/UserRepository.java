package org.example.cosmeticwebpro.repositories;

import org.example.cosmeticwebpro.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameOrEmail(String username, String email);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    @Query(value =
            "select * from users\n" +
                    "where (LOWER(username LIKE LOWER(CONCAT('%', :username, '%'))  or " +
                    "LOWER(email) LIKE LOWER(CONCAT('%', :email, '%'))) and account_status = :status"
    , nativeQuery = true)
    Optional<User> findUserByUserNameOrEmail(String username, String email, String status);
}
