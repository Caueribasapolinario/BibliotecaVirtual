package br.com.biopark.BibliotecaVirtual.Repository;

import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface de repositório para a entidade Usuario
// Estende JpaRepository para fornecer operações CRUD básicas
// O primeiro parâmetro é a entidade, o segundo é o tipo do ID da entidade
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui se necessário
    // Exemplo: Usuario findByEmail(String email);
}
