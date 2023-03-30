package com.br.lsolution.financialcontrol.api.model.jsonField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonCalculatorVariavel {

    private Integer id;
    private String nome;
    private Integer mes;
    private BigDecimal dividendoPorAcao;
    private Integer quantidadeAcao;
    private BigDecimal valorAcao;
    private BigDecimal rendimentoNoMes;
}