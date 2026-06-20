package com.sis414.minimarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sis414.minimarket.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByActivoTrue();
}
