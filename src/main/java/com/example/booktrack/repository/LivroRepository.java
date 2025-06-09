package com.example.booktrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.booktrack.model.Autor;
import com.example.booktrack.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    @Query("SELECT l FROM Livro l")
    List<Livro> retornarLivros();

    @Query("SELECT DISTINCT a FROM Livro l JOIN l.autor a JOIN FETCH a.livros")
    List<Autor> retornarAutores();

    @Query("SELECT DISTINCT a FROM Livro l JOIN l.autor a JOIN FETCH a.livros WHERE a.nascimento <= :ano AND a.morte >= :ano")
    List<Autor> retornarAutoresVivos(int ano);

    @Query("SELECT l FROM Livro l WHERE l.lingua = :lingua")
    List<Livro> retornarLivrosPorLingua(String lingua);
}
