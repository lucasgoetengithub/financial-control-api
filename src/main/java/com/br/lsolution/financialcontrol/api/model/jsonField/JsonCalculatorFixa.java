package com.br.lsolution.financialcontrol.api.model.jsonField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonCalculatorFixa {

    private Integer id;

    private String nome;

    private double percentage;

    private BigDecimal valorAtual;

    private BigDecimal rendimentoNoMes;

}
