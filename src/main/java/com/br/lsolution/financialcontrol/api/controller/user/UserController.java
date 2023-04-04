package com.br.lsolution.financialcontrol.api.controller.user;

import com.br.lsolution.financialcontrol.api.config.exception.ErrorResponse;
import com.br.lsolution.financialcontrol.api.config.exception.SucessReponse;
import com.br.lsolution.financialcontrol.api.model.dto.TokenDTO;
import com.br.lsolution.financialcontrol.api.model.dto.UserRequest;
import com.br.lsolution.financialcontrol.api.model.dto.UserResponse;
import com.br.lsolution.financialcontrol.api.model.user.Users;
import com.br.lsolution.financialcontrol.api.service.JwtService;
import com.br.lsolution.financialcontrol.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;

    private final JwtService jwtService;

    @PostMapping
    public UserResponse save(@RequestBody UserRequest request){
        return service.save(request);
    }


    @GetMapping("email/{email}")
    public Response findByEmail(@PathVariable String email){
        try {
            UserResponse userResponse = service.findByEmail(email);
            return Response.ok(userResponse).build();
        }catch (Exception ex) {
            ErrorResponse errorResponse =  ErrorResponse.create(ex.getMessage(), HttpStatus.NOT_FOUND);
            return Response.ok(errorResponse).build();
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar( @RequestBody UserRequest dto ) {
        try {
            Users usuario = service.autenticar(dto.getEmail(), dto.getPassword());
            String token = jwtService.gerarToken(usuario);
            TokenDTO tokenDTO = new TokenDTO( usuario.getName(), token);
            return ResponseEntity.ok(tokenDTO);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public SucessReponse delete(@PathVariable String email){
        return service.delete(email);
    }


    @PutMapping("{id}")
    public UserResponse update(@RequestBody UserRequest request, @PathVariable Integer id){
        return service.update(request, id);
    }
}
