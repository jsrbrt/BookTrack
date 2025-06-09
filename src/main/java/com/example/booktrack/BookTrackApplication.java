package com.example.booktrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.booktrack.main.MainApp;
import com.example.booktrack.repository.LivroRepository;

@SpringBootApplication
public class BookTrackApplication implements CommandLineRunner{
	@Autowired
	private LivroRepository repositorio;
	public static void main(String[] args) {
		SpringApplication.run(BookTrackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainApp main = new MainApp(repositorio);
		main.showMenu();
	}
}
