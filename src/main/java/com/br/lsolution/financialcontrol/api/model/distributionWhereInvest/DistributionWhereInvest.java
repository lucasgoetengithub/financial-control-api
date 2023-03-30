package com.br.lsolution.financialcontrol.api.model.distributionWhereInvest;

import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.jsonField.JsonDistributionWhereInvest;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class DistributionWhereInvest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer whereInvestId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    List<JsonDistributionWhereInvest> jsonDistributionWhereInvests;

    public static DistributionWhereInvest of(DistributionWhereInvestRequest request) {
        return DistributionWhereInvest.builder()
                .id(request.getId())
                .whereInvestId(request.getWhereInvest())
                .jsonDistributionWhereInvests(request.getJsonDistributionWhereInvests())
                .build();
    }
}
