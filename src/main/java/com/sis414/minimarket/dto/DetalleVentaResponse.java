package com.sis414.minimarket.dto;

import java.math.BigDecimal;

public class DetalleVentaResponse {

    private Long productoId;
    private String producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleVentaResponse(Long productoId, String producto, Integer cantidad,
                                BigDecimal precioUnitario, BigDecimal subtotal) {
        this.productoId = productoId;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
