package br.com.portal_spring_thymeleaf.domain.dtos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.portal_spring_thymeleaf.domain.constraint.validation.FieldMatch;
import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch.List({
		@FieldMatch(first = "password", second = "confirmPassword", message = "Os campos de senha devem iguais"),
		@FieldMatch(first = "email", second = "confirmEmail", message = "Os campos de email devem ser iguais") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRegistrationDto {
	
	private Long id;	
	@NotEmpty
	private String nome;	

	private String sobrenome;
	
	@NotEmpty
	private String password;
	
	@Email(message = "verifique se e-mail está correto!")
	@NotEmpty
	private String email;
	@Email
	@NotEmpty
	private String confirmEmail;	
	@AssertTrue
    private Boolean terms;
	
	private Date dtAleteracao;
	
	private String urlPhoto; 
	
	public UsuarioRegistrationDto(Usuario user) {
		this.nome = user.getNome();
		this.email = user.getEmail();
	}

	public static List<UsuarioRegistrationDto> converterList(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioRegistrationDto::new).collect(Collectors.toList());
	}

}
