package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Marca;
import com.jg.lojapw.repo.MarcaRepo;
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
public class MarcaController {

    @Autowired
    private MarcaRepo marcaRepo;

    @GetMapping("administrativo/marcas/cadastrar")
    public ModelAndView cadastrar(Marca marca){
        ModelAndView mv = new ModelAndView("administrativo/marcas/cadastro");
        mv.addObject("marca", marca);

        return mv;
    }

    @GetMapping("administrativo/marcas/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/marcas/lista");
        mv.addObject("listaMarca", marcaRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/marcas/salvar")
    public ModelAndView salvar(@Validated Marca marca, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(marca);
        }

        marcaRepo.saveAndFlush(marca);

        return cadastrar(new Marca());
    }

    @GetMapping("administrativo/marcas/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Marca> mar = marcaRepo.findById(id);
        System.out.println(mar.get().getId());
        return cadastrar(mar.get());
    }

    @GetMapping("administrativo/marcas/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Marca> mar = marcaRepo.findById(id);

        marcaRepo.delete(mar.get());

        return listar();
    }

}
