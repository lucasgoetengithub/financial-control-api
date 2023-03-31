package com.br.lsolution.financialcontrol.api.repository.distributionWhereInvest;

import com.br.lsolution.financialcontrol.api.model.distributionWhereInvest.DistributionWhereInvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistributionWhereInvestRepository extends JpaRepository<DistributionWhereInvest, Integer> {

    Optional<DistributionWhereInvest> findByWhereInvestId(Integer whereInvestId);

    void deleteByIdAndWhereInvestId(Integer integer, Integer whereInvestId);
}

