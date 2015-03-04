
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import org.onebeartoe.network.TextHttpHandler;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class SpeakHttpHandler extends TextHttpHandler
{
    private TextToSpeech emic2;
    
    public SpeakHttpHandler(TextToSpeech emic2)
    {
        this.emic2 = emic2;
    }
    
    @Override
    protected String getHttpText(HttpExchange exchange)
    {
        URI requestURI = exchange.getRequestURI();

        String text = requestURI.getQuery();
        
        System.out.println("playing: " + text);
        
        emic2.speak(text);
        
        String response = "text played: " + text;
        
        System.out.println(response);
        
        return response;
    }
}
