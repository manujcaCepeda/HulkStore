package com.tienda.todo1.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.VentaResponse;
import com.tienda.todo1.models.DetalleVenta;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.models.Venta;
import com.tienda.todo1.repositories.ProductoRepository;
import com.tienda.todo1.repositories.UsuarioRepository;
import com.tienda.todo1.repositories.VentaRepository;

@RunWith(SpringRunner.class)
public class VentaServiceTest {

	@Autowired
	private VentaService ventaService;
	@MockBean
	private VentaRepository ventaRepository;
	@MockBean
	private ProductoRepository productoRepository;
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Mock
    private Venta venta;
	
	@TestConfiguration
    static class VentaServiceImplTestContextConfiguration {
        @Bean
        public VentaService employeeService() {
            return new VentaService();
        }
    }

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ventaService = new VentaService(ventaRepository, productoRepository, usuarioRepository);
	}
	
	private Producto findProducto() {
		Producto producto = new Producto();
		producto.setId(2);
		producto.setNombre("Supereroes one");
		producto.setDescripcion("Era Marvel de los Comics 1961-1978");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("url");
		return producto;
	}
	
	private Usuario findUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setNombres("Manuel");
		usuario.setApellidos("Cepeda");
		return usuario;
	}
	
	private Venta createVenta() {
		Venta venta = new Venta();
		venta.setNroDocumento("nroDocumento");
		venta.setFechaVenta(new Date());
		venta.setSubtotal(new BigDecimal(100));
		venta.setIva(new BigDecimal(12));
		venta.setTotal(new BigDecimal(112));
		venta.setUsuario(findUsuario());
		List<DetalleVenta> detalleList = new ArrayList<>();
		detalleList.add(createDetalleVenta());
		venta.setDetalleList(detalleList);
		return venta;
	}
	
	private DetalleVenta createDetalleVenta() {
		DetalleVenta detalleVenta = new DetalleVenta();
		detalleVenta.setProducto(findProducto());
		detalleVenta.setCantidad(2);
		detalleVenta.setSubtotal(new BigDecimal(100));
		detalleVenta.setTotal(new BigDecimal(200));
		return detalleVenta;
	}
	
	@Test
	public void whenValidName_thenVentaShouldBeFound() {
		BigDecimal total = new BigDecimal(112);
		venta = createVenta();
		when(productoRepository.findById(findProducto().getId())).thenReturn(findProducto());
		when(usuarioRepository.findById(findUsuario().getId())).thenReturn(findUsuario());
		when(ventaRepository.save(venta)).thenReturn(venta);
		VentaResponse savedVenta = ventaService.crearVenta(venta);
		// Assert
		assertThat(savedVenta.getTotal(), is(equalTo(total)));
	}
	
	
	@Test
	public void whenValidList_thenVentaShouldBeFound() {
		List<Venta> lista = new ArrayList<>();
		lista.add(createVenta());
		when(usuarioRepository.findById(findUsuario().getId())).thenReturn(findUsuario());
		when(ventaRepository.findAll()).thenReturn(lista);
		BodyListResponse<VentaResponse> getVentas = ventaService.obtenerVentas();
		// Assert
		assertThat(getVentas.getData().size(), is(equalTo(1)));
	}
	
	@Test
	public void whenValidList_thenVentaByUserShouldBeFound() {
		List<Venta> lista = new ArrayList<>();
		lista.add(createVenta());
		when(usuarioRepository.findById(findUsuario().getId())).thenReturn(findUsuario());
		when(ventaRepository.findByUsuarioId(1)).thenReturn(lista);
		BodyListResponse<VentaResponse> getVentas = ventaService.obtenerVentasPorIdUsuario(1);
		// Assert
		assertThat(getVentas.getData().size(), is(equalTo(1)));
	}
}
