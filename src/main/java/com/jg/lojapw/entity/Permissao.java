package com.jg.lojapw.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {

    public Permissao() {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dtCadastro = new Date();

    @ManyToOne
    private Funcionario funcionario;

    @ManyToOne
    private Papel papel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }
}
