    package com.example.booktrack.model;

    import java.util.ArrayList;
    import java.util.List;

    import com.example.booktrack.DTO.DadosAutorDTO;

    import jakarta.persistence.CascadeType;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.OneToMany;
    import jakarta.persistence.Table;

    @Entity
    @Table(name = "autores")
    public class Autor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private int nascimento;
        private int morte;
        
        @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
        private List<Livro> livros = new ArrayList<>();
        
        public Autor(){}
        
        public Autor(DadosAutorDTO dadosAutorDTO) {
            this.nome = dadosAutorDTO.nome();
            this.nascimento = dadosAutorDTO.nascimento();
            this.morte = dadosAutorDTO.morte();
        }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public int getNascimento() {
            return nascimento;
        }
        public void setNascimento(int nascimento) {
            this.nascimento = nascimento;
        }
        public int getMorte() {
            return morte;
        }
        public void setMorte(int morte) {
            this.morte = morte;
        }
    }
