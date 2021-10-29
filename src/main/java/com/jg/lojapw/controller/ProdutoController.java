package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProdutoController {

    private static String imgPath = "G:\\Dev\\Code\\Java\\lojapw\\imgs\\";

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
    public ModelAndView salvar(@Validated Produto produto, BindingResult res, @RequestParam("file") MultipartFile arq){
        if (res.hasErrors()){
            return cadastrar(produto);
        }

        produtoRepo.saveAndFlush(produto);

        try{
            if (!arq.isEmpty()){
                byte[] bytes = arq.getBytes();
                Path path = Paths.get(imgPath+produto.getId()+arq.getOriginalFilename());

                Files.write(path, bytes);

                produto.setNomeImg(produto.getId()+arq.getOriginalFilename());
                produtoRepo.saveAndFlush(produto);
            }
        }
        catch (IOException err){
            err.printStackTrace();
        }



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

    @GetMapping("administrativo/produtos/mostrarImg/{img}")
    @ResponseBody
    public byte[] mostrarImg(@PathVariable("img") String img) throws IOException {

        System.out.println(img);
        File arqImg = new File(imgPath+img);

        if (img != null || img.trim().length() > 0){
            return Files.readAllBytes(arqImg.toPath());
        }
        return null;
    }

}
