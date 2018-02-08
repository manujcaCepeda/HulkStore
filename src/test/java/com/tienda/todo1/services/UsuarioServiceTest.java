package com.tienda.todo1.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tienda.todo1.dto.response.BodyResponse;
import com.tienda.todo1.dto.response.UsuarioResponse;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.models.Rol;
import com.tienda.todo1.repositories.UsuarioRepository;
import com.tienda.todo1.repositories.RolRepository;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;
	@MockBean
	private UsuarioRepository usuarioRepository;
	@MockBean
	private RolRepository rolRepository;
	@Mock
    private Usuario user;
	
	@TestConfiguration
    static class UsuarioServiceImplTestContextConfiguration {
        @Bean
        public UsuarioService employeeService() {
            return new UsuarioService();
        }
    }
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		usuarioService = new UsuarioService(usuarioRepository, rolRepository);
		Mockito.when(rolRepository.findById(findRol().getId())).thenReturn(findRol());
	}
	
	private Rol findRol() {
		Rol rol = new Rol();
		rol.setId(1);
		rol.setRol("ROL_ADMIN");
		rol.setDescripcion("ROL ADMINISTRADOR");
		return rol;
	}
	
	
	private Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setApellidos("new Apellido");
		usuario.setNombres("new Nombre");
		usuario.setDireccion("direccion");
		usuario.setTelefono("0989345370");
		usuario.setCorreo("manujca@todo1.com");
		usuario.setRol(findRol());
		return usuario;
	}
	
	private Usuario findUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(2);
		usuario.setApellidos("Cepeda");
		usuario.setNombres("Manuel");
		usuario.setDireccion("direccion");
		usuario.setTelefono("0989345370");
		usuario.setCorreo("manujca2@todo1.com");
		return usuario;
	}
	
	
	@Test
	public void whenValidName_thenUsuarioShouldBeFound() {
		String name = "new Apellido";
		user = createUsuario();
		Usuario usuario = findUsuario();
		when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(usuario);
		when(usuarioRepository.save(user)).thenReturn(user);
		BodyResponse<UsuarioResponse> savedUsuario = usuarioService.crearUsuario(user);
		// Assert
		assertThat(savedUsuario.getData().getApellidos(), is(equalTo(name)));
	}
	
	@Test
	public void whenValidName_thenusuarioShouldNotFound() {
		user = createUsuario();
		when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(user);
		when(usuarioRepository.save(user)).thenReturn(user);
		BodyResponse<UsuarioResponse> savedUsuario = usuarioService.crearUsuario(user);
		// Assert
		assertThat(savedUsuario, is(equalTo(null)));
	}
	
	
	@Test
	public void whenValidList_thenUsuarioShouldBeFound() {
		List<Usuario> lista = new ArrayList<>();
		lista.add(createUsuario());
		when(usuarioRepository.findAll()).thenReturn(lista);
		List<Usuario> getUses = usuarioService.obtenerUsuarios();
		// Assert
		assertThat(getUses.size(), is(equalTo(1)));
	}
	
	
	@Test
	public void shouldReturnusuario_whenGetProductByCorroPasswordIsCalled() {
		String name = "new Apellido";
		user = createUsuario();
		when(usuarioRepository.findByCorreoAndPassword(user.getCorreo(), user.getPassword())).thenReturn(user);
		BodyResponse<UsuarioResponse> savedUsuario = usuarioService.obtenerPorCorreoPassword(user.getCorreo(), user.getPassword());
		// Assert
		assertThat(savedUsuario.getData().getApellidos(), is(equalTo(name)));
	}
}
