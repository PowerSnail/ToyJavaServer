import java.net.*;
import java.io.*;
 
public class KKMultiServerThread extends Thread {
    private Socket socket = null;
 
    public KKMultiServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }
     
    public void run() {
 
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            // KnockKnockProtocol kkp = new KnockKnockProtocol();
            MyProtocol myP = new MyProtocol();
            // outputLine = kkp.processInput(null);
            outputLine = myP.processInput(null);
            out.println(outputLine);
 
            while ((inputLine = in.readLine()) != null) {
                outputLine = myP.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
