package com.br.lsolution.financialcontrol.api.repository.whereinvest;

import com.br.lsolution.financialcontrol.api.model.whereinvest.WhereInvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WhereInvestRepository extends JpaRepository<WhereInvest, Integer> {

    List<Optional<WhereInvest>> findByUserId(Integer userId);
    Optional<WhereInvest> findByIdAndUserId(Integer id, Integer userId);

    List<Optional<WhereInvest>> findByUserIdAndReference(Integer userId, LocalDate reference);
}
