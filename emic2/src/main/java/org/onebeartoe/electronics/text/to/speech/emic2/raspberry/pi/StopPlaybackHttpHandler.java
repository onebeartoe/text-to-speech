
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class StopPlaybackHttpHandler extends Emic2HttpHandler
{
    public StopPlaybackHttpHandler(TextToSpeech emic2)
    {
        super(emic2);
    }
    
    @Override
    protected String getHttpText(HttpExchange exchange)
    {
        String response = "stopping playback";
        try
        {
            if(emic2 == null)
            {
                System.out.println("emic2 is null.  Is it instanciated in the constuctor?");
            }
            else
            {
                emic2.stop();
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
