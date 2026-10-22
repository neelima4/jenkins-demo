import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebApp {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0);

        server.createContext("/", exchange -> {
            String response = "Hello from Jenkins Docker CI/CD!";

            byte[] bytes = response.getBytes(
                StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, bytes.length);

            try (var output = exchange.getResponseBody()) {
                output.write(bytes);
            }
        });

        server.start();
        System.out.println("Web application started on port 8080");
    }
}
