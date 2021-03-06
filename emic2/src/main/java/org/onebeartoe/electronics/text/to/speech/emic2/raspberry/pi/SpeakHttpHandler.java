
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class SpeakHttpHandler extends Emic2HttpHandler
{
    public SpeakHttpHandler(TextToSpeech emic2)
    {
        super(emic2);
    }
    
    @Override
    protected String getHttpText(HttpExchange exchange)
    {
        URI requestURI = exchange.getRequestURI();

        String text = requestURI.getQuery();
        
        System.out.println("playing: " + text);
        
        emic2.speak(text);
        
        String response = " played: " + text;
        
        System.out.println(response);
        
        return response;
    }
}
