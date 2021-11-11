package com.jg.lojapw.entity;


import com.jg.lojapw.RandPass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "senha_token")
public class SenhaToken {


    public SenhaToken() {
        super();
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    Funcionario funcionario;

    String token = createToken();
    Date dataCriada = new Date();

    Integer duracao;

    public boolean isExpired(){
        TimeUnit timeUnit = TimeUnit.MINUTES;
        duracao *= 100000;

        long diff = timeUnit.convert(dataCriada.getTime() - new Date().getTime(), TimeUnit.MINUTES);

        System.out.println(diff+duracao);

        if (diff+duracao < 0){
            return true;
        }

        return false;
    }


    private String createToken(){
        Date dt = new Date();
        String token = dt+RandPass.getRandPass();

        token = new BCryptPasswordEncoder().encode(token);

        return token;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDataCriada() {
        return dataCriada;
    }

    public void setDataCriada(Date dataCriada) {
        this.dataCriada = dataCriada;
    }
}
