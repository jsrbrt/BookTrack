package com.example.booktrack.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
