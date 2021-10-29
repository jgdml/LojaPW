package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Papel;
import com.jg.lojapw.repo.PapelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PapelController {

    @Autowired
    private PapelRepo papelRepo;

    @GetMapping("administrativo/papeis/cadastrar")
    public ModelAndView cadastrar(Papel papel){
        ModelAndView mv = new ModelAndView("administrativo/papeis/cadastro");
        mv.addObject("papel", papel);

        return mv;
    }

    @GetMapping("administrativo/papeis/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/papeis/lista");
        mv.addObject("listaPapel", papelRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/papeis/salvar")
    public ModelAndView salvar(@Validated Papel papel, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(papel);
        }

        papelRepo.saveAndFlush(papel);

        return cadastrar(new Papel());
    }

    @GetMapping("administrativo/papeis/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Papel> papel = papelRepo.findById(id);
        System.out.println(papel.get().getId());
        return cadastrar(papel.get());
    }

    @GetMapping("administrativo/papeis/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Papel> papel = papelRepo.findById(id);

        papelRepo.delete(papel.get());

        return listar();
    }

}
