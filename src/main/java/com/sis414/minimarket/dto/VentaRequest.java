package com.sis414.minimarket.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VentaRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Valid
    @NotEmpty(message = "La venta debe tener al menos un producto")
    private List<DetalleVentaRequest> detalles = new ArrayList<DetalleVentaRequest>();

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<DetalleVentaRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaRequest> detalles) {
        this.detalles = detalles;
    }
}
