package br.com.biopark.BibliotecaVirtual.Repository;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface de repositório para a entidade Emprestimo
// Estende JpaRepository para fornecer operações CRUD básicas
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui se necessário
}
