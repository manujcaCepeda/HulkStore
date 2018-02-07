package com.tienda.todo1.controllers;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.ProductoResponse;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.services.ProductoService;

/**
 * The REST API for processing the incoming requests the Producto. 
 * HiSchool - Agenda Digital 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@RestController
@RequestMapping("/producto")
public class ProductoController extends BaseController{

	final static Logger logger = LoggerFactory.getLogger(ProductoController.class);

	private ProductoService productoService;

	@Autowired
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	/**
	 * API REST para crear un producto
	 * @param model
	 * @param reqProducto
	 * @return
	 */
	@PostMapping(produces = "application/json")
	public ProductoResponse saveProducto(ModelMap model, @RequestBody Producto reqProducto) {
		ProductoResponse producto = null;
		try {
			producto = productoService.crearProducto(reqProducto);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		if(producto == null) {
			throw new NoSuchElementException("Ya existe un producto con nombre: " + reqProducto.getNombre());
		}
		return producto;
	}

	/**
	 * API REST para obtener todos los productos
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public BodyListResponse<ProductoResponse> getProductos() {
		try {
			return productoService.obtenerProductos();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
	/*@GetMapping(value="/{correo}/{password}", produces = "application/json")
	public BodyResponse<ProductoResponse> getProductoPorCedulaPassword(@PathVariable("correo") String correo, @PathVariable("password") String password ) {
		try {
			return productoService.obtenerPorCorreoPassword(correo, password);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}catch (Exception ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Error verifique los datos ingresados: ");
		}
	}*/

}
