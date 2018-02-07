package com.tienda.todo1.controllers;

import java.util.List;
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

import com.tienda.todo1.models.Categoria;
import com.tienda.todo1.services.CategoriaService;

/**
 * The REST API for processing the incoming requests the Categoria. 
 * HiSchool - Agenda Digital 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@RestController
@RequestMapping("/categoria")
public class CategoriaController extends BaseController{

	final static Logger logger = LoggerFactory.getLogger(CategoriaController.class);

	private CategoriaService categoriaService;

	@Autowired
	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	/**
	 * API REST para crear una categoria
	 * @param model
	 * @param reqCategria
	 * @return
	 */
	@PostMapping(produces = "application/json")
	public Categoria saveCategoria(ModelMap model, @RequestBody Categoria reqCategria) {
		Categoria categoria = null;
		try {
			categoria = categoriaService.crearCategoria(reqCategria);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		if(categoria == null) {
			throw new NoSuchElementException("Ya existe un categoria con código: " + reqCategria.getCodigo());
		}
		return categoria;
	}

	
	/**
	 * API REST para obtener todas las categorias
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public List<Categoria> getCategorias() {
		try {
			return categoriaService.obtenerCategorias();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
}
