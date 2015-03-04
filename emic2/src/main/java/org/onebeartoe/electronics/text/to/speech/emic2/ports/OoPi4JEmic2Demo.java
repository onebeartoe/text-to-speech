
package org.onebeartoe.electronics.text.to.speech.emic2.ports;

import java.io.IOException;
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
        System.out.println("Starting...");
        
        main = new Pi4JEmic2();
                
//        main.demoMessage(35254);
        Thread.sleep(1000);
        
        playAudio();
        
        Thread.sleep(3000);
        
//TODO: uncomment the body of this method
        textToSpeach();
        
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

    private static void playAudio() throws IOException, InterruptedException
    {
        System.out.println("playing some audio");

        main.speak(null);

        Thread.sleep(500);

        // Sing a song
        main.demoMessage(3525);
    }

//TODO: Uncomment the body of this method    
    public static void textToSpeach()
    {
        System.out.println("text to speech");

        // Speak some text
//        serial.write( "S".getBytes() );
//        serial.write("This is the text to speach sample.".getBytes());  // Send the desired string to convert to speech
//        serial.write( "\n".getBytes() );
//
//        System.out.println("text to speech done");
//
//        while (serial.read() != ':');   // Wait here until the Emic 2 responds with a ":" indicating it's ready to accept the next command        
        
        System.out.println("leaving text to speech");
    }
}
