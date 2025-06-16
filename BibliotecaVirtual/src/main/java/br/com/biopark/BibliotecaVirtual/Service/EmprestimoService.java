package br.com.biopark.BibliotecaVirtual.Service;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import br.com.biopark.BibliotecaVirtual.Model.EmprestimoItem;
import br.com.biopark.BibliotecaVirtual.Model.Livro;
import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import br.com.biopark.BibliotecaVirtual.Repository.EmprestimoItemRepository;
import br.com.biopark.BibliotecaVirtual.Repository.EmprestimoRepository;
import br.com.biopark.BibliotecaVirtual.Repository.LivroRepository;
import br.com.biopark.BibliotecaVirtual.Repository.UsuarioRepository;
import br.com.biopark.BibliotecaVirtual.dto.CriarEmprestimoRequest; // Importa o DTO correto
import br.com.biopark.BibliotecaVirtual.dto.EmprestimoResponse; // Importa o DTO correto (se usado, embora não neste método específico)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Classe de serviço para gerenciar operações de empréstimo
@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoItemRepository emprestimoItemRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository, EmprestimoItemRepository emprestimoItemRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.emprestimoItemRepository = emprestimoItemRepository;
    }

    /**
     * Cria um novo empréstimo no sistema.
     * @param request O objeto de requisição contendo o ID do usuário e os IDs dos livros.
     * @return O objeto Emprestimo criado.
     * @throws RuntimeException se o usuário ou algum livro não for encontrado.
     */
    @Transactional // Garante que a operação seja atômica
    public Emprestimo criarEmprestimo(CriarEmprestimoRequest request) {
        // 1. Buscar o Usuário
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + request.getUsuarioId()));

        // 2. Criar o objeto Emprestimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(LocalDate.now()); // Define a data do empréstimo como a data atual
        emprestimo.setUsuario(usuario); // Associa o usuário ao empréstimo
        // dataDevolucao pode ser null inicialmente ou calculada (ex: .plusDays(15))

        // 3. Buscar os Livros e criar os Itens de Empréstimo
        if (request.getLivroIds() == null || request.getLivroIds().isEmpty()) {
            throw new RuntimeException("Nenhum livro selecionado para o empréstimo.");
        }

        for (Long livroId : request.getLivroIds()) {
            Livro livro = livroRepository.findById(livroId)
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + livroId));
            // Opcional: Adicionar lógica para verificar disponibilidade do livro aqui
            // (ex: se o livro já está emprestado)

            EmprestimoItem item = new EmprestimoItem(livro, emprestimo);
            emprestimo.addItem(item); // Adiciona o item ao empréstimo, que também define a relação bidirecional
        }

        // 4. Salvar o Emprestimo (itens serão cascateados devido a CascadeType.ALL)
        return emprestimoRepository.save(emprestimo);
    }

    /**
     * Remove um item específico de um empréstimo existente.
     * @param emprestimoId O ID do empréstimo.
     * @param emprestimoItemId O ID do item de empréstimo a ser removido.
     * @throws RuntimeException se o empréstimo ou o item não for encontrado.
     */
    @Transactional
    public void removerItemEmprestimo(Long emprestimoId, Long emprestimoItemId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com ID: " + emprestimoId));

        Optional<EmprestimoItem> itemParaRemover = emprestimo.getItens().stream()
                .filter(item -> item.getId().equals(emprestimoItemId))
                .findFirst();

        if (itemParaRemover.isPresent()) {
            emprestimo.removeItem(itemParaRemover.get()); // Remove o item da lista
            emprestimoRepository.save(emprestimo); // Salva o empréstimo para persistir a mudança
        } else {
            throw new RuntimeException("Item de empréstimo não encontrado com ID: " + emprestimoItemId + " no empréstimo " + emprestimoId);
        }
    }

    /**
     * Busca um empréstimo pelo seu ID.
     * @param id O ID do empréstimo.
     * @return Um Optional contendo o Emprestimo, ou vazio se não encontrado.
     */
    public Optional<Emprestimo> buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    /**
     * Lista todos os empréstimos.
     * @return Uma lista de todos os Emprestimos.
     */
    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }
}
