package com.br.lsolution.financialcontrol.api.repository.calculator;

import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorVariavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorVariavelRepository extends JpaRepository<CalculatorVariavel, Integer> {
}
