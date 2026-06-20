package com.sis414.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sis414.minimarket.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
