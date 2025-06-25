package br.com.biopark.BibliotecaVirtual.dto;

import br.com.biopark.BibliotecaVirtual.Model.Emprestimo;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EmprestimoResponse {
    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String usuarioNome;
    private List<LivroDto> livrosEmprestados;

    public EmprestimoResponse(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.dataEmprestimo = emprestimo.getDataEmprestimo();
        this.dataDevolucao = emprestimo.getDataDevolucao();
        this.usuarioNome = (emprestimo.getUsuario() != null) ? emprestimo.getUsuario().getNome() : "Desconhecido";
        this.livrosEmprestados = emprestimo.getItens().stream()
                .map(item -> new LivroDto(item.getLivro()))
                .collect(Collectors.toList());
    }
}