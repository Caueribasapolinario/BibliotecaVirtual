package br.com.biopark.BibliotecaVirtual.dto;

import br.com.biopark.BibliotecaVirtual.Model.Livro;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroDto {
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
}