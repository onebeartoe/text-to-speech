
package org.onebeartoe.electronics.text.to.speech.emic2.raspberry.pi.ports;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.onebeartoe.system.Sleeper;

/**
 * This, as is, does not play any thing :(
 * It my be as simple as removing that first read for the ':' character, as that is 
 * what worked for the Pi4J implementation.
 * 
 * java -Djava.library.path=/usr/lib/jni -jar
 * arduino-serial-communication-1.0-SNAPSHOT-jar-with-dependencies.jar
 *
 * sudo java -Djava.library.path=/usr/lib/jni -jar
 * arduino-serial-communication-1.0-SNAPSHOT-jar-with-dependencies.jar
 */
/**
 * This was originally the code found at
 * thtp://playground.arduino.cc/Interfacing/Java
 *
 * @author Roberto Marquez
 */
public class RxTxEmic2Demo implements SerialPortEventListener
{

    private SerialPort serialPort;

    /**
     * The port we're normally going to use.
     */
    private static final String[] PORT_NAMES =
    {
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Raspberry Pi
        "/dev/ttyAMA0", // Raspberry Pi Serial Pins
        "/dev/ttyUSB0", // Linux
        "COM3", // Windows
    };

    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    private BufferedReader input;

    private OutputStream output;

    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;

    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;

    public void initialize()
    {
        // the next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
//        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyAMA0");

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            System.out.println("port ID: " + currPortId);
            for (String portName : PORT_NAMES)
            {
                System.out.println("trying: " + portName);
                if (currPortId.getName().equals(portName))
                {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null)
        {
            System.out.println("Could not find COM port.");
            return;
        }

        try
        {
  //          Serial serial = SerialFactory.createInstance();
            
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            
            
            Sleeper.sleepo(1000);

            System.out.println("serial port paramerters");
            
            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            
            Sleeper.sleepo(1000);
                        
            // add event listeners
//            serialPort.addEventListener(this);
//            serialPort.notifyOnDataAvailable(true);
            
            System.out.println("input stream");
            // open the streams
            InputStream is = serialPort.getInputStream();            
            InputStreamReader isr = new InputStreamReader(is);
            input = new BufferedReader(isr);

            Thread.sleep(1000);
            System.out.println("getting output stream...");
            output = serialPort.getOutputStream();
            System.out.println("output stream obtained");
            

            System.out.println("write initial CR code");
            /*
             When the Emic 2 powers on, it takes about 3 seconds for it to successfully
             initialize. It then sends a ":" character to indicate it's ready to accept
             commands. If the Emic 2 is already initialized, a CR will also cause it
             to send a ":"
             */
            output.write('\n');             // Send a CR in case the system is already up
            output.flush();
            
            System.out.println("reading initial CR code response");
            while (input.read() != ':')
            {
                // When the Emic 2 has initialized and is ready, it will send a single ':' character, so wait here until we receive it
                System.out.println("waiting for iniital CR resonse");
                
                Thread.sleep(2000);
            }
//            Thread.sleep(10);
            Thread.sleep(10);                          // Short delay

            System.out.println("output flush 1");
            output.flush();                 // Flush the receive buffer

//            // add event listeners
//            serialPort.addEventListener(this);
//            serialPort.notifyOnDataAvailable(true);
            
            System.out.println("serial communication obtained");
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close()
    {
        if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        System.out.println("a serial event was received: " + oEvent.getEventType() );
        
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                playAudio();
//                String inputLine = input.readLine();
//                System.out.println(inputLine);
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting...");
        RxTxEmic2Demo main = new RxTxEmic2Demo();
        main.initialize();
        main.playAudio();
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
        output.write('S');
        output.write("Hello. Would you like to play a game?".getBytes());  // Send the desired string to convert to speech
        output.write("Hello. My name is the Emic 2 Text-to-Speech module. I would like to sing you a song.".getBytes());  // Send the desired string to convert to speech
        output.write('\n');
        
        output.flush();

        System.out.println("done playing audio");
        
        while (input.read() != ':');   // Wait here until the Emic 2 responds with a ":" indicating it's ready to accept the next command

        Thread.sleep(500);

        // Sing a song
        output.write("D1\n".getBytes());

        while (input.read() != ':');
    }
}
