package com.br.lsolution.financialcontrol.api.controller.whereInvest;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestRequest;
import com.br.lsolution.financialcontrol.api.model.dto.WhereInvestResponse;
import com.br.lsolution.financialcontrol.api.service.whereinvest.WhereInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value ="/whereInvest")
@CrossOrigin(origins = {"http://localhost:3000", "https://financial-control-front.herokuapp.com"})
public class WhereInvestController {

    @Autowired
    private WhereInvestService service;

    @PostMapping("/{userId}")
    public SucessResponse save(@RequestBody WhereInvestRequest request, @PathVariable Integer userId){
        return service.save(request, userId);
    }

    @DeleteMapping("/delete/{id}/{whereInvestId}")
    public SucessResponse delete(@PathVariable Integer id, @PathVariable Integer whereInvestId){
        return service.delete(id, whereInvestId);
    }

    @PutMapping("/update")
    public SucessResponse update(@RequestBody WhereInvestRequest request){
        return service.update(request);
    }

    @PutMapping("/updateAmount")
    public SucessResponse updateAmount(@RequestBody WhereInvestRequest request){
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

    @GetMapping("/allByUser/reference/{userId}/{reference}")
    public ResponseEntity<List<WhereInvestResponse>> findByUserIdAndReference(@PathVariable Integer userId,
                                                                              @PathVariable String reference) throws ParseException {


        return ResponseEntity.ok(service.findByUserIdAndReference(userId, reference));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WhereInvestResponse>> historyByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(service.historyByUserId(userId));
    }

}

