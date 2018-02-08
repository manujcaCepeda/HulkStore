package com.tienda.todo1.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.todo1.dto.response.VentaResponse;
import com.tienda.todo1.models.Producto;
import com.tienda.todo1.models.Usuario;
import com.tienda.todo1.models.Venta;
import com.tienda.todo1.repositories.ProductoRepository;
import com.tienda.todo1.repositories.UsuarioRepository;
import com.tienda.todo1.repositories.VentaRepository;

/**
 * The class contain the business logic for the Venta. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class VentaService {

//private static Logger log = LoggerFactory.getLogger(VentaService.class);
	
	private VentaRepository ventaRepository;
	private ProductoRepository productoRepository;
	private UsuarioRepository usuarioRepository;
	
	public VentaService() {
		
	}
	
	@Autowired
	public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
			UsuarioRepository usuarioRepository) {
		this.ventaRepository = ventaRepository;
		this.productoRepository = productoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Método que permite transfomar de tipo Venta a VentaResponse
	 * @param venta
	 * @return
	 */
	private VentaResponse doVentaResponse(Venta venta) {
		VentaResponse ventaResponse = new VentaResponse(venta.getNroDocumento(), venta.getFechaVenta(),
				venta.getSubtotal(), venta.getIva(), venta.getTotal());
		Usuario user = usuarioRepository.findById(venta.getUsuario().getId());
		ventaResponse.setUsuario(user.getNombres() + " " + user.getApellidos());
		return ventaResponse;
	}

	/**
	 * Método que obtiene todos los Venta de la bdd
	 * @return
	 */
	public List<Venta> obtenerVentas() {
		return (List<Venta>) ventaRepository.findAll();
	}

	/**
	 * Método que permite crear un venta en la bdd
	 * @param venta
	 * @return
	 */
	public VentaResponse crearVenta(Venta venta) {
		venta.setFechaVenta(new Date());
		venta.setNroDocumento("000"+ productoRepository.count() + 1);
		venta.getDetalleList().forEach(detalle ->{
			Producto producto = productoRepository.findById(detalle.getProducto().getId());
			producto.setCantidad(producto.getCantidad() - detalle.getCantidad());
			productoRepository.save(producto);
			
			detalle.setVenta(venta);
		});
		return doVentaResponse(ventaRepository.save(venta));
	}

}
