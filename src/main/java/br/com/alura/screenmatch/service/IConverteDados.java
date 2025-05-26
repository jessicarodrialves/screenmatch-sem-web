package br.com.alura.screenmatch.service;

public interface IConverteDados {

    // É uma forma de receber qualquer dado generico

        <T> T obterDados(String json, Class<T> classe);
}
