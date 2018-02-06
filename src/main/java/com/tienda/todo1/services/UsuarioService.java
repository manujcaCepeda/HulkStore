package com.tienda.todo1.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.todo1.dto.response.BodyResponse;
import com.tienda.todo1.dto.response.UsuarioResponse;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.repositories.RolRepository;
import com.tienda.todo1.repositories.UsuarioRepository;

/**
 * The class contain the business logic for the Usuario. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class UsuarioService {

	private static Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	private UsuarioRepository usuarioRepository;
	private RolRepository rolRepository;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
		this.usuarioRepository = usuarioRepository;
		this.rolRepository = rolRepository;
	}

	/**
	 * Método que permite transfomar de tipo Usuario a UsuarioResponse
	 * @param usuario
	 * @return
	 */
	private UsuarioResponse doUsusarioResponse(Usuario usuario) {
		UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getApellidos(), usuario.getNombres(),
				usuario.getDireccion(), usuario.getTelefono(), usuario.getCorreo());
		usuarioResponse.setRol(rolRepository.findById(usuario.getRol().getId()).getDescripcion());
		return usuarioResponse;
	}

	/**
	 * Método que obtiene todos los usuario de la bdd
	 * @return
	 */
	public List<Usuario> obtenerUsuarios() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	/**
	 * Método que permite crear un Usuario en la bdd
	 * @param usuario
	 * @return
	 */
	public UsuarioResponse crearUsuario(Usuario usuario) {
		usuario.setFecha(new Date());
		Usuario usuarioExiste = usuarioRepository.findByCorreo(usuario.getCorreo());
		if (usuarioExiste == null) {
			usuario = usuarioRepository.save(usuario);
			return doUsusarioResponse(usuario);
		}
		return null;
	}

	/**
	 * Método que obtiene un Usuario por correo y password
	 * @param correo
	 * @param password
	 * @return
	 */
	public BodyResponse<UsuarioResponse> obtenerPorCorreoPassword(String correo, String password){
		Usuario respuesta = usuarioRepository.findByCorreoAndPassword(correo, password);
		if(respuesta != null){
			 return new BodyResponse<>(doUsusarioResponse(respuesta));
		}
		return null;
	}

}
