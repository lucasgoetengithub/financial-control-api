package com.br.lsolution.financialcontrol.api.service.user;

import com.br.lsolution.financialcontrol.api.config.exception.BadRequestException;
import com.br.lsolution.financialcontrol.api.config.exception.SucessResponse;
import com.br.lsolution.financialcontrol.api.model.dto.RecoveryDTO;
import com.br.lsolution.financialcontrol.api.model.user.UserRecoveryCode;
import com.br.lsolution.financialcontrol.api.repository.user.UserRecoveryCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserRecoveryCodeService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRecoveryCodeRepository repository;

    @Value("${support.mail}")
    private String supportMail;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 4;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional(rollbackOn = Exception.class)
    public String sentEmailRecovery(UserRecoveryCode requisicao) {
        try {
            String codigo = getGeneratedCode();
            requisicao.setCodigoRecuperacao(codigo);
            requisicao.setExpirado(false);
            requisicao.setDataCriacao(LocalDateTime.now());
            requisicao.setUsado(false);
            salvarCodigo(requisicao);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Codigo para recuperação de senha!");
            message.setText("Utilize o código: " + getGeneratedCode() + " para recuperar sua senha.");
            message.setTo(requisicao.getUserEmail());
            message.setFrom("suportfinancialcontrol@outlook.com");

            try {
                mailSender.send(message);
                return "Email enviado com sucesso!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Erro ao enviar email.";
            }
        }catch (Exception e) {
            return "Erro ao enviar email.";
        }
    }

    private void salvarCodigo(UserRecoveryCode userRecoveryCode) {
        repository.save(userRecoveryCode);
    }

    private String getGeneratedCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex =  RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }

    public SucessResponse changePassword(RecoveryDTO recoveryDTO) {
        try {
            Optional<UserRecoveryCode> optionalUserRecoveryCode = repository.findLatestRecoveryCode(recoveryDTO.getEmail());
            if (optionalUserRecoveryCode.isPresent()) {
                UserRecoveryCode userRecoveryCode = optionalUserRecoveryCode.get();
                if (userRecoveryCode.getCodigoRecuperacao().equalsIgnoreCase(recoveryDTO.getCodigo())) {
                    String newPassword = encoder.encode(recoveryDTO.getPassword());
                    userService.changePassword(recoveryDTO.getEmail(), newPassword);
                    repository.deleteAllFromEmail(recoveryDTO.getEmail());
                } else {
                    throw new BadRequestException("O Codigo está incorreto ou inválido, verifique o codigo no seu email!");
                }
            }
        }
        catch (Exception e) {

        }

        return SucessResponse.create("A senha foi trocada com sucesso!");
    }
}
