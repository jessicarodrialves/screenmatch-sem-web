package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

//implements CommandLineRunner é uma interface que permite que eu crie algumas chamadas simples
//antes do sistema completo rodar por exemplo limpar os caches/arquivos temporários ou criar alguma pasta entre outros.

public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Nessa parte estamos instanciando a Classe ConsumoAPI e utilizando o metodo ObterDados dessa forma conseguimos
		//usar o metodo para varias chamadas de API exemplo abaixo: API FILME E API GOVERNO

		ConsumoAPI consumioApi = new ConsumoAPI();
		var json = consumioApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=9420c057" );
		System.out.println(json);

		//Realiza a conversão dos dados e mostra somente os dados mapeados
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados =  conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

//		json = consumioApi.obterDados("https://servicodados.ibge.gov.br/api/v1/localidades/municipios/1600303");
//		System.out.println(json);
		json = consumioApi.obterDados("https://www.omdbapi.com/?t=suits&season=1&episode=1&apikey=9420c057");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);


	}
}
