package com.jg.lojapw.controller;

import com.jg.lojapw.EmailSender;
import com.jg.lojapw.entity.Funcionario;
import com.jg.lojapw.entity.SenhaToken;
import com.jg.lojapw.repo.FuncionarioRepo;
import com.jg.lojapw.repo.SenhaTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private SenhaTokenRepo senhaTokenRepo;



    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("/login");

        return mv;
    }

    @GetMapping("/login/sendEmail")
    public ModelAndView sendEmailPage() {
        ModelAndView mv = new ModelAndView("sendEmail");

        return mv;
    }

    @PostMapping("/login/sendEmail")
    public ModelAndView sendEmail(String email){
        ModelAndView mv = new ModelAndView("sendEmail");

        Funcionario func = funcionarioRepo.findByEmail(email);

        if (func == null){
            mv.addObject("err", "Email não cadastrado.");
            return mv;
        }

        SenhaToken senhaToken = new SenhaToken();
        senhaToken.setDuracao(10);
        senhaToken.setFuncionario(func);
        senhaTokenRepo.saveAndFlush(senhaToken);

        try{
            emailSender.sendChangePassEmail(email, URLEncoder.encode(senhaToken.getToken(), StandardCharsets.UTF_8));

            mv.addObject("msg", "Email enviado.");
        }
        catch (Exception err){
            err.printStackTrace();

            mv.addObject("err", "Ocorreu um erro ao enviar o email.");
        }

        return mv;
    }


    @RequestMapping("/login/setPass")
    public ModelAndView setPass(String token, String senha, String senha2){
        ModelAndView mv = new ModelAndView("setPass");
        mv.addObject("token", token);

        SenhaToken senhaToken = senhaTokenRepo.findByToken(URLDecoder.decode(token, StandardCharsets.UTF_8));

        if (senhaToken == null || senhaToken.isExpired()){
            mv.addObject("crash", "Token inválido");
            return mv;
        }

        if (senha != null){

            if (!senha.equals(senha2)){
                mv.addObject("err", "As senhas são diferentes");
                return mv;
            }

            Funcionario func = senhaToken.getFuncionario();

            try{
                func.setSenha(new BCryptPasswordEncoder().encode(senha));
                funcionarioRepo.saveAndFlush(func);
                senhaTokenRepo.delete(senhaToken);
                mv.addObject("msg", "Senha alterada com sucesso");
            }

            catch (Exception e){
                mv.addObject("err", "Ocorreu um erro ao alterar a senha");
            }

        }



        return mv;
    }
}
