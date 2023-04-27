package com.br.lsolution.financialcontrol.api.controller.whereInvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessReponse;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestResponse;
import com.br.lsolution.financialcontrol.api.service.whereinvest.WhereInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/whereInvest")
@CrossOrigin(origins = {"http://localhost:3000", "https://financial-control-front.herokuapp.com"})
public class WhereInvestController {

    @Autowired
    private WhereInvestService service;

    @PostMapping("/{userId}")
    public SucessReponse save(@RequestBody WhereInvestRequest request, @PathVariable Integer userId){
        return service.save(request, userId);
    }

    @DeleteMapping("/delete/{id}/{whereInvestId}")
    public SucessReponse delete(@PathVariable Integer id, @PathVariable Integer whereInvestId){
        return service.delete(id, whereInvestId);
    }

    @PutMapping("/update")
    public SucessReponse update(@RequestBody WhereInvestRequest request){
        return service.update(request);
    }

    @PutMapping("/updateAmount")
    public SucessReponse updateAmount(@RequestBody WhereInvestRequest request){
        return service.updateAmount(request);
    }

    @GetMapping("/{id}")
    public WhereInvestResponse findByIdResponse(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/allByUser/{userId}")
    public ResponseEntity<List<WhereInvestResponse>> findByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WhereInvestResponse>> historyByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(service.historyByUserId(userId));
    }

}

