package com.example.booktrack.main;

import java.util.Optional;
import java.util.Scanner;

import com.example.booktrack.DTO.DadosAutorDTO;
import com.example.booktrack.DTO.DadosLivroDTO;
import com.example.booktrack.model.Autor;
import com.example.booktrack.model.DadosLivro;
import com.example.booktrack.model.Livro;
import com.example.booktrack.repository.LivroRepository;
import com.example.booktrack.service.ConsomeApi;
import com.example.booktrack.service.ConverteDados;

public class MainApp {
    private LivroRepository repository;
    private Scanner scan = new Scanner(System.in);
    private ConsomeApi consumo = new ConsomeApi();
    private ConverteDados conversor = new ConverteDados();
    private String endereco = "https://gutendex.com/books/";
    
    public MainApp(LivroRepository repository){
        this.repository = repository;
    }

    public void showMenu() {
        var menu = 
        """
        Escolha uma opção:
        1 - Buscar livro por título
        2 - Listar livros registrados
        3 - Listar autores registrados
        4 - Listar autores vivos em um determinado ano
        5 - Listar livros em um determinado idioma
        
        0 - Sair
        """;

        int opcao;
        do {
            System.out.println(menu);
            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLivrosPorIdioma();
            }
        } while (opcao != 0);
    }

    private void listarLivrosPorIdioma() {

    }

    private void listarAutoresVivos() {

    }

    private void listarAutores() {

    }

    private void listarLivros() {

    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro:");
        String nomeLivro = scan.nextLine(); 
        var json = consumo.obterDados(endereco + "?search=" + nomeLivro.replace(" ", "+"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);

        Optional<DadosLivroDTO> dadosLivro = dados.results().stream().findFirst();

        if (dadosLivro.isPresent()) {
            DadosLivroDTO livroEncontrado = dadosLivro.get();

            System.out.println("---- Livro ----");
            System.out.println("Título: " + livroEncontrado.titulo());
            livroEncontrado.autor().forEach(a -> System.out.println("Autor: " + a.nome()));
            System.out.println("Idioma: " + livroEncontrado.idioma());
            System.out.println("Número de downloads: " + livroEncontrado.downloads());

            DadosAutorDTO dto = livroEncontrado.autor().get(0);
            System.out.println("Autor DTO - nome: " + dto.nome());
            System.out.println("Autor DTO - nascimento: " + dto.nascimento());
            System.out.println("Autor DTO - morte: " + dto.morte());

            Autor autor = new Autor(dto);
            System.out.println("Autor entity - nome: " + autor.getNome());
            Livro livro = new Livro(livroEncontrado);

            livro.setAutor(autor);

            repository.save(livro);
        }
    }
}
