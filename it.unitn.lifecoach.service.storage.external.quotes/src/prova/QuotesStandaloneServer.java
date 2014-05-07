package prova;

import java.io.IOException;

import javax.ws.rs.Path;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

@Path("/reststorage")
public class QuotesStandaloneServer
{
    public static void main(String[] args) throws IllegalArgumentException, IOException
    {
    	String protocol = "http://";
        String port = ":5929/";
        String hostname = "localhost";
        
        String baseUrl = protocol + hostname + port;

        final HttpServer server = HttpServerFactory.create(baseUrl);
        server.start();
        System.out.println("server starts on " + baseUrl + "\n [kill the process to exit]");
    }
}
