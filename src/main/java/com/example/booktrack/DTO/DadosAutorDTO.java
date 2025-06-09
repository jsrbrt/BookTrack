package com.example.booktrack.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAutorDTO(
    @JsonAlias("name") String nome,
    @JsonAlias("birth_year")int nascimento,
    @JsonAlias("death_year")int morte
) {

}
