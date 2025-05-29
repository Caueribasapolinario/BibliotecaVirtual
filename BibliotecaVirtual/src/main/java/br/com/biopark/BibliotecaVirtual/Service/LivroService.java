package br.com.biopark.BibliotecaVirtual.Service;

import br.com.biopark.BibliotecaVirtual.Model.LivroModel;
import br.com.biopark.BibliotecaVirtual.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public List<LivroModel> listarTodos(){
        return  livroRepository.findAll();
    }

    public LivroModel buscarPorId(Long id){
        return livroRepository.findById(id).orElse(null);

    }

    public LivroModel salvar(LivroModel livro){
        return livroRepository.save(livro);
    }

    public void deletar(Long id){
        livroRepository.deleteById(id);
    }
}
