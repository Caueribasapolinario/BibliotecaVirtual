package br.com.biopark.BibliotecaVirtual.dto;

import br.com.biopark.BibliotecaVirtual.Model.Livro;

// DTO (Data Transfer Object) para Livro
// Usado para enviar informações simplificadas de livros para o frontend.
public class LivroDto {
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;

    // Construtor que recebe uma entidade Livro e mapeia para o DTO
    public LivroDto(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.autor = livro.getAutor();
        this.anoPublicacao = livro.getAnoPublicacao();
    }

    // Construtor padrão (opcional, mas pode ser útil para desserialização)
    public LivroDto() {
    }

    // Getters para todos os campos
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    // Setters (opcionais, dependendo se o DTO será usado para receber dados)
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String toString() {
        return "LivroDto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anoPublicacao=" + anoPublicacao +
                '}';
    }
}
