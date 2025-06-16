package br.com.biopark.BibliotecaVirtual.dto;

import java.util.List;

// DTO (Data Transfer Object) para a requisição de criação de empréstimo
// Usado para receber os dados do frontend de forma estruturada.
public class CriarEmprestimoRequest {
    private Long usuarioId; // ID do usuário que fará o empréstimo
    private List<Long> livroIds; // Lista de IDs dos livros a serem emprestados

    // Construtor padrão
    public CriarEmprestimoRequest() {
    }

    // Construtor com todos os campos
    public CriarEmprestimoRequest(Long usuarioId, List<Long> livroIds) {
        this.usuarioId = usuarioId;
        this.livroIds = livroIds;
    }

    // Getters e Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getLivroIds() {
        return livroIds;
    }

    public void setLivroIds(List<Long> livroIds) {
        this.livroIds = livroIds;
    }

    @Override
    public String toString() {
        return "CriarEmprestimoRequest{" +
                "usuarioId=" + usuarioId +
                ", livroIds=" + livroIds +
                '}';
    }
}
