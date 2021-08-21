package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Cidade;
import com.jg.lojapw.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepo extends JpaRepository<Cidade, Long> {


}
