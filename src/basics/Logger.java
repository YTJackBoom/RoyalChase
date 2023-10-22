package basics;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private PrintStream originalOut;
    private PrintStream originalErr;
    private PrintStream fileOut;

    public Logger(String filename) throws FileNotFoundException {
        // Keep the original streams.
        originalOut = System.out;
        originalErr = System.err;

        // Create the logging file stream.
        fileOut = new PrintStream(new FileOutputStream(filename, true));

        // Write timestamp to the log file
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileOut.println("\n" + formatter.format(new Date()) + "\n");

        // Set up the new system streams.
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) throws IOException {
                originalOut.write(b);
                fileOut.write(b);
            }
        }));

        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) throws IOException {
                originalErr.write(b);
                fileOut.write(b);
            }
        }));
    }

    public void close() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        fileOut.close();
    }
}
