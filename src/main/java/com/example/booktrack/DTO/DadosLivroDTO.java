package com.example.booktrack.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivroDTO(
    Long id,
    @JsonAlias("title") String titulo,
    @JsonAlias("languages") List<String> idioma,
    @JsonAlias("authors") List<DadosAutorDTO> autor,
    @JsonAlias("download_count") int downloads
){
    
}
