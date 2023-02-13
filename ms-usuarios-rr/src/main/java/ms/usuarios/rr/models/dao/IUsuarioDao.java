package ms.usuarios.rr.models.dao;

import org.springframework.data.repository.CrudRepository;

import ms.usuarios.rr.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

}
