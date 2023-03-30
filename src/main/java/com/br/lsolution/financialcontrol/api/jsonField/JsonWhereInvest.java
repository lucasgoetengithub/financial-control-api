package com.br.lsolution.financialcontrol.api.jsonField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonWhereInvest implements Serializable {

    private Integer id;
    private String description;
    private BigDecimal percentage;
    private BigDecimal maxAmount;
}
