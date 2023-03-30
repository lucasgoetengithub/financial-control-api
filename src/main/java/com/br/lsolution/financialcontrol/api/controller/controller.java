package com.br.lsolution.financialcontrol.api.controller;

import com.br.lsolution.financialcontrol.api.config.exception.SucessReponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/")
public class controller {

    @GetMapping
    public SucessReponse getState() {
        return SucessReponse.create("Success");
    }
}
