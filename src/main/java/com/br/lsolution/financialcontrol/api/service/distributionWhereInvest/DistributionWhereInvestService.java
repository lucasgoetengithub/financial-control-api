package com.br.lsolution.financialcontrol.api.service.distributionWhereInvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.config.exception.ValidationException;
import com.br.lsolution.financialcontrol.api.model.distributionWhereInvest.DistributionWhereInvest;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestResponse;
import com.br.lsolution.financialcontrol.api.model.dto.TotalExpensesAndInvestResponse;
import com.br.lsolution.financialcontrol.api.model.jsonField.JsonDistributionWhereInvest;
import com.br.lsolution.financialcontrol.api.model.user.Users;
import com.br.lsolution.financialcontrol.api.model.whereinvest.WhereInvest;
import com.br.lsolution.financialcontrol.api.repository.distributionWhereInvest.DistributionWhereInvestRepository;
import com.br.lsolution.financialcontrol.api.repository.user.UserRepository;
import com.br.lsolution.financialcontrol.api.repository.whereinvest.WhereInvestRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class DistributionWhereInvestService {

    @Autowired
    DistributionWhereInvestRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WhereInvestRepository whereInvestRepository;

    public ArrayList<TotalExpensesAndInvestResponse> getArrayListOfExpensesAndInvestments(String userEmail){
        Users usersOptional = userRepository.findByEmail(userEmail);
        ArrayList<TotalExpensesAndInvestResponse> listResposta = new ArrayList<>();
        if (usersOptional != null) {
            Users user = usersOptional;

            List<Optional<WhereInvest>> optionalList = whereInvestRepository
                    .findByUserId(user.getId());

            optionalList.forEach(optionalObj -> {
                if (optionalObj.isPresent()) {
                    WhereInvest whereInvest =  optionalObj.get();
                    Optional<DistributionWhereInvest> optionalDistributionWhereInvest = repository
                            .findByWhereInvestId(whereInvest.getId());

                    if (optionalDistributionWhereInvest.isPresent()) {

                        DistributionWhereInvest distributionWhereInvest = optionalDistributionWhereInvest.get();

                        AtomicReference<BigDecimal> despesas = new AtomicReference<>(BigDecimal.ZERO);
                        AtomicReference<BigDecimal> investimentos = new AtomicReference<>(BigDecimal.ZERO);

                        ObjectMapper mapper = new ObjectMapper();
                        List<JsonDistributionWhereInvest> investListData = mapper.convertValue(
                                distributionWhereInvest.getJsonDistributionWhereInvests(),
                                new TypeReference<List<JsonDistributionWhereInvest>>(){}
                        );

                        investListData.forEach(jsonDistributionWhereInvest -> {
                            if (jsonDistributionWhereInvest.getInvestExpense().equals("Despesa")) {
                                despesas.set(despesas.get().add(jsonDistributionWhereInvest.getAmountUsed()));
                            } else {
                                investimentos.set(investimentos.get().add(jsonDistributionWhereInvest.getAmountUsed()));
                            }
                        });

                        TotalExpensesAndInvestResponse totalExpensesAndInvestResponse = new TotalExpensesAndInvestResponse();
                        totalExpensesAndInvestResponse.setReference(whereInvest.getReference());
                        totalExpensesAndInvestResponse.setExpenses(despesas.get());
                        totalExpensesAndInvestResponse.setInvestments(investimentos.get());
                        totalExpensesAndInvestResponse.setAmount(whereInvest.getAmount());

                        listResposta.add(totalExpensesAndInvestResponse);
                    }

                }
            });
        }

        return listResposta;
    }

    public DistributionWhereInvestResponse findByWhereInvestId(Integer whereInvestId){
        validateInformedId(whereInvestId);
        Optional<DistributionWhereInvest> optionalDistributionWhereInvest = repository
                .findByWhereInvestId(whereInvestId);

        DistributionWhereInvest distributionWhereInvest = null;
        DistributionWhereInvestResponse response = null;
        if (optionalDistributionWhereInvest.isPresent()) {
            distributionWhereInvest = optionalDistributionWhereInvest.get();
        }

        if (distributionWhereInvest != null) {
            response = DistributionWhereInvestResponse.of(distributionWhereInvest);
        }

        return response;
    }

    public SucessResponse save(DistributionWhereInvestRequest request){
        DistributionWhereInvest distributionWhereInvest = DistributionWhereInvest.of(request);
        repository.save(distributionWhereInvest);
        return SucessResponse.create("The DistributionWhereInvest was created for this user.");
    }

    public SucessResponse update(DistributionWhereInvestRequest request,
                                 Integer id){

        DistributionWhereInvest distributionWhereInvest = repository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no DistributionWhereInvest for this User."));

        repository.save(buildDistributionWhereInvestUpdate(distributionWhereInvest, request));
        return SucessResponse.create("The WhereInvest was updated for this user.");
    }

    private DistributionWhereInvest buildDistributionWhereInvestUpdate(DistributionWhereInvest distributionWhereInvest, DistributionWhereInvestRequest request){
        List<JsonDistributionWhereInvest> investList = new ArrayList<>();
        investList.addAll(request.getJsonDistributionWhereInvests());

        ObjectMapper mapper = new ObjectMapper();
        List<JsonDistributionWhereInvest> investListData = mapper.convertValue(
                distributionWhereInvest.getJsonDistributionWhereInvests(),
                new TypeReference<List<JsonDistributionWhereInvest>>(){}
        );

        investListData.forEach(item -> {
            if (!existeDistributionWhereInvest(investList, item.getId(), item.getIdWhereInvestAllocation())) {
                investList.add(item);
            }
        });

        distributionWhereInvest.setJsonDistributionWhereInvests(investList);

        return distributionWhereInvest;
    }

    private boolean existeDistributionWhereInvest(List<JsonDistributionWhereInvest> list, Integer id, Integer idWhereInvestAllocation) {
        AtomicBoolean existe = new AtomicBoolean(false);
        list.forEach(e -> {
            if (e.getId().equals(id) && e.getIdWhereInvestAllocation().equals(idWhereInvestAllocation)) {
                existe.set(true);
            }
        });

        return existe.get();
    }

    @Transactional
    public SucessResponse delete(Integer focusedId, Integer whereInvestIdFocused, Integer whereInvestId){
        validateInformedId(focusedId);
        validateInformedId(whereInvestIdFocused);
        validateInformedId(whereInvestId);

        Optional<DistributionWhereInvest> optionalDistributionWhereInvest = repository.findByWhereInvestId(whereInvestId);
        if (optionalDistributionWhereInvest.isPresent()) {

            List<JsonDistributionWhereInvest> investList = new ArrayList<>();

            DistributionWhereInvest distributionWhereInvest = optionalDistributionWhereInvest.get();

            ObjectMapper mapper = new ObjectMapper();
            List<JsonDistributionWhereInvest> investListData = mapper.convertValue(
                    distributionWhereInvest.getJsonDistributionWhereInvests(),
                    new TypeReference<List<JsonDistributionWhereInvest>>(){}
            );

            investListData.forEach(jsonDistributionWhereInvest -> {
                if (!(jsonDistributionWhereInvest.getIdWhereInvestAllocation().equals(whereInvestIdFocused) && jsonDistributionWhereInvest.getId().equals(focusedId))) {
                    investList.add(jsonDistributionWhereInvest);
                }
            });

            distributionWhereInvest.setJsonDistributionWhereInvests(investList);

            repository.save(distributionWhereInvest);

            return SucessResponse.create("The DistributionWhereInvest for this User was deleted.");
        } else {
            return SucessResponse.create("The DistributionWhereInvest don't exist for this User.");
        }
    }

    private void validateInformedId(Integer id){
        if (isEmpty(id)) {
            throw new ValidationException("The User id must be informed.");
        }
    }
}
