package com.br.lsolution.financialcontrol.api.controller.user;

import com.br.lsolution.financialcontrol.api.config.exception.SucessReponse;
import com.br.lsolution.financialcontrol.api.model.dto.UserRequest;
import com.br.lsolution.financialcontrol.api.model.dto.UserResponse;
import com.br.lsolution.financialcontrol.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public UserResponse save(@RequestBody UserRequest request){
        return service.save(request);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("email/{email}")
    public ResponseEntity findByEmail(@PathVariable String email){
        try {
            UserResponse userResponse = service.findByEmail(email);
            return ResponseEntity.ok(userResponse);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public SucessReponse delete(@PathVariable String email){
        return service.delete(email);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("{id}")
    public UserResponse update(@RequestBody UserRequest request, @PathVariable Integer id){
        return service.update(request, id);
    }
}