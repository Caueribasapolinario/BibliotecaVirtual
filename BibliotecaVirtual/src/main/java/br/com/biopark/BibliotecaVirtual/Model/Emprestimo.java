package br.com.biopark.BibliotecaVirtual.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EmprestimoItem> itens = new ArrayList<>();

    public Emprestimo(LocalDate dataEmprestimo, Usuario usuario) {
        this.dataEmprestimo = dataEmprestimo;
        this.usuario = usuario;
    }

    public void addItem(EmprestimoItem item) {
        itens.add(item);
        item.setEmprestimo(this);
    }

    public void removeItem(EmprestimoItem item) {
        itens.remove(item);
        item.setEmprestimo(null);
    }
}