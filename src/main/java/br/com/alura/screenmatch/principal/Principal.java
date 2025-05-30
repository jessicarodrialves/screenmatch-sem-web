package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

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

        // Esse trecho percorre as temporadas e acessa cada episodio e exibe o título
//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j <episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        // Esse trecho percorre as temporadas e acessa cada episodio e exibe o título assim como o trecho acima, no entanto
        // é utilizado expressão lambda, ou seja o código fica mais limpo, mais clean, e mais sucinto,
        // toda execução é feita de forma otimizada
        temporadas.forEach(t-> t.episodios().forEach(e-> System.out.println(e.titulo())));

        //Utilizando o Stream para percorrer somente uma lista
        //**FlatMap realiza a buscar de outra lista para trazer os dados para lista atual
        //** .toList trás os dados para uma lista imutavel
        // ou seja não conseguimos modficar a estrutura da lista caso tenha necessidade de incluir mais dados na lista

        //Esse metodo realiza a busca dos TOP 5 Episódios
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
                //.toList();
        System.out.println("\nTop 5 episodios: ");
        dadosEpisodios.stream()
                .filter(e-> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
