package ms.usuarios.rr.models.services;

import java.util.List;

import ms.usuarios.rr.models.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario>findAllUsuarios();
	
}
