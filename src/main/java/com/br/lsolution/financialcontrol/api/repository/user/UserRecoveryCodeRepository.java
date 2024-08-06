package com.br.lsolution.financialcontrol.api.repository.user;

import com.br.lsolution.financialcontrol.api.model.user.UserRecoveryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecoveryCodeRepository extends JpaRepository<UserRecoveryCode, Integer> {

    @Query(value = "SELECT * FROM users_recovery WHERE user_email = :email ORDER BY data_criacao DESC LIMIT 1", nativeQuery = true)
    UserRecoveryCode findLatestRecoveryCode(@Param("email") String email);

    @Query("DELETE FROM UserRecoveryCode urc WHERE urc.userEmail = :email")
    void deleteAllFromEmail(@Param("email") String email);
}
