package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.CategoriaRepo;
import com.jg.lojapw.repo.MarcaRepo;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
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
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Optional;

@Controller
public class ProdutoController {

    private static String imgPath = System.getProperty("user.dir")+"\\imgs\\";

    @Autowired
    private ProdutoRepo produtoRepo;

    @Autowired
    private MarcaRepo marcaRepo;

    @Autowired
    private CategoriaRepo categoriaRepo;

    @GetMapping("administrativo/produtos/cadastrar")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("listaCategoria", categoriaRepo.findAll());
        mv.addObject("listaMarca", marcaRepo.findAll());

        return mv;
    }

    @GetMapping("administrativo/produtos/listar")
    public ModelAndView listar(String busca){
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");

        if (busca != null && !busca.isEmpty()){

            mv.addObject("listaProduto", produtoRepo.findAllByDescricaoContainsOrMarcaNomeContainsOrCategoriaNomeContains(busca, busca, busca));
            mv.addObject("busca", busca);
        }

        List<Produto> produtos;
        String time;
        StopWatch sw = new StopWatch();

        sw.start();
        produtos = produtoRepo.findAll();
        mv.addObject("listaProduto", produtos);
        sw.stop();


        time = String.format("%.2f", sw.getTotalTimeSeconds());
        mv.addObject("time", time);
        mv.addObject("qntde", produtos.size());

        return mv;
    }

    @PostMapping("administrativo/produtos/salvar")
    public ModelAndView salvar(@Validated Produto produto, BindingResult res, @RequestParam("files") List<MultipartFile> arqs){
        if (res.hasErrors()){
            return cadastrar(produto);
        }

        produtoRepo.saveAndFlush(produto);

        try{
            List<String> nomeImgs = new ArrayList<String>();
            for (MultipartFile arq : arqs){
                if (!arq.isEmpty()){
                    String imgName = produto.getId()+"-"+nomeImgs.size()+ arq.getOriginalFilename();

                    byte[] bytes = arq.getBytes();
                    Path path = Paths.get(imgPath+imgName);

                    Files.write(path, bytes);

                    nomeImgs.add(imgName);
                }
            }
            produto.setNomeImgs(nomeImgs);
            produtoRepo.saveAndFlush(produto);
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

        return listar("");
    }

    @GetMapping("administrativo/produtos/mostrarImg/{img}")
    @ResponseBody
    public byte[] mostrarImg(@PathVariable("img") String img) throws IOException {

        File arqImg = new File(imgPath+img);

        if (img != null || img.trim().length() > 0){
            return Files.readAllBytes(arqImg.toPath());
        }
        return null;
    }

}
