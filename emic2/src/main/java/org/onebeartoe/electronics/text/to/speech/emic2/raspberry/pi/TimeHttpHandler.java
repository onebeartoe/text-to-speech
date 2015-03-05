
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import java.util.Calendar;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class TimeHttpHandler extends Emic2HttpHandler
{
    public TimeHttpHandler(TextToSpeech emic2)
    {
        super(emic2);
    }
    
    @Override
    protected String getHttpText(HttpExchange exchange)
    {        
        String spokenTime = emic2.currentTime();
        
        String response = "date spoken: " + spokenTime;
        
        System.out.println(response);
        
        return response;
    }
}
