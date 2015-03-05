
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class VoiceHttpHandler extends Emic2HttpHandler
{
    public VoiceHttpHandler(TextToSpeech emic2)
    {
        super(emic2);
    }

    @Override
    protected String getHttpText(HttpExchange exchange)
    {
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();
        
        int i = path.lastIndexOf("/") + 1;
        String s = path.substring(i);
        int id = Integer.valueOf(s);
        
        String response = "setting voice: " + id;
        try
        {
            if(emic2 == null)
            {
                System.out.println("emic2 is null.  Is it instanciated in the constuctor?");
            }
            else
            {
                emic2.setVoice(id);
                
//String message = "All your bases are belong to us.";                        
//emic2.speak(message);
                emic2.currentTime();
            }
            
            response += " - okay";                
        } 
        catch (Exception ex)
        {
            response += " - not okay";
            
            ex.printStackTrace();
        }
        
        return response;
    }
}
