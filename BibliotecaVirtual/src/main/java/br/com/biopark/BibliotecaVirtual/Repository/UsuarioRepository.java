package br.com.biopark.BibliotecaVirtual.Repository;

import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}