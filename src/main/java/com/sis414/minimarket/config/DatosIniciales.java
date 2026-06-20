package com.sis414.minimarket.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sis414.minimarket.model.Cliente;
import com.sis414.minimarket.model.Producto;
import com.sis414.minimarket.repository.ClienteRepository;
import com.sis414.minimarket.repository.ProductoRepository;

@Component
public class DatosIniciales implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public DatosIniciales(ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            Cliente cliente = new Cliente();
            cliente.setNombre("Cliente General");
            cliente.setNit("0");
            cliente.setTelefono("70000000");
            clienteRepository.save(cliente);
        }

        if (productoRepository.count() == 0) {
            crearProducto("ARR-001", "Arroz 1 kg", "Bolsa de arroz popular", "8.50", 40);
            crearProducto("ACE-001", "Aceite 900 ml", "Aceite vegetal", "12.00", 25);
            crearProducto("LEC-001", "Leche 1 litro", "Leche entera", "7.00", 30);
        }
    }

    private void crearProducto(String codigo, String nombre, String descripcion, String precio, int stock) {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(new BigDecimal(precio));
        producto.setStock(stock);
        productoRepository.save(producto);
    }
}
