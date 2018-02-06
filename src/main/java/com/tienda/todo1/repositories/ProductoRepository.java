package com.tienda.todo1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.todo1.models.Producto;

/**
 * The interface which represent DOA for the producto database table.
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer>{

}
