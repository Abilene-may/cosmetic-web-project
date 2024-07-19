package org.example.cosmeticwebpro.repositories;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.cosmeticwebpro.domains.User;
import org.example.cosmeticwebpro.models.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

  @Query(value =
      "SELECT * FROM users " +
          "WHERE LOWER(email) LIKE LOWER(CONCAT('%', :email, '%')) " +
          " OR LOWER(username) LIKE LOWER(CONCAT('%', :username, '%')) " +
          " AND account_status = :status",
      nativeQuery = true)
    Optional<User> findByUserNameOrEmailForAdmin(@Param("username") String username, @Param("email") String email);

  Optional<User> findByUserNameOrEmail( String username, String email);

    @Query(value =
            "SELECT * FROM users " +
                    "WHERE LOWER(email) LIKE LOWER(CONCAT('%', :email, '%')) " +
                    "AND account_status = :status",
            nativeQuery = true)
    Optional<User> findUserByEmail(String email, String status);


  @Query(
      value =
          "UPDATE users \n"
              + "SET role_id = 2 \n"
              + "WHERE role_id = :roleId ",
      nativeQuery = true)
  @Modifying
  @Transactional
  void updateRole(Long roleId);

  @Query(
      value =
          "select u.id as id,\n"
              + "       u.email as email,\n"
              + "       u.first_name as firstName,\n"
              + "       u.last_name as lastName,\n"
              + "       u.username as username,\n"
              + "       u.password as password,\n"
              + "       u.created_date as createdDate,\n"
              + "       u.modified_date as modifiedDate,\n"
              + "       u.account_status as accountStatus,\n"
              + "       u.request_date as requestDate,\n"
              + "       r.role_name as roleName\n"
              + "from users u join role r on u.role_id = r.id "
              + " order by u.created_date ",
      nativeQuery = true)
  List<UserDTO> findAllUsersForAdmin();

  @Transactional
  @Modifying
  @Query(value = " UPDATE users SET role_id = :roleId WHERE id = :userId "
      , nativeQuery = true
  )
  void changeRoleUserForAdmin(Long userId, Long roleId);
}
