package br.com.portal_spring_thymeleaf.domain.mappers.administracao;

import org.springframework.util.StringUtils;

import br.com.portal_spring_thymeleaf.domain.dtos.UsuarioRegistrationDto;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;

public class UsuarioMapper {
	
	public static Usuario toEntity(UsuarioRegistrationDto dto) {
		return Usuario.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.sobrenome(dto.getSobrenome())
				.email(dto.getEmail())
				.urlPhoto(dto.getUrlPhoto())
				.build();
	}
	
	public static UsuarioRegistrationDto toDto(Usuario entity) {
		return UsuarioRegistrationDto.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.sobrenome(entity.getSobrenome())
				.email(entity.getEmail())
				.dtAleteracao(entity.getDataAlterado())
				.urlPhoto(StringUtils.hasText(entity.getUrlPhoto()) ? entity.getUrlPhoto() : "/bootstrap/img/undraw_profile.svg")
				.build();
				
	}

}
