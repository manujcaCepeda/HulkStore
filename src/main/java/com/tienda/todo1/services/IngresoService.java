package com.tienda.todo1.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.todo1.dto.response.BodyListResponse;
import com.tienda.todo1.dto.response.IngresoResponse;
import com.tienda.todo1.models.Ingreso;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.repositories.IngresoRepository;
import com.tienda.todo1.repositories.ProductoRepository;
import com.tienda.todo1.repositories.UsuarioRepository;

/**
 * The class contain the business logic for the Ingreso. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class IngresoService {

//private static Logger log = LoggerFactory.getLogger(IngresoService.class);
	
	private IngresoRepository ingresoRepository;
	private ProductoRepository productoRepository;
	private UsuarioRepository usuarioRepository;

	public IngresoService() {
		
	}
	
	@Autowired
	public IngresoService(IngresoRepository ingresoRepository, ProductoRepository productoRepository,
			UsuarioRepository usuarioRepository) {
		this.ingresoRepository = ingresoRepository;
		this.productoRepository = productoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Método que permite transfomar de tipo Ingreso a IngresoResponse
	 * @param ingreso
	 * @return
	 */
	private IngresoResponse doIngresoResponse(Ingreso ingreso) {
		IngresoResponse ingresoResponse = new IngresoResponse(ingreso.getId(), ingreso.getCantidad(), ingreso.getFechaIngreso(),
				ingreso.getTotal());
		
		Producto producto = productoRepository.findById(ingreso.getProducto().getId());
		ingresoResponse.setProducto(producto.getNombre());
		ingresoResponse.setPrecio(producto.getPrecio());
		Usuario user = usuarioRepository.findById(ingreso.getUsuario().getId());
		ingresoResponse.setUsuario(user.getNombres() + " " + user.getApellidos());
		return ingresoResponse;
	}

	/**
	 * Método que obtiene todos los ingreso de la bdd
	 * @return
	 */
	public BodyListResponse<IngresoResponse> obtenerIngresos() {
		List<IngresoResponse> productos = new ArrayList<>();	
		List<Ingreso> respuesta = (List<Ingreso>) ingresoRepository.findAll();

		if (respuesta != null && !respuesta.isEmpty()) {
			for (Ingreso ingreso : respuesta) {
				productos.add(doIngresoResponse(ingreso));
			}
		}
		return new BodyListResponse<>(productos);
	}

	/**
	 * Método que permite crear un Ingreso en la bdd
	 * @param ingreso
	 * @return
	 */
	public IngresoResponse crearIngreso(Ingreso ingreso) {
		ingreso.setFechaIngreso(new Date());
		Producto producto = productoRepository.findById(ingreso.getProducto().getId());
		producto.setCantidad(producto.getCantidad() + ingreso.getCantidad());
		productoRepository.save(producto);
		return doIngresoResponse(ingresoRepository.save(ingreso));
	}
	
	/**
	 * Método que permite eliminar un Ingreso de la bdd
	 * @param codigo
	 */
	public void eliminar(Integer codigo) {
		Ingreso ingreso = ingresoRepository.findOne(codigo);
		Producto producto = ingreso.getProducto();
		producto.setCantidad(producto.getCantidad() - ingreso.getCantidad());
		productoRepository.save(producto);
		ingresoRepository.delete(codigo);
	}

}
