package br.com.biopark.BibliotecaVirtual.Service;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import br.com.biopark.BibliotecaVirtual.Model.EmprestimoItem;
import br.com.biopark.BibliotecaVirtual.Model.Livro;
import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import br.com.biopark.BibliotecaVirtual.Repository.EmprestimoRepository;
import br.com.biopark.BibliotecaVirtual.Repository.LivroRepository;
import br.com.biopark.BibliotecaVirtual.Repository.UsuarioRepository;
import br.com.biopark.BibliotecaVirtual.dto.CriarEmprestimoRequest;
import br.com.biopark.BibliotecaVirtual.dto.EmprestimoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Emprestimo criarEmprestimo(CriarEmprestimoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + request.getUsuarioId()));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setUsuario(usuario);

        if (request.getLivroIds() == null || request.getLivroIds().isEmpty()) {
            throw new RuntimeException("Nenhum livro selecionado para o empréstimo.");
        }

        for (Long livroId : request.getLivroIds()) {
            Livro livro = livroRepository.findById(livroId)
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + livroId));

            EmprestimoItem item = new EmprestimoItem(livro, emprestimo);
            emprestimo.addItem(item);
        }

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public void removerItemEmprestimo(Long emprestimoId, Long emprestimoItemId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com ID: " + emprestimoId));

        Optional<EmprestimoItem> itemParaRemover = emprestimo.getItens().stream()
                .filter(item -> item.getId().equals(emprestimoItemId))
                .findFirst();

        if (itemParaRemover.isPresent()) {
            emprestimo.removeItem(itemParaRemover.get());
            emprestimoRepository.save(emprestimo);
        } else {
            throw new RuntimeException("Item de empréstimo não encontrado com ID: " + emprestimoItemId + " no empréstimo " + emprestimoId);
        }
    }

    public Optional<Emprestimo> buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public List<EmprestimoResponse> listarTodosEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(EmprestimoResponse::new)
                .collect(Collectors.toList());
    }
}