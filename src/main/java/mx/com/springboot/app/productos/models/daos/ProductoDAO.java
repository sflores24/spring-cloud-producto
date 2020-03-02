package mx.com.springboot.app.productos.models.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.springboot.app.productos.models.entities.Producto;

public interface ProductoDAO extends JpaRepository<Producto, Long> {

}
