package com.jg.lojapw;

import com.jg.lojapw.entity.SenhaToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendChangePassEmail(String email, String token){
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String link = baseUrl+"/login/setPass?token="+token;


        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Solicitação de alteração de senha");
        msg.setText("Foi solicitado a alteração da sua senha em LojaPW. \nClique no Link abaixo para redefini-la. \n\n" + link);

        javaMailSender.send(msg);
    }

    public void sendSetPassEmail(String email, String token){
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String link = baseUrl+"/login/setPass?token="+token;


        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Finalizar cadastro no sistema");

        msg.setText("Para finalizar seu cadastro e definir sua senha em LojaPW clique no link abaixo.\n\n"+link);

        javaMailSender.send(msg);
    }
}
