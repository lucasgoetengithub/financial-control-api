package com.br.lsolution.financialcontrol.api.whereinvest;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonWhereInvest;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestRequest;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class WhereInvest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDate reference;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<JsonWhereInvest> jsonWhereInvest;

    private Integer userId;

    BigDecimal amount;

    public static WhereInvest of(WhereInvestRequest request) {
        return WhereInvest.builder()
                .jsonWhereInvest(request.getJsonWhereInvest())
                .reference(request.getDate())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .build();
    }
}