package br.com.biopark.BibliotecaVirtual.Repository;

import br.com.biopark.BibliotecaVirtual.Model.EmprestimoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface de repositório para a entidade EmprestimoItem
// Estende JpaRepository para fornecer operações CRUD básicas
@Repository
public interface EmprestimoItemRepository extends JpaRepository<EmprestimoItem, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui se necessário
}
