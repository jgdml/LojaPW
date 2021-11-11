package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Estado;
import com.jg.lojapw.entity.SenhaToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenhaTokenRepo extends JpaRepository<SenhaToken, Long> {

    SenhaToken findByToken(String token);

}
