package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication

//implements CommandLineRunner é uma interface que permite que eu crie algumas chamadas simples
//antes do sistema completo rodar por exemplo limpar os caches/arquivos temporários ou criar alguma pasta entre outros.

public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner recebeDados  = new Scanner(System.in);
		System.out.println("Informe o nome da série: ");
		String serie = recebeDados.nextLine();

		//Nessa parte estamos instanciando a Classe ConsumoAPI e utilizando o metodo ObterDados dessa forma conseguimos
		//usar o metodo para varias chamadas de API exemplo abaixo: API FILME E API GOVERNO

		ConsumoAPI consumioApi = new ConsumoAPI();
		var json = consumioApi.obterDados("https://www.omdbapi.com/?t="+serie+"&apikey=9420c057" );
		System.out.println(json);

		//Realiza a conversão dos dados e mostra somente os dados mapeados
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados =  conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

//		json = consumioApi.obterDados("https://servicodados.ibge.gov.br/api/v1/localidades/municipios/1600303");
//		System.out.println(json);
		json = consumioApi.obterDados("https://www.omdbapi.com/?t="+serie+"&season=1&episode=1&apikey=9420c057");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dados.totalTemporadas();i++ ){
			json = consumioApi.obterDados("https://www.omdbapi.com/?t="+serie+"&season="+i+"&apikey=9420c057");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
			System.out.println(dadosTemporada);
		}
	//Esse forEach realiza o loop dentro da lista temporadas e ja realiza a impressão dos dados na tela
	// Realiza a mesma impressão da linha 52 **** System.out.println(dadosTemporada);****
	//temporadas.forEach(System.out::println);
	}
}
