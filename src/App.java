import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App {

    public static final String RESET = "\033[0M";

    public static void main(String[] args) throws Exception {
        
       // Conexão HTTP
       
        System.out.println("O que você deseja ver?");
        System.out.println("1- Top 250 Filmes\n2- Top 250 Series");
        Scanner pegarOpcao = new Scanner(System.in);
        Integer opcaoEscolhida = pegarOpcao.nextInt();

        String url = GerarUrl.setUrl(opcaoEscolhida);

        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Pegar só os dados dos filmes - Título, poster, classificação

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        //Exibir e manipular os dados
        
        var geradora = new GeradoraDeFigurinhas();

        for (Map<String,String> filme : listaDeFilmes) {
           
            String urlImagem = filme.get("image");
            String tituloFilme = filme.get("title");
            float nota = Float.parseFloat(filme.get("imDbRating"));
            
            InputStream ist = new URL(urlImagem).openStream();
            String nomeArquivo = tituloFilme + ".png";

            geradora.cria(ist, nomeArquivo, nota);

            System.out.println("\u001b[38;2;255;31;0m" + "Titulo: " + tituloFilme + "\u001b[0m");
            System.out.println();
            System.out.println("\u001b[38;2;42;122;228m" + "Avaliação: " + nota + "\u001b[0m");
            System.out.println();
            
        }
        pegarOpcao.close();
    }
 
    
}