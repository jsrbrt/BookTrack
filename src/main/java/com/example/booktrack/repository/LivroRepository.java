package com.example.booktrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booktrack.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
