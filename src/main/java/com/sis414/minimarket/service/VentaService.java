package com.sis414.minimarket.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sis414.minimarket.dto.DetalleVentaRequest;
import com.sis414.minimarket.dto.DetalleVentaResponse;
import com.sis414.minimarket.dto.VentaRequest;
import com.sis414.minimarket.dto.VentaResponse;
import com.sis414.minimarket.model.Cliente;
import com.sis414.minimarket.model.DetalleVenta;
import com.sis414.minimarket.model.Producto;
import com.sis414.minimarket.model.Venta;
import com.sis414.minimarket.repository.ClienteRepository;
import com.sis414.minimarket.repository.ProductoRepository;
import com.sis414.minimarket.repository.VentaRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository,
                        ClienteRepository clienteRepository,
                        ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        List<VentaResponse> respuestas = new ArrayList<VentaResponse>();
        for (Venta venta : ventaRepository.findAll()) {
            respuestas.add(toResponse(venta));
        }
        return respuestas;
    }

    @Transactional(readOnly = true)
    public VentaResponse buscarPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada con id " + id));
        return toResponse(venta);
    }

    @Transactional
    public VentaResponse crear(VentaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con id " + request.getClienteId()));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVentaRequest item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con id " + item.getProductoId()));

            if (Boolean.FALSE.equals(producto.getActivo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto " + producto.getNombre() + " no esta activo");
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para " + producto.getNombre());
            }

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            venta.agregarDetalle(detalle);
            total = total.add(subtotal);
        }

        venta.setTotal(total);
        return toResponse(ventaRepository.save(venta));
    }

    private VentaResponse toResponse(Venta venta) {
        List<DetalleVentaResponse> detalles = new ArrayList<DetalleVentaResponse>();
        for (DetalleVenta detalle : venta.getDetalles()) {
            detalles.add(new DetalleVentaResponse(
                    detalle.getProducto().getId(),
                    detalle.getProducto().getNombre(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario(),
                    detalle.getSubtotal()
            ));
        }

        return new VentaResponse(
                venta.getId(),
                venta.getCliente().getId(),
                venta.getCliente().getNombre(),
                venta.getFecha(),
                venta.getTotal(),
                detalles
        );
    }
}
