package com.tienda.todo1.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.todo1.dto.response.ProductoResponse;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.repositories.CategoriaRepository;
import com.tienda.todo1.repositories.ProductoRepository;

/**
 * The class contain the business logic for the Producto. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class ProductoService {

//private static Logger log = LoggerFactory.getLogger(ProductoService.class);
	
	private ProductoRepository productoRepository;
	private CategoriaRepository categoriaRepository;

	@Autowired
	public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
		this.productoRepository = productoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	/**
	 * Método que permite transfomar de tipo Producto a ProductoResponse
	 * @param producto
	 * @return
	 */
	private ProductoResponse doUsusarioResponse(Producto producto) {
		ProductoResponse productoResponse = new ProductoResponse(producto.getNombre(), producto.getDescripcion(),
				producto.getFecha(), producto.getCantidad(), producto.getUrl(), producto.getPrecio());
		productoResponse.setCategoria(categoriaRepository.findByCodigo(producto.getCategoria().getCodigo()).getDescripcion());
		return productoResponse;
	}

	/**
	 * Método que obtiene todos los producto de la bdd
	 * @return
	 */
	public List<Producto> obtenerProductos() {
		return (List<Producto>) productoRepository.findAll();
	}

	/**
	 * Método que permite crear un Producto en la bdd
	 * @param producto
	 * @return
	 */
	public ProductoResponse crearProducto(Producto producto) {
		producto.setFecha(new Date());
		Producto productoExiste = productoRepository.findByNombre(producto.getNombre());
		if (productoExiste == null) {
			producto = productoRepository.save(producto);
			return doUsusarioResponse(producto);
		}
		return null;
	}

}
