package com.jg.lojapw.entity;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "estado")
public class EntradaItens implements Serializable {

    public EntradaItens() {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EntradaProduto entrada;
    @ManyToOne
    private Produto produto;

    private Double quantidade=0.;
    private Double valorProduto=0.;
    private Double valorVenda=0.;

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntradaProduto getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaProduto entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorPago) {
        this.valorProduto = valorPago;
    }
}
