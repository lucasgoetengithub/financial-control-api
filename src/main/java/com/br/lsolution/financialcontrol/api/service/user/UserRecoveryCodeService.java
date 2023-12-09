package com.br.lsolution.financialcontrol.api.service.user;

import com.br.lsolution.financialcontrol.api.model.user.UserRecoveryCode;
import com.br.lsolution.financialcontrol.api.repository.user.UserRecoveryCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRecoveryCodeService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRecoveryCodeRepository repository;

    @Value("${support.mail}")
    private String supportMail;

    public String sentEmailRecovery(UserRecoveryCode requisicao) {
        try {
            salvarCodigo(requisicao);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Codigo para recuperação de senha!");
            message.setText("Utilize o código: " + requisicao.getCodigoRecuperacao() + " para recuperar sua senha.");
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
}
