package com.tienda.todo1.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.tienda.todo1.dto.response.BodyResponse;
import com.tienda.todo1.dto.response.UsuarioResponse;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.services.UsuarioService;

/**
 * The REST API for processing the incoming requests the Usuario. 
 * HiSchool - Agenda Digital 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController extends BaseController{

	final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping(produces = "application/json")
	public UsuarioResponse saveUsuario(ModelMap model, @RequestBody Usuario reqUser) {
		UsuarioResponse usuario = null;
		try {
			usuario = usuarioService.crearUsuario(reqUser);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		if(usuario == null) {
			throw new NoSuchElementException("Ya existe un usuario con email: " + reqUser.getCorreo());
		}
		return usuario;
	}

	@GetMapping(produces = "application/json")
	public List<Usuario> getUsuarios() {
		try {
			return usuarioService.obtenerUsuarios();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
	@GetMapping(value="/{correo}/{password}", produces = "application/json")
	public BodyResponse<UsuarioResponse> getUsuarioPorCedulaPassword(@PathVariable("correo") String correo, @PathVariable("password") String password ) {
		try {
			return usuarioService.obtenerPorCorreoPassword(correo, password);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}catch (Exception ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Error verifique los datos ingresados: ");
		}
	}

}
