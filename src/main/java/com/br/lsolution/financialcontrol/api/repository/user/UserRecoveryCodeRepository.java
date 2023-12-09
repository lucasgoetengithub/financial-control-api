package com.br.lsolution.financialcontrol.api.repository.user;

import com.br.lsolution.financialcontrol.api.model.user.UserRecoveryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecoveryCodeRepository extends JpaRepository<UserRecoveryCode, Integer> {
}
