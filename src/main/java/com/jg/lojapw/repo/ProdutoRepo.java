package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProdutoRepo extends JpaRepository<Produto, Long> {

    @Transactional
    List<Produto> deleteByDescricao(String descricao);
}
