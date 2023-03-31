package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonWhereInvest;
import com.br.lsolution.financialcontrol.api.model.whereinvest.WhereInvest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhereInvestResponse {
    private Integer id;
    private List<JsonWhereInvest> json;
    private LocalDate date;
    private Integer userId;
    private BigDecimal amount;

    public static WhereInvestResponse of(WhereInvest whereInvest) {
        return WhereInvestResponse.builder()
                .id(whereInvest.getId())
                .date(whereInvest.getReference())
                .json(whereInvest.getJsonWhereInvest())
                .userId(whereInvest.getUserId())
                .amount(whereInvest.getAmount())
                .build();
    }
}
