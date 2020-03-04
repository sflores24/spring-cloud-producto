package mx.com.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.com.springboot.app.commons.models.entities.Producto;
import mx.com.springboot.app.productos.models.services.IProductoService;

@RestController
public class ProductoController {

	@Autowired
	private Environment env;

	@Value("{server.port}")
	private String port;

	@Autowired
	private IProductoService productoService;

	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.findAll().stream().map(prod -> {
			prod.setPort(Integer.parseInt(env.getProperty("local.server.port"))); // Con environment
			return prod;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws Exception {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));

//Ej 1 Hystrix
//		boolean ok = false;
//		if(ok == false) {
//			throw new Exception("Exception of test");
//		}

//Ej 2 hystrix para configuración de timeout
		// Thread.sleep(2000);

		return producto;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@PathVariable Long id, @RequestBody Producto producto) {
		Producto productoBD = productoService.findById(id);
			
		productoBD.setNombre(producto.getNombre());
		productoBD.setPrecio(producto.getPrecio());
		
		return productoService.save(productoBD);
	}
		
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
}
