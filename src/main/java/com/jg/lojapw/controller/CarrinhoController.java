package com.jg.lojapw.controller;

import com.jg.lojapw.entity.ItensCompra;
import com.jg.lojapw.entity.Produto;
import com.jg.lojapw.repo.ProdutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CarrinhoController {


    private List<ItensCompra> itensCompraList = new ArrayList<ItensCompra>();

    @Autowired
    private ProdutoRepo produtoRepo;


    @GetMapping("/carrinho")
    public ModelAndView carrinho()  {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        mv.addObject("listaItensCompra", itensCompraList);

        return mv;
    }

    @GetMapping("/addCarrinho/{id}")
    public ModelAndView addCarrinho(@PathVariable Long id)  {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        ItensCompra itensCompra = new ItensCompra();
        Optional<Produto> produtoOptional = produtoRepo.findById(id);
        Produto produto = produtoOptional.get();

        itensCompra.setProduto(produto);
        itensCompra.setValorProduto(produto.getValorVenda());
        itensCompra.setQuantidade(itensCompra.getQuantidade()+1);
        itensCompra.setValorTotal(itensCompra.getValorProduto() * itensCompra.getQuantidade());
        itensCompraList.add(itensCompra);

        mv.addObject("listaItensCompra", itensCompraList);

        return mv;
    }

}
