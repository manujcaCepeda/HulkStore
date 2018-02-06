package com.tienda.todo1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.todo1.models.Rol;

/**
 * The interface which represent DOA for the rol database table.
 * Hulk Store 2018 - Todos los derechos reservados
 * @author Manuel Cepeda - email manujca@hotmail.com - cel. 0989345370
 * @version $1.0$
 */
@Repository
public interface RolRepository extends CrudRepository<Rol, Integer>{

	Rol findById(Integer id);
}
