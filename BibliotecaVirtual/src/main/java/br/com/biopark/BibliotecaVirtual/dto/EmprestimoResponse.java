package br.com.biopark.BibliotecaVirtual.dto;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// DTO (Data Transfer Object) para a resposta de um empréstimo
// Usado para enviar informações de empréstimo para o frontend.
public class EmprestimoResponse {
    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String usuarioNome;
    private List<LivroDto> livrosEmprestados;

    // Construtor que recebe uma entidade Emprestimo e mapeia para o DTO
    public EmprestimoResponse(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.dataEmprestimo = emprestimo.getDataEmprestimo();
        this.dataDevolucao = emprestimo.getDataDevolucao();
        // Garante que o usuário não seja nulo antes de tentar acessar o nome
        this.usuarioNome = (emprestimo.getUsuario() != null) ? emprestimo.getUsuario().getNome() : "Desconhecido";
        this.livrosEmprestados = emprestimo.getItens().stream()
                .map(item -> new LivroDto(item.getLivro())) // Mapeia cada EmprestimoItem para um LivroDto
                .collect(Collectors.toList());
    }

    // Construtor padrão (opcional, mas pode ser útil para desserialização)
    public EmprestimoResponse() {
    }

    // Getters para todos os campos
    public Long getId() {
        return id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public List<LivroDto> getLivrosEmprestados() {
        return livrosEmprestados;
    }

    // Setters (opcionais, dependendo se o DTO será usado para receber dados)
    public void setId(Long id) {
        this.id = id;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public void setLivrosEmprestados(List<LivroDto> livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    @Override
    public String toString() {
        return "EmprestimoResponse{" +
                "id=" + id +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", livrosEmprestados=" + livrosEmprestados +
                '}';
    }
}
