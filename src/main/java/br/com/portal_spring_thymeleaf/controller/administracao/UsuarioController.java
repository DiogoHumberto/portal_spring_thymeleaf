package br.com.portal_spring_thymeleaf.controller.administracao;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.services.UsuarioService;

@Controller
//@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
//	@ModelAttribute("usuario")
//	public UsuarioRegistrationDto usuarioRegistrationDto() {
//		return new UsuarioRegistrationDto();
//	}
	
	@GetMapping
	@RequestMapping("/usuario/cadastro")
	public String cadastro(UsuarioRegistrationDto registrationDto) {
		return "registration";
	}
	
	@GetMapping
	@RequestMapping("/login")
	public String login(UsuarioRegistrationDto registrationDto) {
		return "login";
	}

	@PostMapping
	@RequestMapping("/usuario/novo")
	public String salvarUsuario(@Valid UsuarioRegistrationDto registrationDto, BindingResult result) {
		if (result.hasErrors()) {
			return login(registrationDto);
		}
		if (this.usuarioService.existByEmail(registrationDto.getEmail())) {
			return "redirect:/usuario/cadastro?exist";
		} else {
			this.usuarioService.save(registrationDto);
			return "redirect:/login?success";
		}		
	}

}
