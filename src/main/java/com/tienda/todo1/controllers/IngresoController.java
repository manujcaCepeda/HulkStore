package com.tienda.todo1.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.IngresoResponse;
import com.tienda.todo1.models.Ingreso;
import com.tienda.todo1.services.IngresoService;

/**
 * The REST API for processing the incoming requests the Ingreso. 
 * HiSchool - Agenda Digital 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@RestController
@RequestMapping("/ingreso")
public class IngresoController extends BaseController{

	final static Logger logger = LoggerFactory.getLogger(IngresoController.class);

	private IngresoService ingresoService;

	@Autowired
	public IngresoController(IngresoService ingresoService) {
		this.ingresoService = ingresoService;
	}

	/**
	 * API REST para crear un nuevo ingreso
	 * @param model
	 * @param reqIngreso
	 * @return
	 */
	@PostMapping(produces = "application/json")
	public IngresoResponse saveIngreso(ModelMap model, @RequestBody Ingreso reqIngreso) {
		IngresoResponse ingreso = null;
		try {
			ingreso = ingresoService.crearIngreso(reqIngreso);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		return ingreso;
	}

	/**
	 * API REST para obtener todos los ingresos
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public BodyListResponse<IngresoResponse> getIngresos() {
		try {
			return ingresoService.obtenerIngresos();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
	/**
	 * API REST para eliminar un ingreso
	 * @return
	 */
	@RequestMapping(path="/{id}", produces="application/json")
	public void eliminarPaquete(@PathVariable(value="id") Integer codigo){
		try {
			ingresoService.eliminar(codigo);
		} catch (DataIntegrityViolationException e) {
			logger.info("Error en el consumo del servicio eliminarPaquete : " +e.getMessage());
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

}
