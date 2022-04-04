package br.com.portal_spring_thymeleaf.domain.repositories.administracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.portal_spring_thymeleaf.domain.entities.administracao.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	
	Usuario findById(long id);
	
	boolean existsByEmail(String email);
}
