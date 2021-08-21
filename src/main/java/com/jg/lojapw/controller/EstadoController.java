package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Estado;
import com.jg.lojapw.repo.EstadoRepo;
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
public class EstadoController {

    @Autowired
    private EstadoRepo estadoRepo;

    @GetMapping("administrativo/estados/cadastrar")
    public ModelAndView cadastrar(Estado estado){
        ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
        mv.addObject("estado", estado);

        return mv;
    }

    @GetMapping("administrativo/estados/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
        mv.addObject("listaEstado", estadoRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/estados/salvar")
    public ModelAndView salvar(@Validated Estado estado, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(estado);
        }

        estadoRepo.saveAndFlush(estado);

        return cadastrar(new Estado());
    }

    @GetMapping("administrativo/estados/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Estado> est = estadoRepo.findById(id);
        System.out.println(est.get().getId());
        return cadastrar(est.get());
    }

    @GetMapping("administrativo/estados/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Estado> est = estadoRepo.findById(id);

        estadoRepo.delete(est.get());

        return listar();
    }

}
