package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Cidade;
import com.jg.lojapw.repo.CidadeRepo;
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
public class CidadeController {

    @Autowired
    private CidadeRepo cidadeRepo;

    @Autowired
    private EstadoRepo estadoRepo;

    @GetMapping("administrativo/cidades/cadastrar")
    public ModelAndView cadastrar(Cidade cidade){
        ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
        mv.addObject("cidade", cidade);
        mv.addObject("listaEstados", estadoRepo.findAll());

        return mv;
    }

    @GetMapping("administrativo/cidades/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
        mv.addObject("listaCidade", cidadeRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/cidades/salvar")
    public ModelAndView salvar(@Validated Cidade cidade, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(cidade);
        }

        cidadeRepo.saveAndFlush(cidade);

        return cadastrar(new Cidade());
    }

    @GetMapping("administrativo/cidades/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cidade> est = cidadeRepo.findById(id);
        System.out.println(est.get().getId());
        return cadastrar(est.get());
    }

    @GetMapping("administrativo/cidades/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Cidade> est = cidadeRepo.findById(id);

        cidadeRepo.delete(est.get());

        return listar();
    }

}
