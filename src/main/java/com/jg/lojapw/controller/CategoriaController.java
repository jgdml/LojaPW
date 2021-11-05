package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Categoria;
import com.jg.lojapw.repo.CategoriaRepo;

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
public class CategoriaController {

    @Autowired
    private CategoriaRepo categoriaRepo;

    @GetMapping("administrativo/categorias/cadastrar")
    public ModelAndView cadastrar(Categoria categoria){
        ModelAndView mv = new ModelAndView("administrativo/categorias/cadastro");
        mv.addObject("categoria", categoria);

        return mv;
    }

    @GetMapping("administrativo/categorias/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/categorias/lista");
        mv.addObject("listaCategoria", categoriaRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/categorias/salvar")
    public ModelAndView salvar(@Validated Categoria categoria, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(categoria);
        }

        categoriaRepo.saveAndFlush(categoria);

        return cadastrar(new Categoria());
    }

    @GetMapping("administrativo/categorias/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Categoria> cat = categoriaRepo.findById(id);
        System.out.println(cat.get().getId());
        return cadastrar(cat.get());
    }

    @GetMapping("administrativo/categorias/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Categoria> cat = categoriaRepo.findById(id);

        categoriaRepo.delete(cat.get());

        return listar();
    }

}
