package com.br.lsolution.financialcontrol.api.model.jsonField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonDistributionWhereInvest implements Serializable {

    private Integer id;
    private Integer idWhereInvestAllocation;
    private String description;
    private BigDecimal amount;
    private BigDecimal amountUsed;
    private String investExpense;
}
