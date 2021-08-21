package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.ProdutoRepo;
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
public class ProdutoController {

    @Autowired
    private ProdutoRepo produtoRepo;

    @GetMapping("administrativo/produtos/cadastrar")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);

        return mv;
    }

    @GetMapping("administrativo/produtos/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProduto", produtoRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/produtos/salvar")
    public ModelAndView salvar(@Validated Produto produto, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(produto);
        }

        produtoRepo.saveAndFlush(produto);

        return cadastrar(new Produto());
    }

    @GetMapping("administrativo/produtos/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Produto> est = produtoRepo.findById(id);
        System.out.println(est.get().getId());
        return cadastrar(est.get());
    }

    @GetMapping("administrativo/produtos/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Produto> est = produtoRepo.findById(id);

        produtoRepo.delete(est.get());

        return listar();
    }

}
