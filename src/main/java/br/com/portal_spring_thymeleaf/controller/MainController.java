package br.com.portal_spring_thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.portal_spring_thymeleaf.configuration.OAuth2.CustomOAuth2User;
import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.services.UsuarioService;
import br.com.portal_spring_thymeleaf.utils.GenericUtils;

@Controller
public class MainController {

	@Autowired
	private UsuarioService usuarioService;

	@ModelAttribute("usuarioLogado")
	public UsuarioRegistrationDto usuario(Authentication authentication) {

		if (authentication != null && authentication.isAuthenticated()) {			

			if (!GenericUtils.isEmailValido(authentication.getName())) {
				CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
				return oauthUser != null ? usuarioService.findByEmail(oauthUser.getEmail())
						: usuarioService.findByEmail(authentication.getName());
			}

			return usuarioService.findByEmail(authentication.getName());
		}

		return null;
	}
	
	@GetMapping("/403")
	public String semAcesso403() {
		//return "bootstrap/index";
		return "bootstrap/404";
	}

	@GetMapping("/")
	public String home() {
		//return "bootstrap/index";
		return "pages/home";
	}

	@GetMapping("/home")
	public String homepage() {
		return "pages/home";
	}

	@GetMapping("/utilities-color")
	public String utilitiesColor() {
		return "bootstrap/utilities-color";
	}

	@GetMapping("/utilities-border")
	public String utilitiesBorder() {
		return "bootstrap/utilities-border";
	}

	@GetMapping("/utilities-animation")
	public String utilitiesAnimation() {
		return "bootstrap/utilities-animation";
	}

	@GetMapping("/utilities-other")
	public String utilitiesOther() {
		return "bootstrap/utilities-other";
	}

	@GetMapping("/page")
	public String page() {
		return "bootstrap/page";
	}
	
//	private boolean isGoogleAuthentication(Authentication authentication) {
//
//		return authentication.getAuthorities().stream()
//				.filter(f -> f.getAuthority().toUpperCase().contains(AuthenticationType.GOOGLE.toString())
//						|| f.getAuthority().toUpperCase().contains(AuthenticationType.FACEBOOK.toString()))
//				.findFirst().isPresent();
//	}

//	@GetMapping
//	@RequestMapping("/login")
//	public String login(UsuarioRegistrationDto registrationDto) {
//		return "novo-login";
//	}

}
