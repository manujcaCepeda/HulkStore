package com.tienda.todo1.controllers;

import java.util.List;

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

import com.tienda.todo1.dto.response.VentaResponse;
import com.tienda.todo1.models.Venta;
import com.tienda.todo1.services.VentaService;

/**
 * The REST API for processing the incoming requests the Venta. 
 * HiSchool - Agenda Digital 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@RestController
@RequestMapping("/venta")
public class VentaController extends BaseController{

	final static Logger logger = LoggerFactory.getLogger(VentaController.class);

	private VentaService ventaService;

	@Autowired
	public VentaController(VentaService ventaService) {
		this.ventaService = ventaService;
	}

	/**
	 * API REST para crear una nueva venta
	 * @param model
	 * @param reqVenta
	 * @return
	 */
	@PostMapping(produces = "application/json")
	public VentaResponse saveVenta(ModelMap model, @RequestBody Venta reqVenta) {
		VentaResponse venta = null;
		try {
			venta = ventaService.crearVenta(reqVenta);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		return venta;
	}

	/**
	 * API REST para obtener todas las ventas
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public List<Venta> getVentas() {
		try {
			return ventaService.obtenerVentas();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	

}
