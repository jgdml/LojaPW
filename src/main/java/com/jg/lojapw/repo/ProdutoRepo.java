package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepo extends JpaRepository<Produto, Long> {


}
