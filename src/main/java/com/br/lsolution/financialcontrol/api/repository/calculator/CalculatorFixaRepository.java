package com.br.lsolution.financialcontrol.api.repository.calculator;

import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorFixaRepository extends JpaRepository<CalculatorFixa, Integer> {
}
