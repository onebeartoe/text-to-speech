
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 *
 * @author Roberto Marquez
 */
public class DemoMessageHttpHandler extends Emic2HttpHandler
{
    private Logger logger;

    public DemoMessageHttpHandler(TextToSpeech emic2)
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
        
        String response = "play demo message " + id + ": ";
        try
        {
            if(emic2 == null)
            {
                System.out.println("emic2 is null.  Is it instanciated in the constuctor?");
            }
            else
            {
                switch(id)
                {
                    case 0:
                    {
                        String message = "Hello. My name is the Emic 2 Text-to-Speech module. I would like to sing you a song.";
                        emic2.speak(message);
                        
                        break;
                    }
                    case 1:
                    {
                        emic2.demoMessage(1);
                        
                        break;
                    }
                    case 2:
                    {
                        String message = "Yo quiero taco bell!";
                        
                        emic2.setLanguage(2);
                        emic2.speak(message);
                        emic2.setLanguage(0);
                        
                        break;
                    }
                    case 3:
                    {
                        String message = "All your bases are belong to us.";
                        
                        emic2.speak(message);
                        
                        break;
                    }
                    case 4:
                    {
                        
                        String message = "Shall we play a game?";
                        
                        emic2.speak(message);
                        
                        break;
                    }
                    default:
                    {
                        response += " DEMO MEESAGE NOT FOUND";
                    }
                }
                
                response += "okay";                
            }
        } 
        catch (Exception ex)
        {
            response += "not okay";
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return response;
    }
}
