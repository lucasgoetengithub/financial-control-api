package com.br.lsolution.financialcontrol.api.model.calculator;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonCalculatorFixa;
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
public class CalculatorFixa {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer meses;

    private double porcentagem;

    private String nome;

    private BigDecimal aporteMensal;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<JsonCalculatorFixa> jsonCalculatorFixas;

}