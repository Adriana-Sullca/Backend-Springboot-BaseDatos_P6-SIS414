package com.sis414.minimarket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VentaResponse {

    private Long id;
    private Long clienteId;
    private String cliente;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<DetalleVentaResponse> detalles;

    public VentaResponse(Long id, Long clienteId, String cliente, LocalDateTime fecha,
                         BigDecimal total, List<DetalleVentaResponse> detalles) {
        this.id = id;
        this.clienteId = clienteId;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<DetalleVentaResponse> getDetalles() {
        return detalles;
    }
}
