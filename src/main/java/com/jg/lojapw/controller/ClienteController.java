package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Cliente;
import com.jg.lojapw.repo.CidadeRepo;
import com.jg.lojapw.repo.ClienteRepo;
import com.jg.lojapw.repo.EstadoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private CidadeRepo cidadeRepo;

    @GetMapping("cliente/cadastrar")
    public ModelAndView cadastrar(Cliente cliente){
        ModelAndView mv = new ModelAndView("/cliente/cadastrar");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidade", cidadeRepo.findAll());

        return mv;
    }


    @PostMapping("cliente/salvar")
    public ModelAndView salvar(@Validated Cliente cliente, BindingResult res){
        if (res.hasErrors()){
            return cadastrar(cliente);
        }

        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));

        clienteRepo.saveAndFlush(cliente);

        return cadastrar(new Cliente());
    }

    @GetMapping("cliente/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cliente> cli = clienteRepo.findById(id);
        System.out.println(cli.get().getId());
        return cadastrar(cli.get());
    }


}
