package com.example.booktrack.model;

import java.util.List;

import com.example.booktrack.DTO.DadosLivroDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(List<DadosLivroDTO> results){}
