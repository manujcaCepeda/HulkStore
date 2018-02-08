package com.tienda.todo1.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.BDDMockito.given;

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.ProductoResponse;
import com.tienda.todo1.models.Producto;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private ProductoController productoController;
	
	String exampleCourseJson = "{\"id\":\"13\",\"nombre\":\"Supereroes\",\"descripcion\":\"Era Marvel de los Comics 1961-1978\"}";

	@Test
	public void getArrivals() throws Exception {
		ProductoResponse producto = new ProductoResponse(1, "nombre", "descripcion", new Date(), 100, "url",
				new BigDecimal(10));
		List<ProductoResponse> productos = new ArrayList<>();
		productos.add(producto);

		given(productoController.getProductos()).willReturn(new BodyListResponse<>(productos));
		mvc.perform(get("/producto")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data.[0].nombre", is(producto.getNombre())));
	}
	
	@Test
    public void removeUsersById() throws Exception {
        Producto producto = createProducto();
        mvc.perform(delete("/producto/" + producto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
    private Producto createProducto() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Supereroes");
        producto.setDescripcion("Era Marvel de los Comics 1961-1978");
        producto.setCantidad(10);
        producto.setPrecio(new BigDecimal(10));
        producto.setUrl("url");
        return producto;
    }

    @Test
	public void createStudentCourse() throws Exception {
		ProductoResponse mockProducto = new ProductoResponse(1,"Supereroes", "Era Marvel de los Comics 1961-1978", new Date(), 10, "url", new BigDecimal(10));

		Mockito.when(productoController.saveProducto(Mockito.any(Producto.class))).thenReturn(mockProducto);

		// Send course as body to /producto
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/producto")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());


	}
    
}
