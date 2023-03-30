package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonWhereInvest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhereInvestRequest {

    private LocalDate date;
    private List<JsonWhereInvest> jsonWhereInvest;
    private Integer userId;
    private Integer id;
    private BigDecimal amount;

}
