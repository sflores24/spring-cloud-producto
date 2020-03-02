package mx.com.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mx.com.springboot.app.productos.models.entities.Producto;
import mx.com.springboot.app.productos.models.services.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;

	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.findAll().stream().map(prod -> {
			prod.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return prod;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return producto;
	}
}
