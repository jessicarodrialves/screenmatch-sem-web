package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//a anotação @JsonAlias é como um apelido que estou dando para meu atributo
//é necessário ter a mesma nomenclatura dos dados do JSON para desserializar ou seja
//separar e pegar somente os dados que preciso.

// a anotação @JsonProperty ele é usado para desserealizar e serializar também ou seja
// ele sobreescreve o meu atributo pelo apelido que passei.

// a anotação JsonIgnoreProperties(ignoreUnknown = true) ignora tudo o que não estiver declarado nessa classe
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao ){

}


