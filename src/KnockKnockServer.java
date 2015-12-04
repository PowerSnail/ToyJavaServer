import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Han on 12/4/2015.
 */
public class KnockKnockServer {


    public static void main(String[] args) {

       int portNum = Integer.parseInt(args[0]);
        try (
            ServerSocket serverSocket = new ServerSocket(portNum);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            )
        ) {
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if(outputLine.equals("Bye."))
                    break;
            }
        } catch(IOException e) {
		System.out.println("There is an IO problem");
        }

    }
}
