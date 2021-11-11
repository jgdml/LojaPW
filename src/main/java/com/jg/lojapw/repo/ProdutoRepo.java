package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import java.util.List;

@Repository
public interface ProdutoRepo extends JpaRepository<Produto, Long> {

    List<Produto> findAllByDescricaoContainsOrMarcaNomeContainsOrCategoriaNomeContains(String desc, String marca, String cat);
    @Transactional
    List<Produto> deleteByDescricao(String descricao);
}
