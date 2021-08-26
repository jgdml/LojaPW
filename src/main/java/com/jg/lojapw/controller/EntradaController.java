package com.jg.lojapw.controller;

import com.jg.lojapw.entity.EntradaItens;
import com.jg.lojapw.entity.EntradaProduto;
import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.EntradaItensRepo;
import com.jg.lojapw.repo.EntradaProdutoRepo;
import com.jg.lojapw.repo.FuncionarioRepo;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaController {

    private List<EntradaItens> listaEntradaItens = new ArrayList<EntradaItens>();

    @Autowired
    private EntradaProdutoRepo entradaProdutoRepo;

    @Autowired
    private EntradaItensRepo entradaItensRepo;

    @Autowired
    private FuncionarioRepo funcionarioRepo;

    @Autowired
    private ProdutoRepo produtoRepo;

    @GetMapping("administrativo/entrada/cadastrar")
    public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens){
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("listaEntradaItens", this.listaEntradaItens);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("listaFuncionarios", funcionarioRepo.findAll());
        mv.addObject("listaProdutos", produtoRepo.findAll());

        return mv;
    }
//
//    @GetMapping("administrativo/estados/listar")
//    public ModelAndView listar(){
//        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//        mv.addObject("listaEstado", estadoRepo.findAll());
//
//        return mv;
//    }

    @PostMapping("administrativo/entrada/salvar")
    public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens ){
        if (acao.equals("itens")){
            if (entradaItens.getProduto() != null
                && entradaItens.getQuantidade() != 0.0
                && entradaItens.getValorProduto() != 0.0
                && entradaItens.getValorVenda() != 0.0){
                this.listaEntradaItens.add(entradaItens);
            }
            else{
                System.out.println("Existem campos vazios");
                return cadastrar(entrada, entradaItens);
            }

        }
        else if (acao.equals("salvar")){
            entradaProdutoRepo.saveAndFlush(entrada);
            for (EntradaItens ei : this.listaEntradaItens){
                entradaItensRepo.saveAndFlush(ei);

                Optional<Produto> p = produtoRepo.findById(ei.getProduto().getId());

                Produto produto = p.get();

                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + ei.getQuantidade());
                produto.setValorVenda(ei.getValorVenda());
                produtoRepo.saveAndFlush(produto);

                this.listaEntradaItens = new ArrayList();

            }
            return cadastrar(new EntradaProduto(), new EntradaItens());
        }

        return cadastrar(entrada, new EntradaItens());
    }
//
//    @GetMapping("administrativo/estados/editar/{id}")
//    public ModelAndView editar(@PathVariable("id") Long id){
//        Optional<Estado> est = estadoRepo.findById(id);
//        System.out.println(est.get().getId());
//        return cadastrar(est.get());
//    }
//
//    @GetMapping("administrativo/estados/excluir/{id}")
//    public ModelAndView excluir(@PathVariable("id") Long id){
//        Optional<Estado> est = estadoRepo.findById(id);
//
//        estadoRepo.delete(est.get());
//
//        return listar();
//    }

}
