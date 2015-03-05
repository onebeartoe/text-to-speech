
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.pi4j.system.SystemInfo;
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
        TextToSpeech emic2 = null;
        
        String osName = SystemInfo.getOsName();
        System.out.println("OS Name           :  " + osName);
        if(osName.contains("Mac") ||
            osName.contains("Windows"))
        {
            System.out.println("The application is NOT running on Raspberry Pi.");
        }
        else
        {
            emic2 = new Pi4JEmic2();
        }

        InetSocketAddress anyhost = new InetSocketAddress(2110);        
        server = HttpServer.create(anyhost, 0);

        HttpHandler userInterfaceHttpHander = new ClasspathResourceHttpHandler();
        HttpHandler demoHttpHandler = new DemoMessageHttpHandler(emic2);
        HttpHandler quitHttpHandler = new EndOfRunHttpHandler(server);
        HttpHandler speakHttpHandler = new SpeakHttpHandler(emic2);
        HttpHandler stopPlaybackHttpHandler = new StopPlaybackHttpHandler(emic2);
        HttpHandler timeHttpHandler = new TimeHttpHandler(emic2);
        HttpHandler voiceHttpHandler = new VoiceHttpHandler(emic2);

        server.createContext("/", userInterfaceHttpHander);
        server.createContext("/demo/", demoHttpHandler);
        server.createContext("/quit", quitHttpHandler);
        server.createContext("/speak", speakHttpHandler);
        server.createContext("/stop", stopPlaybackHttpHandler);
        server.createContext("/time", timeHttpHandler);
        server.createContext("/voice/", voiceHttpHandler);
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
