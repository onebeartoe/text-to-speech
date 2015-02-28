package org.onebeartoe.electronics.emic2;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import java.io.IOException;
import org.onebeartoe.system.Sleeper;

/**
 * This was originally the code found at
 * http://playground.arduino.cc/Interfacing/Java
 *
 * @author Roberto Marquez
 */
public class Pi4JEmic2Demo
{

    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;

    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    private Serial serial;

    public void initialize()
    {
        try
        {

            serial = SerialFactory.createInstance();
            Sleeper.sleepo(5000);
            System.out.println("serial instanciated");

            // create and register the serial data listener
            serial.addListener(new SerialDataListener()
            {
                @Override
                public void dataReceived(SerialDataEvent event)
                {
                    // print out the data received to the console
                    System.out.println("Data was recieved: " + event.getData());
                }
            });
            System.out.println("serial listener added");
            Sleeper.sleepo(5000);
            
            // open the default serial port provided on the GPIO header
            serial.open(Serial.DEFAULT_COM_PORT, DATA_RATE);
            System.out.println("serial opened");
            Sleeper.sleepo(5000);

            System.out.println("writing initial CR code");
            /*
             When the Emic 2 powers on, it takes about 3 seconds for it to successfully
             initialize. It then sends a ":" character to indicate it's ready to accept
             commands. If the Emic 2 is already initialized, a CR will also cause it
             to send a ":"
             */
            serial.write("\n".getBytes());             // Send a CR in case the system is already up
            
            Sleeper.sleepo(5000);

            Thread.sleep(10);                          // Short delay

            System.out.println("output flush 1");
            serial.flush();                 // Flush the receive buffer

            System.out.println("serial communication obtained");

            // wait 1 second before continuing
            Thread.sleep(1000);

        }
        catch (Exception e)
        {
            System.out.println(" ==>> SETUP FAILED : " + e.getMessage());

            System.err.println(e.toString());
        }
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting...");
        Pi4JEmic2Demo main = new Pi4JEmic2Demo();
        main.initialize();
        
        main.singSong();
        Thread.sleep(1000);
        
        main.playAudio();
        
        Thread.sleep(3000);
        
        main.textToSpeach();
        
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

    private void playAudio() throws IOException, InterruptedException
    {
        System.out.println("playing some audio");

        // Speak some text
        serial.write( "S".getBytes() );
        serial.write("Hello. Would you like to play a game?".getBytes());  // Send the desired string to convert to speech
        serial.write("Hello. My name is the Emic 2 Text-to-Speech module. I would like to sing you a song.".getBytes());  // Send the desired string to convert to speech
        serial.write( "\n".getBytes() );

        System.out.println("done playing audio");

        while (serial.read() != ':');   // Wait here until the Emic 2 responds with a ":" indicating it's ready to accept the next command

        Thread.sleep(500);

        // Sing a song
        System.out.println("playing song");
        serial.write("D3\n".getBytes());
        System.out.println("playing song done");
        
        while (serial.read() != ':');
        
        System.out.println("leaving playAudio()");
    }
    
    private void singSong() throws IOException, InterruptedException
    {
        System.out.println("singing song");
        
        serial.write("D1\n".getBytes());
        System.out.println("singing song done");
        
        while (serial.read() != ':');
        
        System.out.println("leaving singSong()");
    }    

    public void textToSpeach()
    {
        System.out.println("text to speech");

        // Speak some text
        serial.write( "S".getBytes() );
        serial.write("This is the text to speach sample.".getBytes());  // Send the desired string to convert to speech
        serial.write( "\n".getBytes() );

        System.out.println("text to speech done");

        while (serial.read() != ':');   // Wait here until the Emic 2 responds with a ":" indicating it's ready to accept the next command        
        
        System.out.println("leaving text to speech");
    }
}
