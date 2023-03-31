package com.br.lsolution.financialcontrol.api.service.user;

import com.br.lsolution.financialcontrol.api.config.exception.SucessReponse;
import com.br.lsolution.financialcontrol.api.config.exception.ValidationException;
import com.br.lsolution.financialcontrol.api.model.dto.UserRequest;
import com.br.lsolution.financialcontrol.api.model.dto.UserResponse;
import com.br.lsolution.financialcontrol.api.model.user.Users;
import com.br.lsolution.financialcontrol.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    UserRepository repository;

    public UserResponse findByIdReponse(Integer id){
        return UserResponse.of(findById(id));
    }

    public Users findById(Integer id){
        validateInformedId(id);

        return repository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no User for the given ID."));
    }

    public UserResponse findByEmail(String email){
        if (isEmpty(email)){
            throw new ValidationException("The user email must be informed.");
        }

        Users users = repository
                .findByEmail(email)
                .orElseThrow(() -> new ValidationException("There's no User for the given Email."));

        return UserResponse.of(users);
    }

    @Transactional
    public UserResponse save(UserRequest request){
        validateRequestSave(request);
        Users users = Users.of(request, pe.encode(request.getPassword()));
        users.setPerfis(new HashSet<>());
        request.getPerfis().forEach(x -> {
            users.addPerfilEnum(x);
        });

        repository.save(users);
        return UserResponse.of(users);
    }

    @Transactional
    public UserResponse update(UserRequest request,
                               Integer id){

        Users users = repository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no User for the given Email."));

        repository.save(buildUserUpdate(users, request));
        return UserResponse.of(users);
    }

    @Transactional
    private Users buildUserUpdate(Users users, UserRequest request){
        if (request.getName() != null && !users.getName().equals(request.getName())) {
            users.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().equals(users.getEmail())) {
            users.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().equals(users.getPassword())) {
            users.setPassword(request.getPassword());
        }

        if (request.getAmount() != null && !request.getAmount().equals(users.getAmount())) {
            users.setAmount(request.getAmount());
        }

        return users;
    }

    private void validateRequestSave(UserRequest request){
        if (isEmpty(request.getName())) {
            throw new ValidationException("The User's Name was not informed.");
        }

        if (isEmpty(request.getEmail())) {
            throw new ValidationException("The User's Email was not informed.");
        }

        if (isEmpty(request.getPassword())) {
            throw new ValidationException("The User's Password was not informed.");
        }
    }

    @Transactional
    public SucessReponse delete(String email){
        validateInformedEmail(email);
        if (!repository.existsByEmail(email)) {
            throw new ValidationException("The User does not exists.");
        }

        repository.deleteByEmail(email);
        return SucessReponse.create("The User was deleted.");
    }

    private void validateInformedEmail(String email){
        if (isEmpty(email)) {
            throw new ValidationException("The User email must be informed.");
        }
    }

    private void validateInformedId(Integer id){
        if (isEmpty(id)) {
            throw new ValidationException("The User id must be informed.");
        }
    }
}
