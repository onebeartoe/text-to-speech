
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi.ports;

import org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi.Pi4JEmic2;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * This is an object oriented refactoring of the program seen in Pi4JEmic2Demo.java
 *
 * @author Roberto Marquez
 */
public class OoPi4JEmic2Demo
{
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    
    private static TextToSpeech main;

    public static void main(String[] args) throws Exception
    {
        System.out.println("begin run...");
        
        main = new Pi4JEmic2();
        Thread.sleep(1000);
        
        System.out.println("playing some audio");

        String greetingAndIntro = "Hello. My name is the Emic 2 Text-to-Speech module. I would like to sing you a song.";
        main.speak(greetingAndIntro);

        Thread.sleep(500);

        // Sing a song
//TODO: chage the 1 to a constant        
        main.demoMessage(1);
        
        Thread.sleep(2000);
        main.speak("Thanks for listening.");
        
        Thread t = new Thread()
        {
            public void run()
            {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try
                {
                    Thread.sleep(10, 000);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
        };
        t.start();
        System.out.println("Started");
    }
}
