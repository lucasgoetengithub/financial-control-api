package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonDistributionWhereInvest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionWhereInvestRequest {

    private Integer id;
    private Integer whereInvest;
    List<JsonDistributionWhereInvest> jsonDistributionWhereInvests;
}
