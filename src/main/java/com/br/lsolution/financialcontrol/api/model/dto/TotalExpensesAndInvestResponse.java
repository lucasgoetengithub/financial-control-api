package com.br.lsolution.financialcontrol.api.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TotalExpensesAndInvestResponse {

    private LocalDate reference;
    private BigDecimal amount;
    private BigDecimal expenses;
    private BigDecimal investments;

}
