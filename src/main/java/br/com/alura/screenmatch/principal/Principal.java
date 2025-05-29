package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    Scanner recebeDados  = new Scanner(System.in);
    ConsumoAPI consumioApi = new ConsumoAPI();
    ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=9420c057";


    public void exibeMenu(){
        System.out.println("Informe o nome da série: ");
        String serie = recebeDados.nextLine();

        //Nessa parte estamos instanciando a Classe ConsumoAPI e utilizando o metodo ObterDados dessa forma conseguimos
        //usar o metodo para varias chamadas de API exemplo abaixo: API FILME E API GOVERNO
        //json = consumioApi.obterDados("https://servicodados.ibge.gov.br/api/v1/localidades/municipios/1600303");

        var json = consumioApi.obterDados(ENDERECO+serie.replace(" ", "+").toUpperCase()+API_KEY );
        System.out.println(json);

        //Realiza a conversão dos dados e mostra somente os dados mapeados
        DadosSerie dados =  conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

//        json = consumioApi.obterDados("https://www.omdbapi.com/?t="+serie+"&season=1&episode=1&apikey=9420c057");
//        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
//        System.out.println(dadosEpisodio);
        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= dados.totalTemporadas();i++ ){

            json = consumioApi.obterDados(ENDERECO+serie.replace(" ", "+").toUpperCase()+"&season="+i+ API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
            System.out.println(dadosTemporada);
        }
        //Esse forEach realiza o loop dentro da lista temporadas e ja realiza a impressão dos dados na tela
        // Realiza a mesma impressão da linha 52 **** System.out.println(dadosTemporada);****
        //temporadas.forEach(System.out::println);

    }
}
