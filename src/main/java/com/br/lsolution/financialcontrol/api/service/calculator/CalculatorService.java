package com.br.lsolution.financialcontrol.api.service.calculator;

import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorFixa;
import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorVariavel;
import com.br.lsolution.financialcontrol.api.repository.calculator.CalculatorFixaRepository;
import com.br.lsolution.financialcontrol.api.repository.calculator.CalculatorVariavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalculatorService {

    @Autowired
    CalculatorFixaRepository calculatorFixaRepository;

    @Autowired
    CalculatorVariavelRepository calculatorVariavelRepository;


    @Transactional
    public void salvarCalculoRendaFixa(CalculatorFixa calculatorFixa) {
        calculatorFixaRepository.save(calculatorFixa);
    }

    @Transactional
    public void salvarCalculoRendaVariavel(CalculatorVariavel calculatorVariavel) {
        calculatorVariavelRepository.save(calculatorVariavel);
    }
}