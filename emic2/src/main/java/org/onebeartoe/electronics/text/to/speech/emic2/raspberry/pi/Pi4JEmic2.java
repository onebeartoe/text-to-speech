
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import java.util.Calendar;
import org.onebeartoe.system.Sleeper;
import org.onebeartoe.text.to.speech.TextToSpeech;

/**
 * @author lando
 */
public class Pi4JEmic2 implements TextToSpeech
{
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;
    
    private Serial serial;
    
    public Pi4JEmic2()
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
    
    @Override
    public void speak(String text) 
    {
        // Speak some text
        serial.write( "S".getBytes() );
        serial.write(text.getBytes());
        serial.write( "\n".getBytes() );

        System.out.println("done playing audio");

        while (serial.read() != ':');   // Wait here until the Emic 2 responds with a ":" indicating it's ready to accept the next command
    }

    @Override
    public String currentTime() 
    {
        Calendar now = Calendar.getInstance();
        
        int hour = now.get(Calendar.HOUR);
        
        int minute = now.get(Calendar.MINUTE);
        
        String spokenMeridian = "what the";
        int meridian = now.get(Calendar.AM_PM);
        if(meridian == Calendar.AM)
        {
            spokenMeridian = "A M";
        }
        else if(meridian == Calendar.PM)
        {
            spokenMeridian = "P M";
        }

        String text = "The time is now: " + hour + " " + minute + " " + spokenMeridian;
        
        System.out.println("speaking date: " + text);
        
        speak(text);
        
        return text;
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method sends the caller's message to the Emic2 device.
     * 
     * It takes care of sending the '\n' end-of-command character, if it is not 
     * present.
     * 
     * It also consumes the Emic2's response character, '\n'.
     * 
     * For valid commands see the documentation: http://www.parallax.com/sites/default/files/downloads/30016-Emic-2-Text-To-Speech-Documentation-v1.1.pdf
     * 
     * @param message The message to send the Emic2 device.
     */
    private void sendCommand(String command)
    {
        System.out.println("sending command: " + command);
        
        String commandTerminator = "\n";
        
        if(command.endsWith(commandTerminator))
        {
            System.out.println("The command terminator was found, but not needed.");
        }
        else
        {
            command += commandTerminator;
        }
        
        serial.write(command.getBytes());
        
        System.out.println("sending command done: " + command);
        
        while (serial.read() != ':');
        
        System.out.println("leaving sendCommand()");
    }
    
    /**
     * @param id Valid values are 0, 1, 2
     */
    @Override
    public void setLanguage(int id)
    {
        String command = "L" + id;
        
        sendCommand(command);
    }    
    
    @Override
    public void setSpeakingRate(int rate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param id valid values are 0-8
     */
    @Override
    public void setVoice(int id) 
    {
        String command = "N" + id;
        
        sendCommand(command);
    }

    @Override
    public void stop() 
    {
        String command = "X";
                
        sendCommand(command);
    }

    /**
     * Sends a demo message command to the Emic2
     * The command is something like: 'D1\n'
     * @param id 
     */
    @Override
    public void demoMessage(int id) 
    {
        System.out.println("singing song");
        String command = "D" + id + "\n";
        serial.write(command.getBytes());
        System.out.println("singing song done");
        
        while (serial.read() != ':');
        
        System.out.println("leaving singSong()");
    }    
}