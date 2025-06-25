package br.com.biopark.BibliotecaVirtual.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class CriarEmprestimoRequest {
    private Long usuarioId;
    private List<Long> livroIds;
}