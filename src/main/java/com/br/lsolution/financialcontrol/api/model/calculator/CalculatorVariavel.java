package com.br.lsolution.financialcontrol.api.model.calculator;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonCalculatorVariavel;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
public class CalculatorVariavel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String  nome;

    private Integer acoesPorMes;

    private BigDecimal dividendoPorAcao;

    private BigDecimal valorDaAcao;

    private Integer userId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<JsonCalculatorVariavel> jsonCalculatorVariavel;
}
