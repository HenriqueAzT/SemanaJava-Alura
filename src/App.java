import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {

    public static final String RESET = "\033[0M";

    public static void main(String[] args) throws Exception {
        
       // Conexão HTTP
       
        // String url = "https://api.mocki.io/v2/549a5d8b";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/NASA-APOD.json";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        String url = "http://localhost:8080/linguagens";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        //Exibir e manipular os dados
        
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (Conteudo conteudo : conteudos) {
           
            InputStream InputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = conteudo.getTitulo() + ".png";

            geradora.cria(InputStream, nomeArquivo);

            System.out.println("\u001b[38;2;255;31;0m" + "Titulo: " + conteudo.getTitulo() + "\u001b[0m");
            System.out.println();
            
        }
        
    }
 
    
}