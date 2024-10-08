package com.br.lsolution.financialcontrol.api.controller.user;

import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.dto.*;
import com.br.lsolution.financialcontrol.api.model.user.UserRecoveryCode;
import com.br.lsolution.financialcontrol.api.model.user.Users;
import com.br.lsolution.financialcontrol.api.service.JwtService;
import com.br.lsolution.financialcontrol.api.service.user.UserRecoveryCodeService;
import com.br.lsolution.financialcontrol.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://financial-control-front.herokuapp.com"})
public class UserController {

    @Autowired
    private UserService service;

    private final JwtService jwtService;

    @Autowired
    private UserRecoveryCodeService userRecoveryCodeService;


    @PostMapping
    public UserResponse save(@RequestBody RegisterDTO request){
        return service.save(request);
    }

    @PostMapping("/existe")
    public ResponseEntity existeUsuario(@RequestBody UserRequest request){
        try {
            ResponseEntity response = service.existeUsuario(request.getEmail());
            return response;
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/email")
    public ResponseEntity findByEmail(@RequestBody UserRequest request){
        try {
            UserResponse userResponse = service.findByEmail(request.getEmail());
            return ResponseEntity.ok(userResponse);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar( @RequestBody UserRequest dto ) {
        try {
            Users usuario = service.autenticar(dto.getEmail(), dto.getPassword());
            String token = "Bearer " + jwtService.gerarToken(usuario);
            TokenDTO tokenDTO = new TokenDTO( usuario.getName(), token);
            return ResponseEntity.ok(tokenDTO);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public SucessResponse delete(@PathVariable String email){
        return service.delete(email);
    }


    @PutMapping("{id}")
    public UserResponse update(@RequestBody UserRequest request, @PathVariable Integer id){
        return service.update(request, id);
    }

    @PostMapping("/sentEmailRecovery")
    public String sentEmailRecovery(@RequestBody UserRecoveryCode userRecoveryCode ) {
        return userRecoveryCodeService.sentEmailRecovery(userRecoveryCode);
    }

    @PostMapping("/changePassword")
    public SucessResponse changePassword(@RequestBody RecoveryDTO recoveryDTO) {
        return userRecoveryCodeService.changePassword(recoveryDTO);
    }
}
