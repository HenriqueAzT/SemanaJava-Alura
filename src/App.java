import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // Conexão HTTP - Top 250 filmes

        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        
        // Pegar só os dados dos filmes - Título, poster, classificação
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados

        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println();
            System.out.println("\u001b[38;2;255;31;0m" + filme.get("title") + "\u001b[0m");
            System.out.println("\u001b[38;2;42;122;228m" +filme.get("image") + "\u001b[0m");
            System.out.print("\u001b[33;1m" +filme.get("imDbRating")+"\u001b[0m ");
            Double x = Double.parseDouble(filme.get("imDbRating"));
            
            System.out.println("\n");
            System.out.println("-----------------------------------------------------------------");
        }
    }
}

