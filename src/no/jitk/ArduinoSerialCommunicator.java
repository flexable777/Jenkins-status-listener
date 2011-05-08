package no.jitk;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

@Component
public class ArduinoSerialCommunicator {

    public enum Mode {
        PRODUCTION(14),
        TEST(16);

        private int code;

        Mode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final int ERROR_CODE = 1;

    private SerialPort serialPort;

    private OutputStream output;

    //Milliseconds to block while waiting for port open
    private static final int TIME_OUT = 3000;

    //Default bits per second for COM port.
    private static final int DATA_RATE = 9600;

    public ArduinoSerialCommunicator() {
        init();
    }

    private void init() {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");

            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Another owner: " + portIdentifier.getCurrentOwner());
            }

            // open serial port
            serialPort = (SerialPort) portIdentifier.open("Arduino", TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            output = serialPort.getOutputStream();

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void send(int status) {

        System.out.println("write to Arduino: " + status);

        try {
            output.write(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public void close() {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    public void notifyArduino(JenkinsStatus status) {

        System.out.println("Receiving status from Jenkins: " + status);

        //When phase is finished, check status and send to Arduino

        if (JenkinsStatus.Phase.FINISHED.equals(status.getBuild().getPhase())) {
            if (JenkinsStatus.Status.SUCCESS.equals(status.getBuild().getStatus())) {
                System.out.println("sending SUCCESS to Arduino");
                send(JenkinsStatus.Status.SUCCESS.getCode());
            } else if (JenkinsStatus.Status.FAILURE.equals(status.getBuild().getStatus())) {
                System.out.println("sending FAILED to Arduino");
                send(JenkinsStatus.Status.FAILURE.getCode());
            } else if (JenkinsStatus.Status.ABORTED.equals(status.getBuild().getStatus())) {
                System.out.println("sending ABORTED to Arduino");
                send(JenkinsStatus.Status.ABORTED.getCode());
            } else {
                //Send error report to Arduino to indicate something is wrong with code
                //send(ERROR_CODE);
            }
        }
        if (JenkinsStatus.Phase.STARTED.equals(status.getBuild().getPhase())) {
            System.out.println("sending STARTED to Arduino");
            send(JenkinsStatus.Phase.STARTED.getCode());
        }
    }

    public void notifyArduino(int status) {
        System.out.println("Receiving status from web: " + status);
        send(status);
    }


    //Test code
    public static void main(String[] args) {
        ArduinoSerialCommunicator arduinoSerialCommunicator = new ArduinoSerialCommunicator();
        try {
            // Wait for init
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 1; i++) {

                arduinoSerialCommunicator.send(JenkinsStatus.Status.FAILURE.getCode());

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                arduinoSerialCommunicator.send(JenkinsStatus.Status.SUCCESS.getCode());

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            arduinoSerialCommunicator.close();
        }
    }
}
