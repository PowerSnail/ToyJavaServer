import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Han on 12/4/2015.
 */
public class KnockKnockClient {

    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);


        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
                        true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                Scanner sc = new Scanner(System.in) ;
                String fromUser = sc.nextLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }

            }

        } catch (IOException e) {

        }


    }
}
