package com.tienda.todo1.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private IngresoResponse doUsusarioResponse(Ingreso ingreso) {
		IngresoResponse ingresoResponse = new IngresoResponse(ingreso.getCantidad(), ingreso.getFechaIngreso(),
				ingreso.getTotal());
		ingresoResponse.setProducto(productoRepository.findById(ingreso.getProducto().getId()).getNombre());
		Usuario user = usuarioRepository.findById(ingreso.getUsuario().getId());
		ingresoResponse.setUsuario(user.getNombres() + " " + user.getApellidos());
		return ingresoResponse;
	}

	/**
	 * Método que obtiene todos los ingreso de la bdd
	 * @return
	 */
	public List<Ingreso> obtenerIngresos() {
		return (List<Ingreso>) ingresoRepository.findAll();
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
		return doUsusarioResponse(ingresoRepository.save(ingreso));
	}

}
