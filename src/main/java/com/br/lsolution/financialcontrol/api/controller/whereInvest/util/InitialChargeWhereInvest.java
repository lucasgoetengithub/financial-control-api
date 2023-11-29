package com.br.lsolution.financialcontrol.api.controller.whereInvest.util;

import com.br.lsolution.financialcontrol.api.model.jsonField.JsonWhereInvest;
import com.br.lsolution.financialcontrol.api.model.whereinvest.WhereInvest;
import com.br.lsolution.financialcontrol.api.repository.whereinvest.WhereInvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitialChargeWhereInvest {

    @Autowired
    WhereInvestRepository repository;

    public void InitialChargeWhereInvest(Integer userId) {
        WhereInvest whereInvest = new WhereInvest();
        whereInvest.setUserId(userId);
        whereInvest.setAmount(BigDecimal.ZERO);
        LocalDate now = LocalDate.now();
        LocalDate saveDate = LocalDate.of(now.getYear(), now.getMonth(), 1);
        whereInvest.setReference(saveDate);

        JsonWhereInvest json = new JsonWhereInvest();

        json.setId(1);
        json.setDescription("Gastos gerais");
        json.setPercentage(BigDecimal.ZERO);
        json.setMaxAmount(BigDecimal.ZERO);

        JsonWhereInvest json2 = new JsonWhereInvest();

        json2.setId(2);
        json2.setDescription("Educação");
        json2.setPercentage(BigDecimal.ZERO);
        json2.setMaxAmount(BigDecimal.ZERO);

        JsonWhereInvest json3 = new JsonWhereInvest();

        json3.setId(3);
        json3.setDescription("Lazer");
        json3.setPercentage(BigDecimal.ZERO);
        json3.setMaxAmount(BigDecimal.ZERO);

        JsonWhereInvest json4 = new JsonWhereInvest();

        json4.setId(4);
        json4.setDescription("Metas de curto prazo");
        json4.setPercentage(BigDecimal.ZERO);
        json4.setMaxAmount(BigDecimal.ZERO);

        JsonWhereInvest json5 = new JsonWhereInvest();

        json5.setId(5);
        json5.setDescription("Metas de médio prazo");
        json5.setPercentage(BigDecimal.ZERO);
        json5.setMaxAmount(BigDecimal.ZERO);

        JsonWhereInvest json6 = new JsonWhereInvest();

        json6.setId(6);
        json6.setDescription("Metas de longo prazo");
        json6.setPercentage(BigDecimal.ZERO);
        json6.setMaxAmount(BigDecimal.ZERO);

        List<JsonWhereInvest> investList = new ArrayList<>();

        investList.add(json);
        investList.add(json2);
        investList.add(json3);
        investList.add(json4);
        investList.add(json5);
        investList.add(json6);

        whereInvest.setJsonWhereInvest(investList);

        repository.save(whereInvest);
    }

}