package br.com.biopark.BibliotecaVirtual.Controller;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import br.com.biopark.BibliotecaVirtual.Model.Livro;
import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import br.com.biopark.BibliotecaVirtual.Service.EmprestimoService;
import br.com.biopark.BibliotecaVirtual.Repository.LivroRepository; // Para buscar livros para o frontend
import br.com.biopark.BibliotecaVirtual.Repository.UsuarioRepository; // Para buscar usuários para o frontend
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

// Classe de serviço para gerenciar operações de empréstimo (repetido para clareza)
// DTOs para requisição e resposta do empréstimo
// (Normalmente estariam em um pacote .dto, mas para simplificar, estão aqui)
class CriarEmprestimoRequest {
    private Long usuarioId;
    private List<Long> livroIds;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public List<Long> getLivroIds() { return livroIds; }
    public void setLivroIds(List<Long> livroIds) { this.livroIds = livroIds; }
}

class EmprestimoResponse {
    private Long id;
    private String usuarioNome;
    private List<LivroDto> livrosEmprestados;

    public EmprestimoResponse(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.usuarioNome = emprestimo.getUsuario().getNome();
        this.livrosEmprestados = emprestimo.getItens().stream()
                .map(item -> new LivroDto(item.getLivro()))
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public String getUsuarioNome() { return usuarioNome; }
    public List<LivroDto> getLivrosEmprestados() { return livrosEmprestados; }
}

class LivroDto {
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;

    public LivroDto(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.autor = livro.getAutor();
        this.anoPublicacao = livro.getAnoPublicacao();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public Integer getAnoPublicacao() { return anoPublicacao; }
}


// Controlador para gerenciar as requisições relacionadas a empréstimos
@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final UsuarioRepository usuarioRepository; // Para listar usuários no formulário
    private final LivroRepository livroRepository; // Para listar livros ou buscar por título

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService,
                                UsuarioRepository usuarioRepository,
                                LivroRepository livroRepository) {
        this.emprestimoService = emprestimoService;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    // Exibe o formulário de registro de empréstimo (GET /emprestimos/registrar)
    @GetMapping("/registrar")
    public String showRegistrarEmprestimoForm(Model model) {
        // Adiciona uma lista de usuários para o dropdown
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);

        // Adiciona um objeto de requisição vazio para preencher no formulário
        model.addAttribute("criarEmprestimoRequest", new CriarEmprestimoRequest());
        return "registrar-emprestimo"; // Nome do template Thymeleaf (registrar-emprestimo.html)
    }

    // Endpoint para criar um novo empréstimo (POST /emprestimos/registrar)
    // O @RequestBody indica que o corpo da requisição será mapeado para o objeto CriarEmprestimoRequest
    @PostMapping("/registrar")
    public String registrarEmprestimo(@ModelAttribute CriarEmprestimoRequest request,
                                      RedirectAttributes redirectAttributes) {
        try {
            Emprestimo novoEmprestimo = emprestimoService.criarEmprestimo(request);
            redirectAttributes.addFlashAttribute("message", "Empréstimo registrado com sucesso! ID: " + novoEmprestimo.getId());
            return "redirect:/emprestimos/listar"; // Redireciona para uma página de listagem ou sucesso
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/emprestimos/registrar"; // Redireciona de volta ao formulário em caso de erro
        }
    }

    // Endpoint para remover um item de empréstimo (DELETE /emprestimos/{emprestimoId}/itens/{emprestimoItemId})
    // @DeleteMapping é usado para remover recursos
    @DeleteMapping("/{emprestimoId}/itens/{emprestimoItemId}")
    public ResponseEntity<String> removerItemEmprestimo(@PathVariable Long emprestimoId,
                                                        @PathVariable Long emprestimoItemId) {
        try {
            emprestimoService.removerItemEmprestimo(emprestimoId, emprestimoItemId);
            return new ResponseEntity<>("Item removido com sucesso!", HttpStatus.NO_CONTENT); // 204 No Content
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found ou 400 Bad Request
        }
    }

    // Endpoint para listar todos os empréstimos (GET /emprestimos/listar)
    @GetMapping("/listar")
    public String listarEmprestimos(Model model) {
        List<Emprestimo> emprestimos = emprestimoService.listarTodosEmprestimos();
        model.addAttribute("emprestimos", emprestimos);
        return "listar-emprestimos"; // Nome do template Thymeleaf (listar-emprestimos.html)
    }

    // Endpoint para buscar livros por título (AJAX para o campo "Livro")
    @GetMapping("/livros/buscar")
    @ResponseBody // Retorna diretamente os dados como JSON
    public List<LivroDto> buscarLivros(@RequestParam String titulo) {
        // Supondo que você tenha um método no LivroRepository para buscar por título
        // Ou você pode fazer isso no serviço se a lógica for mais complexa
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livros.stream()
                .map(LivroDto::new)
                .collect(Collectors.toList());
    }
}
