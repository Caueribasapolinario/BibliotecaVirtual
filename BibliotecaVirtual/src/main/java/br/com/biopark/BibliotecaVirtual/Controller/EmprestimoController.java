package br.com.biopark.BibliotecaVirtual.Controller;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import br.com.biopark.BibliotecaVirtual.Model.Livro;
import br.com.biopark.BibliotecaVirtual.Model.Usuario;
import br.com.biopark.BibliotecaVirtual.Repository.LivroRepository;
import br.com.biopark.BibliotecaVirtual.Repository.UsuarioRepository;
import br.com.biopark.BibliotecaVirtual.Service.EmprestimoService;
import br.com.biopark.BibliotecaVirtual.dto.CriarEmprestimoRequest;
import br.com.biopark.BibliotecaVirtual.dto.EmprestimoResponse;
import br.com.biopark.BibliotecaVirtual.dto.LivroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService,
                                UsuarioRepository usuarioRepository,
                                LivroRepository livroRepository) {
        this.emprestimoService = emprestimoService;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping("/registrar")
    public String showRegistrarEmprestimoForm(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("criarEmprestimoRequest", new CriarEmprestimoRequest());
        return "emprestimo-form";
    }

    @PostMapping("/registrar")
    public String registrarEmprestimo(@ModelAttribute CriarEmprestimoRequest request,
                                      RedirectAttributes redirectAttributes) {
        try {
            Emprestimo novoEmprestimo = emprestimoService.criarEmprestimo(request);
            redirectAttributes.addFlashAttribute("message", "Empréstimo registrado com sucesso! ID: " + novoEmprestimo.getId());
            return "redirect:/emprestimos";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/emprestimos/registrar";
        }
    }

    @GetMapping
    public String listarEmprestimos(Model model) {
        List<EmprestimoResponse> emprestimos = emprestimoService.listarTodosEmprestimos();
        model.addAttribute("emprestimos", emprestimos);
        return "emprestimo-list";
    }

    @GetMapping("/livros/buscar")
    @ResponseBody
    public List<LivroDto> buscarLivros(@RequestParam String titulo) {
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livros.stream()
                .map(LivroDto::new)
                .collect(Collectors.toList());
    }
}