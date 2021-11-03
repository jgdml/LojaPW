package com.jg.lojapw.controller;

import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ProdutoRepo produtoRepo;

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("/index");

        mv.addObject("listaProdutos", produtoRepo.findAll());
        return mv;
    }

}
