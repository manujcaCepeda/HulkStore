package com.tienda.todo1.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.tienda.todo1.dto.response.DetalleVentaResponse;
import com.tienda.todo1.models.DetalleVenta;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.repositories.DetalleVentaRepository;
import com.tienda.todo1.repositories.ProductoRepository;

@RunWith(SpringRunner.class)
public class DetalleVentaServiceTest {

	@Autowired
	private DetalleVentaService detalleVentaService;
	@MockBean
	private DetalleVentaRepository detalleVentaRepository;
	@MockBean
	private ProductoRepository productoRepository;

	
	@Mock
    private DetalleVenta detalleVenta;
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public DetalleVentaService employeeService() {
            return new DetalleVentaService();
        }
    }

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		detalleVentaService = new DetalleVentaService(detalleVentaRepository, productoRepository);
		Mockito.when(productoRepository.findById(findProducto().getId())).thenReturn(findProducto());
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
	
	private DetalleVenta createDetalleVenta() {
		DetalleVenta detalleVenta = new DetalleVenta();
		detalleVenta.setProducto(findProducto());
		detalleVenta.setCantidad(2);
		detalleVenta.setSubtotal(new BigDecimal(100));
		detalleVenta.setTotal(new BigDecimal(200));
		return detalleVenta;
	}
	
	@Test
	public void whenValidName_thenDetalleVentaShouldBeFound() {
		BigDecimal total = new BigDecimal(200);
		detalleVenta = createDetalleVenta();
		when(detalleVentaRepository.save(detalleVenta)).thenReturn(detalleVenta);
		DetalleVentaResponse savedDetalleVenta = detalleVentaService.crearDetalleVenta(detalleVenta);
		// Assert
		assertThat(savedDetalleVenta.getTotal(), is(equalTo(total)));
	}
	
	
	@Test
	public void whenValidList_thenDetalleVentaShouldBeFound() {
		List<DetalleVenta> lista = new ArrayList<>();
		lista.add(createDetalleVenta());
		when(detalleVentaRepository.findAll()).thenReturn(lista);
		List<DetalleVenta> getDetalleVentas = detalleVentaService.obtenerDetalleVentas();
		// Assert
		assertThat(getDetalleVentas.size(), is(equalTo(1)));
	}
}
