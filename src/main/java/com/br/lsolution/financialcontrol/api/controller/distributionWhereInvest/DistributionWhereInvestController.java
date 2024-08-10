package com.br.lsolution.financialcontrol.api.controller.distributionWhereInvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionCharDTO;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestResponse;
import com.br.lsolution.financialcontrol.api.model.dto.TotalExpensesAndInvestResponse;
import com.br.lsolution.financialcontrol.api.service.distributionWhereInvest.DistributionWhereInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/DistributionWhereInvest")
@CrossOrigin(origins = {"http://localhost:3000", "https://financial-control-front.herokuapp.com"})
public class DistributionWhereInvestController {
    @Autowired
    private DistributionWhereInvestService service;

    @PostMapping("/save")
    public SucessResponse save(@RequestBody DistributionWhereInvestRequest request){
        return service.save(request);
    }

    @DeleteMapping("/delete/{id}/{focusedWhereInvestId}/{whereInvestId}")
    public SucessResponse delete(@PathVariable Integer id, @PathVariable Integer focusedWhereInvestId, @PathVariable Integer whereInvestId){
        return service.delete(id, focusedWhereInvestId, whereInvestId);
    }

    @PutMapping("/update/{id}")
    public SucessResponse update(@PathVariable Integer id, @RequestBody DistributionWhereInvestRequest request){
        return service.update(request, id);
    }

    @GetMapping("/{whereInvestId}")
    public DistributionWhereInvestResponse findByWhereInvestIdId(@PathVariable Integer whereInvestId){
        return service.findByWhereInvestId(whereInvestId);
    }

    @PostMapping("/chart")
    public ArrayList<TotalExpensesAndInvestResponse> getArrayListOfExpensesAndInvestments(@RequestBody DistributionCharDTO distributionCharDTO){
        return service.getArrayListOfExpensesAndInvestments(distributionCharDTO.getEmail());
    }

}