package com.example.booktrack.main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.example.booktrack.DTO.DadosLivroDTO;
import com.example.booktrack.model.Autor;
import com.example.booktrack.model.DadosLivro;
import com.example.booktrack.model.Livro;
import com.example.booktrack.repository.LivroRepository;
import com.example.booktrack.service.ConsomeApi;
import com.example.booktrack.service.ConverteDados;

import jakarta.transaction.Transactional;

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
        System.out.println("Insira o idioma para realizar a busca:");
        System.out.println("es - espanhol");
        System.out.println("en - inglês");
        System.out.println("pt - português");
        System.out.println("fr - francês");
        String idioma = scan.nextLine();

        Optional<List<Livro>> livrosPorIdioma = repository.retornarLivrosPorLingua(idioma);
        if (livrosPorIdioma.isPresent()) {
            livrosPorIdioma.get().forEach(l -> printarLivro(l));
        } else{
            System.out.println("Não existem livros com esse idioma no banco de dados.");
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Insira o ano:");
        int ano = scan.nextInt();
        scan.nextLine();
        Optional<List<Autor>> autoresVivos = repository.retornarAutoresVivos(ano);

        if (autoresVivos.isPresent()) {
            autoresVivos.get().forEach(a -> printarAutor(a));
        } else {
            System.out.println("Nenhum autor vivo neste ano no banco de dados");
        }
    }

    @Transactional
    private void listarAutores() {
        repository.retornarAutores().forEach(a -> printarAutor(a));
    }

    private void listarLivros() {
        repository.findAll().forEach(l -> printarLivro(l));
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro:");
        String nomeLivro = scan.nextLine(); 

        Optional<Livro> livroExistente = repository.findByTituloContainingIgnoreCase(nomeLivro);

        if (livroExistente.isPresent()) {
            System.out.println("Esse livro já está no banco!");
            printarLivro(livroExistente.get());
            return;
        }

        var json = consumo.obterDados(endereco + "?search=" + nomeLivro.replace(" ", "+"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);

        Optional<DadosLivroDTO> dadosLivro = dados.results().stream().findFirst();

        if (dadosLivro.isPresent()) {
            DadosLivroDTO livroEncontrado = dadosLivro.get();
            Livro livro = new Livro(livroEncontrado);

            Optional<Autor> autorExistente = repository.retornarAutorByNome(livroEncontrado.autor().get(0).nome());

            if (autorExistente.isPresent()) {
                livro.setAutor(autorExistente.get());
            } else {
                livro.setAutor(new Autor(livroEncontrado.autor().get(0)));
            }
            repository.save(livro);
            printarLivro(livro);
        }
    }
    
    private void printarAutor(Autor autor){
        System.out.println("---- Autor ----");
        System.out.println("Autor: " + autor.getNome());
        System.out.println("Ano de nascimento: " + autor.getNascimento());
        System.out.println("Ano de morte: " + autor.getMorte());
        System.out.print("Livro(s): ");
        autor.getLivros().forEach(l -> System.out.print(l.getTitulo() + ", "));
        System.out.println("\n---------------");
    }

    private void printarLivro(Livro livro){
        System.out.println("---- Livro ----");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
        System.out.println("Idioma: " + livro.getLingua());
        System.out.println("Número de downloads: " + livro.getNumDownloads());
        System.out.println("---------------");
    }
}