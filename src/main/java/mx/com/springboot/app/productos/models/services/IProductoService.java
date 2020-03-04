package mx.com.springboot.app.productos.models.services;

import java.util.List;

import mx.com.springboot.app.productos.models.entities.Producto;

public interface IProductoService {
	List<Producto> findAll();
	Producto findById(Long id);
	Producto save(Producto producto);
	void deleteById(Long id);
}
