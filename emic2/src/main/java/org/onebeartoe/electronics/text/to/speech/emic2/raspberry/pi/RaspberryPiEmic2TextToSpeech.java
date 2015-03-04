
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import org.onebeartoe.network.ClasspathResourceHttpHandler;
import org.onebeartoe.network.EndOfRunHttpHandler;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class RaspberryPiEmic2TextToSpeech 
{
    private HttpServer server;
    
    public RaspberryPiEmic2TextToSpeech() throws IOException
    {
        TextToSpeech emic2 = new Pi4JEmic2();
        
        InetSocketAddress anyhost = new InetSocketAddress(2110);        
        server = HttpServer.create(anyhost, 0);
        
        HttpHandler userInterfaceHttpHander = new ClasspathResourceHttpHandler();
        HttpHandler quitHttpHandler = new EndOfRunHttpHandler(server);
        HttpHandler speakHttpHandler = new SpeakHttpHandler(emic2);
        
        server.createContext("/", userInterfaceHttpHander);
        server.createContext("/speak", speakHttpHandler);
        server.createContext("/quit", quitHttpHandler);
    }
    public static void main(String[] args) throws InterruptedException, IOException
    {
        RaspberryPiEmic2TextToSpeech app = new RaspberryPiEmic2TextToSpeech();
        app.startServer();
    }
    
    public void startServer()
    {
        server.start();
    }
}
