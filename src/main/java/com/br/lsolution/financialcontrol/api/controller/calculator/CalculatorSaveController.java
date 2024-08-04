package com.br.lsolution.financialcontrol.api.controller.calculator;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorFixa;
import com.br.lsolution.financialcontrol.api.model.calculator.CalculatorVariavel;
import com.br.lsolution.financialcontrol.api.service.calculator.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
public class CalculatorSaveController {

    @Autowired
    CalculatorService calculatorService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save-renda-fixa")
    public SucessResponse salvarCalculoRendaFixa(@RequestBody CalculatorFixa calculatorFixa){
        calculatorService.salvarCalculoRendaFixa(calculatorFixa);
        return SucessResponse.create("The CalculatorFixa was created for this user.");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save-renda-variavel")
    public SucessResponse salvarCalculoRendaVariavel(@RequestBody CalculatorVariavel calculatorVariavel){
        calculatorService.salvarCalculoRendaVariavel(calculatorVariavel);
        return SucessResponse.create("The calculatorVariavel was created for this user.");
    }
}