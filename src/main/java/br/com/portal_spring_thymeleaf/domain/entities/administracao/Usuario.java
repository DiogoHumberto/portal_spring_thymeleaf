package br.com.portal_spring_thymeleaf.domain.entities.administracao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.portal_spring_thymeleaf.domain.enumerators.AuthenticationType;
import br.com.portal_spring_thymeleaf.domain.enumerators.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "sobrenome")
	private String sobrenome;

	@NotEmpty
	@NotNull
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "data_cadastro")
	private Date dataCastro;

	@Column(name = "data_alterado")
	private Date dataAlterado;

	@Column(name = "status_usuario")
	private StatusUsuario status;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_type")
	private AuthenticationType authType;
    
    @Column(name = "url_photo")
    private String urlPhoto;
	
	public Usuario(Long id, String nome, @NotEmpty @NotNull String email, String password, Date dataCastro,
			Date dataAlterado, StatusUsuario status) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.dataCastro = dataCastro;
		this.dataAlterado = dataAlterado;
		this.status = status;
	}

	
	public Usuario(Long id, String nome, @NotEmpty @NotNull String email, String password, Date dataCastro,
			Date dataAlterado, StatusUsuario status, Collection<Role> roles) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.dataCastro = dataCastro;
		this.dataAlterado = dataAlterado;
		this.status = status;
		this.roles = roles;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", email=" + email + ", password="
				+ password + ", dataCastro=" + dataCastro + ", dataAlterado=" + dataAlterado + ", status=" + status
				+ ", roles=" + roles + "]";
	}
	

}
