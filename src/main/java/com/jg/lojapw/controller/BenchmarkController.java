package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BenchmarkController {

    @Autowired
    private ProdutoRepo produtoRepo;

    @GetMapping("administrativo/bench")
    public ModelAndView benchPage(){
        ModelAndView mv = new ModelAndView("administrativo/benchmark");
        return mv;
    }

    @RequestMapping("administrativo/bench/test")
    public ModelAndView test(Integer qntd){

        ModelAndView mv = new ModelAndView("administrativo/benchmark");
        StopWatch sw = new StopWatch();
        List<Produto> produtos = new ArrayList<>();

        sw.start();
        for (Integer i = 0; i < qntd; i++){
            Produto p = new Produto();
            p.setDescricao("Produto.De.Teste");
            produtos.add(p);
        }
        produtoRepo.saveAllAndFlush(produtos);
        sw.stop();

        mv.addObject("time", String.format("%.2f", sw.getTotalTimeSeconds()));
        mv.addObject("qntd", qntd);

        return mv;
    }


    @PostMapping("administrativo/bench/deleteTest")
    public ModelAndView deleteTestProduct(){
        ModelAndView mv = new ModelAndView("administrativo/benchmark");
        StopWatch sw = new StopWatch();

        sw.start();
        List<Produto> ids = produtoRepo.deleteByDescricao("Produto.De.Teste");
        sw.stop();

        mv.addObject("time", String.format("%.2f", sw.getTotalTimeSeconds()));
        mv.addObject("qntd", ids.size());

        return mv;
    }

}
