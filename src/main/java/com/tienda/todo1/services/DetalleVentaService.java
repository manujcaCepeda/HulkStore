package com.tienda.todo1.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.todo1.dto.response.DetalleVentaResponse;
import com.tienda.todo1.models.DetalleVenta;
import com.tienda.todo1.repositories.ProductoRepository;
import com.tienda.todo1.repositories.DetalleVentaRepository;

/**
 * The class contain the business logic for the DetalleVenta. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class DetalleVentaService {

//private static Logger log = LoggerFactory.getLogger(DetalleVentaService.class);
	
	private DetalleVentaRepository detalleVentaRepository;
	private ProductoRepository productoRepository;

	@Autowired
	public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, ProductoRepository productoRepository) {
		this.detalleVentaRepository = detalleVentaRepository;
		this.productoRepository = productoRepository;
	}

	/**
	 * Método que permite transfomar de tipo DetalleVenta a DetalleVentaResponse
	 * @param detalleVenta
	 * @return
	 */
	private DetalleVentaResponse doUsusarioResponse(DetalleVenta detalleVenta) {
		DetalleVentaResponse detalleVentaResponse = new DetalleVentaResponse(detalleVenta.getCantidad(), detalleVenta.getSubtotal(),
				detalleVenta.getTotal());
		detalleVentaResponse.setProducto(productoRepository.findById(detalleVenta.getProducto().getId()).getNombre());
		return detalleVentaResponse;
	}

	/**
	 * Método que obtiene todos los detalleVenta de la bdd
	 * @return
	 */
	public List<DetalleVenta> obtenerDetalleVentas() {
		return (List<DetalleVenta>) detalleVentaRepository.findAll();
	}

	/**
	 * Método que permite crear un DetalleVenta en la bdd
	 * @param detalleVenta
	 * @return
	 */
	public DetalleVentaResponse crearDetalleVenta(DetalleVenta detalleVenta) {
		detalleVenta = detalleVentaRepository.save(detalleVenta);
		return doUsusarioResponse(detalleVenta);
	}

}
