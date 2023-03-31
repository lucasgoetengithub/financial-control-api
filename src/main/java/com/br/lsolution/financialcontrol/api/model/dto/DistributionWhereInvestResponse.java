package com.br.lsolution.financialcontrol.api.model.dto;

import com.br.lsolution.financialcontrol.api.model.distributionWhereInvest.DistributionWhereInvest;
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
public class DistributionWhereInvestResponse {

    private Integer id;
    private Integer whereInvest;
    List<JsonDistributionWhereInvest> jsonDistributionWhereInvests;

    public static DistributionWhereInvestResponse of(DistributionWhereInvest distributionWhereInvest) {
        return DistributionWhereInvestResponse.builder()
                .id(distributionWhereInvest.getId())
                .whereInvest(distributionWhereInvest.getWhereInvestId())
                .jsonDistributionWhereInvests(distributionWhereInvest.getJsonDistributionWhereInvests())
                .build();
    }
}