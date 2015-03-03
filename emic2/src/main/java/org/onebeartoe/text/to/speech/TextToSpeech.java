
package org.onebeartoe.text.to.speech;

/**
 * @author Roberto Marquez
 */
public interface TextToSpeech 
{
    void speak(String text);
    
    void currentTime();
    
    void pause();
    
    void resume();
    
    void setSpeakingRate(int rate);
    
    void setVoice(int id);
    
    void stop();
}
