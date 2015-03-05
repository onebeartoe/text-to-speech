
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import org.onebeartoe.network.TextHttpHandler;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author Roberto Marquez
 */
public abstract class Emic2HttpHandler extends TextHttpHandler
{
    protected TextToSpeech emic2;
    
    public Emic2HttpHandler(TextToSpeech emic2)
    {
        this.emic2 = emic2;
    }
}
