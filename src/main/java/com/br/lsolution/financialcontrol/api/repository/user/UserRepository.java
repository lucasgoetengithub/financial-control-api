package com.br.lsolution.financialcontrol.api.repository.user;

import com.br.lsolution.financialcontrol.api.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    void deleteByEmail(String email);

}
