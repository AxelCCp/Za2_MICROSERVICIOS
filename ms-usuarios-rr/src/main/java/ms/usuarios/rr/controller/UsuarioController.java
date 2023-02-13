package ms.usuarios.rr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ms.usuarios.rr.models.entity.Usuario;
import ms.usuarios.rr.models.services.IUsuarioService;

@RestController
public class UsuarioController {

	@GetMapping("/listar")
	public List<Usuario>listaUsuarios(){
		return usuarioService.findAllUsuarios();
	}
	
	@Autowired
	private IUsuarioService usuarioService;
	
}
