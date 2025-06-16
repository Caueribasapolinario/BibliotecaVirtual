package br.com.biopark.BibliotecaVirtual.Repository;

import br.com.biopark.BibliotecaVirtual.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Interface de repositório para a entidade Livro
// Estende JpaRepository para fornecer operações CRUD básicas
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    // Você pode adicionar métodos de consulta personalizados aqui se necessário
    // Exemplo: List<Livro> findByTituloContainingIgnoreCase(String titulo);
}
