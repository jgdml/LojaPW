package com.jg.lojapw.repo;

import com.jg.lojapw.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

    public List<Cliente> findClienteByEmail(String email);

}
