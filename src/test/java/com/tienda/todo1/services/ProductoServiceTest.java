package com.tienda.todo1.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tienda.todo1.dto.response.ProductoResponse;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.repositories.CategoriaRepository;
import com.tienda.todo1.repositories.ProductoRepository;

@RunWith(SpringRunner.class)
public class ProductoServiceTest {

	@Autowired
	private ProductoService productoService;
	@MockBean
	private ProductoRepository productoRepository;
	@MockBean
	private CategoriaRepository categoriaRepository;
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ProductoService employeeService() {
            return new ProductoService();
        }
    }
	
	

	@Before
	public void setUp() {
		
		productoService = new ProductoService(productoRepository, categoriaRepository);
		
		Producto producto = createProducto();
		Mockito.when(productoRepository.findByNombre(producto.getNombre())).thenReturn(producto);
		Mockito.when(productoRepository.save(producto)).thenReturn(producto);
		
	}
	
	
	
	private Producto createProducto() {
		Producto producto = new Producto();
		producto.setId(1);
		producto.setNombre("Supereroes two");
		producto.setDescripcion("Era Marvel de los Comics 1961-1978");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("url");
		return producto;
	}
	
	private Producto findProducto() {
		Producto producto = new Producto();
		producto.setId(1);
		producto.setNombre("Supereroes one");
		producto.setDescripcion("Era Marvel de los Comics 1961-1978");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("url");
		return producto;
	}

	
	
	
	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
	    String name = "Supereroes two";
	    Producto producto = createProducto();
	    ProductoResponse found = productoService.crearProducto(producto);
	  
	     //assertThat(found.getNombre()).isEqualTo(name);
	 }
}
