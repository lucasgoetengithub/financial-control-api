package com.br.lsolution.financialcontrol.api.controller.distributionWhereInvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.DistributionWhereInvestResponse;
import com.br.lsolution.financialcontrol.api.model.dto.TotalExpensesAndInvestResponse;
import com.br.lsolution.financialcontrol.api.service.distributionWhereInvest.DistributionWhereInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/DistributionWhereInvest")
public class DistributionWhereInvestController {
    @Autowired
    private DistributionWhereInvestService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public SucessResponse save(@RequestBody DistributionWhereInvestRequest request){
        return service.save(request);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}/{focusedWhereInvestId}/{whereInvestId}")
    public SucessResponse delete(@PathVariable Integer id, @PathVariable Integer focusedWhereInvestId, @PathVariable Integer whereInvestId){
        return service.delete(id, focusedWhereInvestId, whereInvestId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update/{id}")
    public SucessResponse update(@PathVariable Integer id, @RequestBody DistributionWhereInvestRequest request){
        return service.update(request, id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{whereInvestId}")
    public DistributionWhereInvestResponse findByWhereInvestIdId(@PathVariable Integer whereInvestId){
        return service.findByWhereInvestId(whereInvestId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chart/{userEmail}")
    public ArrayList<TotalExpensesAndInvestResponse> getArrayListOfExpensesAndInvestments(@PathVariable String userEmail){
        return service.getArrayListOfExpensesAndInvestments(userEmail);
    }

}