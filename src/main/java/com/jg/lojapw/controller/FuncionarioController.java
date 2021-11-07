package com.jg.lojapw.controller;

import com.jg.lojapw.EmailSender;
import com.jg.lojapw.RandPass;
import com.jg.lojapw.entity.Funcionario;
import com.jg.lojapw.entity.SenhaToken;
import com.jg.lojapw.repo.CidadeRepo;
import com.jg.lojapw.repo.FuncionarioRepo;
import com.jg.lojapw.repo.SenhaTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private CidadeRepo cidadeRepo;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private SenhaTokenRepo senhaTokenRepo;

    @GetMapping("administrativo/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepo.findAll());

        return mv;
    }

    @GetMapping("administrativo/funcionarios/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
        mv.addObject("listaFuncionario", funcionarioRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/funcionarios/salvar")
    public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(funcionario);
        }

        funcionario.setSenha(new BCryptPasswordEncoder().encode(RandPass.getRandPass()));
        funcionarioRepo.saveAndFlush(funcionario);

        SenhaToken senhaToken = new SenhaToken();
        senhaToken.setDuracao(1440);
        senhaToken.setFuncionario(funcionario);
        senhaTokenRepo.saveAndFlush(senhaToken);

        emailSender.sendSetPassEmail(funcionario.getEmail(), URLEncoder.encode(senhaToken.getToken(), StandardCharsets.UTF_8));

        return cadastrar(new Funcionario());
    }

    @GetMapping("administrativo/funcionarios/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Funcionario> func = funcionarioRepo.findById(id);
        System.out.println(func.get().getId());
        return cadastrar(func.get());
    }

    @GetMapping("administrativo/funcionarios/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Funcionario> func = funcionarioRepo.findById(id);

        funcionarioRepo.delete(func.get());

        return listar();
    }

}
