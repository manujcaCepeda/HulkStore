package com.tienda.todo1.dto.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO for response the Producto - API REST.
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
public class ProductoResponse {

	private String nombre;
	private String descripcion;
	private Date fecha;
	private Integer cantidad;
	private String url;
	private String categoria;
	private BigDecimal precio;
	
	public ProductoResponse(String nombre, String descripcion, Date fecha, Integer cantidad, String url,
			BigDecimal precio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.url = url;
		this.precio = precio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the precio
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

}
