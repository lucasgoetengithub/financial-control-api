package com.br.lsolution.financialcontrol.api.service.whereinvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.config.exception.ValidationException;
import com.br.lsolution.financialcontrol.api.controller.whereInvest.util.InitialChargeWhereInvest;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestResponse;
import com.br.lsolution.financialcontrol.api.model.jsonField.JsonWhereInvest;
import com.br.lsolution.financialcontrol.api.model.user.Users;
import com.br.lsolution.financialcontrol.api.model.whereinvest.WhereInvest;
import com.br.lsolution.financialcontrol.api.repository.user.UserRepository;
import com.br.lsolution.financialcontrol.api.repository.whereinvest.WhereInvestRepository;
import com.br.lsolution.financialcontrol.api.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class WhereInvestService {
    @Autowired
    WhereInvestRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    InitialChargeWhereInvest initialChargeWhereInvest;


    public WhereInvestResponse findById(Integer id){
        validateInformedId(id);
        WhereInvestResponse response = WhereInvestResponse.of(repository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no WhereInvest for this user yet.")));

        return response;
    }

    public List<WhereInvestResponse> historyByUserId(Integer userId){
        List<Optional<WhereInvest>> optionalList = repository
                .findByUserId(userId);

        List<WhereInvestResponse> response = new ArrayList<>();
        optionalList.forEach(optionalObj -> {
            response.add(WhereInvestResponse.of(optionalObj.get()));
        });

        return response;
    }

    public List<WhereInvestResponse> findByUserId(Integer userId){
        validateInformedId(userId);
        List<Optional<WhereInvest>> optionalList = repository
                .findByUserId(userId);

        List<WhereInvestResponse> response = new ArrayList<>();

        if (optionalList.isEmpty()) {
            initialChargeWhereInvest.InitialChargeWhereInvest(userId);

            optionalList = repository
                    .findByUserId(userId);
        }

        LocalDate now = LocalDate.now();
        AtomicReference<WhereInvestResponse> whereInvestAnterior = new AtomicReference<>();
        optionalList.forEach(optionalObj -> {
            WhereInvestResponse whereInvestResponse = WhereInvestResponse.of(optionalObj.get());
            if ((whereInvestResponse.getDate().getMonth().equals(now.getMonth())) &&
                    (whereInvestResponse.getDate().getYear() == now.getYear())) {
                response.add(whereInvestResponse);
            } else {
                whereInvestAnterior.set(whereInvestResponse);
            }
        });

        if (whereInvestAnterior.get() != null && response.size() == 0) {
            WhereInvest newWhereInvest = new WhereInvest();
            WhereInvestResponse whereInvestResponseAnterior = whereInvestAnterior.get();
            newWhereInvest.setJsonWhereInvest(whereInvestResponseAnterior.getJson());
            newWhereInvest.setUserId(whereInvestResponseAnterior.getUserId());
            newWhereInvest.setReference(now);
            newWhereInvest.setAmount(whereInvestResponseAnterior.getAmount());

            repository.save(newWhereInvest);

            return newReturn(userId, now);
        }

        return response;
    }

    public List<WhereInvestResponse> findByUserIdAndReference(Integer userId, String date){

        LocalDate reference = verificaECompletaData(date);
        validateInformedId(userId);
        validateInformedReference(reference);
        List<Optional<WhereInvest>> optionalList = repository
                .findByUserIdAndReference(userId, reference);

        List<WhereInvestResponse> response = new ArrayList<>();

        optionalList.forEach(optionalObj -> {
            WhereInvestResponse whereInvestResponse = WhereInvestResponse.of(optionalObj.get());
            response.add(whereInvestResponse);
        });

        return response;
    }

    private LocalDate verificaECompletaData(String date) {
        LocalDate reference;
        if (date.length() == 10) {
            reference = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else {
            String aux;
            aux = date.substring(0,5);
            System.out.println(aux);
            if (date.substring(6,7).equals("-")) {
                aux = aux + "0" + date.substring(6,7);
            } else {
                aux = aux + date.substring(5,8);
            }

            if (date.length() == 8) {
                aux = aux + "0" + date.substring(7);
            } else if (date.length() == 9) {
                if (date.substring(7, 8).equals("-")) {
                    aux = aux + "0" + date.substring(8);
                } else {
                    aux = aux + date.substring(7, 8);
                }
            }
            System.out.println(aux);
            reference = LocalDate.parse(aux, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        return reference;
    }

    private List<WhereInvestResponse> newReturn(Integer userId, LocalDate now) {
        List<Optional<WhereInvest>> optionalList = repository
                .findByUserId(userId);

        List<WhereInvestResponse> response = new ArrayList<>();

        optionalList.forEach(optionalObj -> {
            WhereInvestResponse whereInvestResponse = WhereInvestResponse.of(optionalObj.get());
            if (whereInvestResponse.getDate().getMonth().equals(now.getMonth())) {
                response.add(whereInvestResponse);
            }

        });

        return response;
    }

    public SucessResponse save(WhereInvestRequest request, Integer userId){
        Users users = userService.findById(userId);
        WhereInvest whereInvest = WhereInvest.of(request);
        LocalDate saveDate = LocalDate.of(whereInvest.getReference().getYear(), whereInvest.getReference().getMonth(), 1);
        whereInvest.setReference(saveDate);
        repository.save(whereInvest);
        userRepository.save(users);
        return SucessResponse.create("The WhereInvest was created for this user.");
    }

    public SucessResponse updateAmount(WhereInvestRequest request){
        WhereInvest whereInvest  = repository.findByIdAndUserId(request.getId(), request.getUserId())
                .orElseThrow(() -> new ValidationException("There's no WhereInvest for this Id and User."));

        whereInvest.setAmount(request.getAmount());

        repository.save(whereInvest);

        return SucessResponse.create("The WhereInvest was updated for this user.");
    }

    public SucessResponse update(WhereInvestRequest request){
        WhereInvest whereInvest  = repository.findByIdAndUserId(request.getId(), request.getUserId())
                .orElseThrow(() -> new ValidationException("There's no WhereInvest for this Id and User."));

        repository.save(buildWhereInvestUpdate(whereInvest, request));

        return SucessResponse.create("The WhereInvest was updated for this user.");
    }

    private WhereInvest buildWhereInvestUpdate(WhereInvest whereInvest, WhereInvestRequest request){

        whereInvest.setJsonWhereInvest(request.getJsonWhereInvest());

        return whereInvest;
    }

    public SucessResponse delete(Integer id, Integer whereInvestId){
        Optional<WhereInvest> optionalWhereInvest = repository.findById(whereInvestId);
        if (optionalWhereInvest.isPresent()) {

            WhereInvest whereInvest = optionalWhereInvest.get();

            ObjectMapper mapper = new ObjectMapper();

            List<JsonWhereInvest> investListResult = new ArrayList<>();

            List<JsonWhereInvest> investListData = mapper.convertValue(
                    whereInvest.getJsonWhereInvest(),
                    new TypeReference<List<JsonWhereInvest>>(){}
            );

            investListData.forEach(jsonWhereInvest -> {
                if (!jsonWhereInvest.getId().equals(id)) {
                    investListResult.add(jsonWhereInvest);
                }
            });

            whereInvest.setJsonWhereInvest(investListResult);

            repository.save(whereInvest);

        }

        return SucessResponse.create("The WhereInvest for this User was deleted.");
    }

    private void validateInformedId(Integer id){
        if (isEmpty(id)) {
            throw new ValidationException("The User id must be informed.");
        }
    }

    private void validateInformedReference(LocalDate reference){
        if (isEmpty(reference)) {
            throw new ValidationException("The reference must be informed.");
        }
    }
}
