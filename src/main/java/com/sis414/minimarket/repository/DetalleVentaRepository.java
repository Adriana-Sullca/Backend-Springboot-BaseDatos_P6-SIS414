package com.sis414.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sis414.minimarket.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
