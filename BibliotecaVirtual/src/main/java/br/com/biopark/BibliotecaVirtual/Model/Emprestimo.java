package br.com.biopark.BibliotecaVirtual.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Entidade JPA para representar um Empréstimo
@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do empréstimo

    @Column(nullable = false)
    private LocalDate dataEmprestimo; // Data em que o empréstimo foi feito

    private LocalDate dataDevolucao; // Data de devolução do empréstimo (pode ser nula se não devolvido)

    // Relacionamento muitos-para-um com Usuario
    // Um usuário pode ter muitos empréstimos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Coluna de chave estrangeira
    private Usuario usuario; // O usuário que realizou o empréstimo

    // Relacionamento um-para-muitos com EmprestimoItem
    // Um empréstimo pode ter muitos itens (livros)
    // CascadeType.ALL: Operações de persistência (salvar, atualizar, deletar) serão propagadas para os itens
    // orphanRemoval = true: Remove itens órfãos (itens que não estão mais associados a nenhum empréstimo)
    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmprestimoItem> itens = new ArrayList<>(); // Lista de itens (livros) neste empréstimo

    // Construtor padrão necessário para JPA
    public Emprestimo() {
    }

    // Construtor com campos obrigatórios
    public Emprestimo(LocalDate dataEmprestimo, Usuario usuario) {
        this.dataEmprestimo = dataEmprestimo;
        this.usuario = usuario;
    }

    // Getters e Setters para todos os campos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<EmprestimoItem> getItens() {
        return itens;
    }

    // Método para adicionar um item ao empréstimo, mantendo a consistência do relacionamento bidirecional
    public void addItem(EmprestimoItem item) {
        itens.add(item);
        item.setEmprestimo(this);
    }

    // Método para remover um item do empréstimo, mantendo a consistência do relacionamento bidirecional
    public void removeItem(EmprestimoItem item) {
        itens.remove(item);
        item.setEmprestimo(null);
    }

    // Métodos equals e hashCode para comparação de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", itensCount=" + itens.size() +
                '}';
    }
}
