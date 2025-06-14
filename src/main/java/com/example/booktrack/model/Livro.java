package com.example.booktrack.model;

import com.example.booktrack.DTO.DadosLivroDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    private Long id;
    private String titulo;
    private String lingua;
    private int numDownloads;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro(){}

    public Livro(DadosLivroDTO dadosLivroDTO){
        this.id = dadosLivroDTO.id();
        this.titulo = dadosLivroDTO.titulo();
        this.lingua = String.join(", ", dadosLivroDTO.idioma());
        this.numDownloads = dadosLivroDTO.downloads();
    }
    
    public Autor getAutor() {
        return autor;
    }

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    @Override
    public String toString() {
        return "Livro [id=" + id + ", titulo=" + titulo + ", lingua=" + lingua + ", autor=" + autor.getNome() + "]";
    }
}
