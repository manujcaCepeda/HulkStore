package com.tienda.todo1.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.todo1.models.Categoria;
import com.tienda.todo1.repositories.CategoriaRepository;

/**
 * The class contain the business logic for the Categoria. 
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Service
@Transactional
public class CategoriaService {

//	private static Logger log = LoggerFactory.getLogger(CategoriaService.class);
	
	private CategoriaRepository categoriaRepository;

	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;	}



	/**
	 * Método que obtiene todos los categoria de la bdd
	 * @return
	 */
	public List<Categoria> obtenerCategorias() {
		return (List<Categoria>) categoriaRepository.findAll();
	}

	/**
	 * Método que permite crear un Categoria en la bdd
	 * @param categoria
	 * @return
	 */
	public Categoria crearCategoria(Categoria categoria) {
		Categoria categoriaExiste = categoriaRepository.findByCodigo(categoria.getCodigo());
		if (categoriaExiste == null) {
			return categoriaRepository.save(categoria);
		}
		return null;
	}

}
