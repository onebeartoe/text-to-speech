package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.sun.net.httpserver.HttpExchange;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public class WordsPerMinuteHttpHandler extends Emic2HttpHandler
{
    public WordsPerMinuteHttpHandler(TextToSpeech emic2)
    {
        super(emic2);
    }

    @Override
    protected String getHttpText(HttpExchange exchange)
    {
        return "okay";
    }    
}
