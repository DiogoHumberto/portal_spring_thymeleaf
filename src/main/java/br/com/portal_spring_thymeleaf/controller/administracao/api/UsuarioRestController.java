package br.com.portal_spring_thymeleaf.controller.administracao.api;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;
import br.com.portal_spring_thymeleaf.domain.repositories.administracao.UsuarioRepository;
import br.com.portal_spring_thymeleaf.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api")
@Api(value="API REST Usuario")
public class UsuarioRestController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService service;
	
	@ApiOperation(value="Retorna lista de usuariosDto")
	@GetMapping("/usuarios")
	public List<UsuarioRegistrationDto> listaUsuario(){
	
		return UsuarioRegistrationDto.converterList(service.findAll());
				
	}
	
	@ApiOperation(value="Retorna um usuario pelo id")
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioRegistrationDto> listaUsuarioId(@PathVariable(value="id") long id){
		return ResponseEntity.ok(new UsuarioRegistrationDto(service.findById(id).get()));
		
	}
	
	@ApiOperation(value="salva um usuario")
	@PostMapping("/usuario")
	public ResponseEntity<UsuarioRegistrationDto> salvaUsuario(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
		usuarioRepository.save(usuario);
		URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioRegistrationDto(usuario));
	}
	
	@ApiOperation(value="Deleta um usuario")
	@DeleteMapping("/usuario/{id}")
	public void deleteUsuario(@PathVariable(value="id") long id, @RequestBody Usuario usuario) {
//		service.deletar(usuario);
			
	}
	
	@ApiOperation(value="Altera um usuario")
	@PutMapping("/usuario/{id}")
	public ResponseEntity<UsuarioRegistrationDto> alterarUsuario(@PathVariable(value="id") long id, @RequestBody Usuario usuario) {
		return ResponseEntity.ok(null);
		
	}
}
