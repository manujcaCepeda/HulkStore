package com.tienda.todo1.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.ProductoResponse;
import com.tienda.todo1.models.Categoria;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.repositories.CategoriaRepository;
import com.tienda.todo1.repositories.ProductoRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class ProductoServiceTest {

	@Autowired
	private ProductoService productoService;
	@MockBean
	private ProductoRepository productoRepository;
	@MockBean
	private CategoriaRepository categoriaRepository;
	@Mock
    private Producto product;
	
	@TestConfiguration
    static class ProductoServiceImplTestContextConfiguration {
        @Bean
        public ProductoService employeeService() {
            return new ProductoService();
        }
    }
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		productoService = new ProductoService(productoRepository, categoriaRepository);
		Mockito.when(categoriaRepository.findByCodigo(findCategoria().getCodigo())).thenReturn(findCategoria());
	}

	private Categoria findCategoria() {
		Categoria categoria = new Categoria();
		categoria.setCodigo("CAMI");
		categoria.setDescripcion("Camisetas");
		return categoria;
	}
	
	private Producto createProducto() {
		Producto producto = new Producto();
		producto.setId(1);
		producto.setNombre("Supereroes two");
		producto.setDescripcion("Era Marvel de los Comics 1961-1978");
		producto.setCantidad(10);
		producto.setPrecio(new BigDecimal(10));
		producto.setUrl("url");
		producto.setCategoria(findCategoria());
		return producto;
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
	
	
	@Test
	public void whenValidName_thenProductShouldBeFound() {
		String name = "Supereroes two";
		product = createProducto();
		Producto producto = findProducto();
		when(productoRepository.findByNombre(producto.getNombre())).thenReturn(producto);
		when(productoRepository.save(product)).thenReturn(product);
		ProductoResponse savedProduct = productoService.crearProducto(product);
		// Assert
		assertThat(savedProduct.getNombre(), is(equalTo(name)));
	}
	
	
	@Test
	public void whenValidName_thenProductShouldNotFound() {
		product = createProducto();
		when(productoRepository.findByNombre(product.getNombre())).thenReturn(product);
		when(productoRepository.save(product)).thenReturn(product);
		ProductoResponse savedProduct = productoService.crearProducto(product);
		// Assert
		assertThat(savedProduct, is(equalTo(null)));
	}
	
	@Test
	public void whenValidList_thenProductShouldBeFound() {
		List<Producto> lista = new ArrayList<>();
		lista.add(createProducto());
		when(productoRepository.findAll()).thenReturn(lista);
		BodyListResponse<ProductoResponse> getProducts = productoService.obtenerProductos();
		// Assert
		assertThat(getProducts.getData().size(), is(equalTo(1)));
	}
	
	@Test
    public void shouldCallDeleteMethodOfProductRepository_whenDeleteProductIsCalled() throws Exception {
        doNothing().when(productoRepository).delete(5);
        productoService.eliminar(5);
        // Assert
        verify(productoRepository, times(1)).delete(5);
    }
}
