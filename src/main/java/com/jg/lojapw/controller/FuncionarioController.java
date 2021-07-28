package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Funcionario;
import com.jg.lojapw.entity.repo.FuncionarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FuncionarioController {

    @Autowired
    FuncionarioRepo funcionarioRepo;

    @GetMapping("administrativo/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);

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

        funcionarioRepo.saveAndFlush(funcionario);

        return cadastrar(new Funcionario());
    }

}
