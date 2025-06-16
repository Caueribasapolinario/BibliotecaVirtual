package br.com.biopark.BibliotecaVirtual.Model;

import jakarta.persistence.*;
import java.util.Objects;

// Entidade JPA para representar um item de um Empréstimo (um livro dentro de um empréstimo específico)
@Entity
@Table(name = "emprestimo_item")
public class EmprestimoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do item de empréstimo

    // Relacionamento muitos-para-um com Livro
    // Um livro pode estar em muitos itens de empréstimo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false) // Coluna de chave estrangeira
    private Livro livro; // O livro associado a este item de empréstimo

    // Relacionamento muitos-para-un com Emprestimo
    // Um empréstimo pode ter muitos itens, e um item pertence a um único empréstimo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprestimo_id", nullable = false) // Coluna de chave estrangeira
    private Emprestimo emprestimo; // O empréstimo ao qual este item pertence

    // Construtor padrão necessário para JPA
    public EmprestimoItem() {
    }

    // Construtor com campos obrigatórios
    public EmprestimoItem(Livro livro, Emprestimo emprestimo) {
        this.livro = livro;
        this.emprestimo = emprestimo;
    }

    // Getters e Setters para todos os campos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    // Métodos equals e hashCode para comparação de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmprestimoItem that = (EmprestimoItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "EmprestimoItem{" +
                "id=" + id +
                ", livro=" + (livro != null ? livro.getTitulo() : "null") +
                ", emprestimoId=" + (emprestimo != null ? emprestimo.getId() : "null") +
                '}';
    }
}
