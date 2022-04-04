package br.com.portal_spring_thymeleaf.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.portal_spring_thymeleaf.configuration.OAuth2.CustomOAuth2User;
import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Role;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;
import br.com.portal_spring_thymeleaf.domain.enumerators.AuthenticationType;
import br.com.portal_spring_thymeleaf.domain.mappers.administracao.UsuarioMapper;
import br.com.portal_spring_thymeleaf.domain.repositories.administracao.UsuarioRepository;
import br.com.portal_spring_thymeleaf.services.UsuarioService;
import br.com.portal_spring_thymeleaf.utils.exception.UsuarioJaExisteException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void processOAuthPostLogin(CustomOAuth2User oauthUser) {
		
		if(!usuarioRepository.existsByEmail(oauthUser.getEmail())) {
			usuarioRepository.save(
					Usuario.builder()
					.nome(oauthUser.getNameClean())
					.email(oauthUser.getEmail())
					.urlPhoto(oauthUser.getPicture())
					.authType(AuthenticationType.valueOf(oauthUser.getOauth2ClientName().toUpperCase())).build());
		}
		
	}

	public Usuario save(UsuarioRegistrationDto registration) {
				
		Usuario entity = usuarioRepository.findByEmail(registration.getEmail());	
		
		if (entity != null) {			
			if (StringUtils.hasText(String.valueOf(registration.getId()))) {				
				Usuario usuarioUpdate = UsuarioMapper.toEntity(registration);			
				BeanUtils.copyProperties(usuarioUpdate, entity, "nome", "sobrenome","roles", "authType","urlPhoto");			
				usuarioUpdate.setDataAlterado(Calendar.getInstance().getTime());				
				return usuarioRepository.save(usuarioUpdate);				
			}
			throw new UsuarioJaExisteException("Alteração indevida!");
		}
		
		return usuarioRepository.save(Usuario.builder().nome(registration.getNome())
				.sobrenome(registration.getSobrenome()).email(registration.getEmail())
				.password(passwordEncoder.encode(registration.getPassword()))
				.roles(Arrays.asList(new Role("ROLE_USER"))).authType(AuthenticationType.DATABASE)
				.dataCastro(Calendar.getInstance().getTime()).dataAlterado(Calendar.getInstance().getTime()).build());
		
	}
	
	public boolean existByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	public  UsuarioRegistrationDto findByEmail(String email) {
		return UsuarioMapper.toDto(usuarioRepository.findByEmail(email));
	}

	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	//@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("nome de usuário ou senha inválidos.");
		}
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//	    HttpSession session = attr.getRequest().getSession(true);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
