package br.com.portal_spring_thymeleaf.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.portal_spring_thymeleaf.configuration.OAuth2.CustomOAuth2User;
import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;

public interface UsuarioService extends UserDetailsService {

	boolean existByEmail(String email);

	UsuarioRegistrationDto findByEmail(String email);

	Optional<Usuario> findById(Long id);

	List<Usuario> findAll();

	Usuario save(UsuarioRegistrationDto registration);

	void processOAuthPostLogin(CustomOAuth2User oauthUser);

}
