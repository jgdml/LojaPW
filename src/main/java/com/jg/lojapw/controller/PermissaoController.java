package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Permissao;
import com.jg.lojapw.repo.PapelRepo;
import com.jg.lojapw.repo.PermissaoRepo;
import com.jg.lojapw.repo.FuncionarioRepo;
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
public class PermissaoController {

    @Autowired
    private PermissaoRepo permissaoRepo;

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private PapelRepo papelRepo;

    @GetMapping("administrativo/permissoes/cadastrar")
    public ModelAndView cadastrar(Permissao permissao){
        ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
        mv.addObject("permissao", permissao);
        mv.addObject("listaFuncionario", funcionarioRepo.findAll());
        mv.addObject("listaPapel", papelRepo.findAll());

        return mv;
    }

    @GetMapping("administrativo/permissoes/listar")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
        mv.addObject("listaPermissao", permissaoRepo.findAll());

        return mv;
    }

    @PostMapping("administrativo/permissoes/salvar")
    public ModelAndView salvar(@Validated Permissao permissao, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(permissao);
        }

        permissaoRepo.saveAndFlush(permissao);

        return cadastrar(new Permissao());
    }

    @GetMapping("administrativo/permissoes/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Permissao> est = permissaoRepo.findById(id);
        System.out.println(est.get().getId());
        return cadastrar(est.get());
    }

    @GetMapping("administrativo/permissoes/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        Optional<Permissao> est = permissaoRepo.findById(id);

        permissaoRepo.delete(est.get());

        return listar();
    }

}
