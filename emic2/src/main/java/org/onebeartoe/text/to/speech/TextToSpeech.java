
package org.onebeartoe.text.to.speech;

/**
 * @author Roberto Marquez
 */
public interface TextToSpeech 
{
    void demoMessage(int id);
    
    String currentTime();
    
    void pause();
    
    void resume();
    
    void setLanguage(int id);
    
    void setSpeakingRate(int rate);
    
    void setVoice(int id);
    
    void speak(String text);
    
    void stop();
}
