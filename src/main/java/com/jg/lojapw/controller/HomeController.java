package com.jg.lojapw.controller;

import com.jg.lojapw.repo.CidadeRepo;
import com.jg.lojapw.repo.FuncionarioRepo;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private ProdutoRepo produtoRepo;

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private CidadeRepo cidadeRepo;

    @GetMapping("/administrativo")
    public ModelAndView acessarHome(){
        ModelAndView mv = new ModelAndView("administrativo/home");

        mv.addObject("prod" ,produtoRepo.count());
        mv.addObject("func" ,funcionarioRepo.count());
        mv.addObject("cid" ,cidadeRepo.count());


        return mv;
    }
}
