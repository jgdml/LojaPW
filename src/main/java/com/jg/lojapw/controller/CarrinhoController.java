package com.jg.lojapw.controller;

import com.jg.lojapw.entity.Compra;
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

    private Compra compra = new Compra();

    @Autowired
    private ProdutoRepo produtoRepo;


    private void calcularTotal(){
        compra.setTotal(.0);
        for (ItensCompra it : itensCompraList){

            compra.setTotal(compra.getTotal()+it.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView carrinho()  {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        calcularTotal();

        mv.addObject("compra", compra);
        mv.addObject("listaItensCompra", itensCompraList);
        return mv;
    }


    @GetMapping("/finalizar")
    public ModelAndView finalizar()  {
        ModelAndView mv = new ModelAndView("cliente/finalizar");

        calcularTotal();

        mv.addObject("compra", compra);
        mv.addObject("listaItensCompra", itensCompraList);
        return mv;
    }


    @GetMapping("/removerProduto/{id}")
    public String removerProduto(@PathVariable Long id) {

        for (ItensCompra it : itensCompraList){
            if (it.getProduto().getId().equals(id)){
                itensCompraList.remove(it);

                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/alterarQuantidadeCarrinho/{id}/{acao}")
    public String alterarQuantidadeCarrinho(@PathVariable Long id, @PathVariable Integer acao)  {

        for (ItensCompra it : itensCompraList){
            if (it.getProduto().getId().equals(id)){
                Integer qntd = it.getQuantidade();

                if (acao.equals(1)){
                    if (qntd < 1001){
                        it.setQuantidade(qntd+1);
                    }
                }
                else if (acao.equals(0)){
                    if (qntd > 1){
                        it.setQuantidade(qntd-1);
                    }
                }
                it.setValorTotal(it.getValorProduto() * it.getQuantidade());

                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/addCarrinho/{id}")
    public String addCarrinho(@PathVariable Long id)  {

        ItensCompra itensCompra = new ItensCompra();
        Optional<Produto> produtoOptional = produtoRepo.findById(id);
        Produto produto = produtoOptional.get();


        Boolean produtoExiste = false;
        for (ItensCompra it : itensCompraList){
            if (it.getProduto().getId().equals(produto.getId())){
                it.setQuantidade(it.getQuantidade()+1);

                produtoExiste = true;
                break;
            }
        }

        if (!produtoExiste){
            itensCompra.setProduto(produto);
            itensCompra.setValorProduto(produto.getValorVenda());
            itensCompra.setQuantidade(itensCompra.getQuantidade()+1);
            itensCompra.setValorTotal(itensCompra.getValorProduto() * itensCompra.getQuantidade());
            itensCompraList.add(itensCompra);
        }


        return "redirect:/carrinho";
    }

}
