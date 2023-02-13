package ms.usuarios.rr.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.usuarios.rr.models.dao.IUsuarioDao;
import ms.usuarios.rr.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService{

	@Override
	public List<Usuario> findAllUsuarios() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}
	
	@Autowired
	private IUsuarioDao usuarioDao;

}
