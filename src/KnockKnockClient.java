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

        Socket kkSocket = null;

        try
        {
            kkSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("Failed setting up client socket\n" + e);
        }

        PrintWriter out = null;

        try
        {
            out = new PrintWriter(kkSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Failed setting up output stream\n" + e);
        }

        BufferedReader in = null;

        try
        {
            in = new BufferedReader(
                    new InputStreamReader(kkSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Failed setting up input stream\n" + e);
        }


            String fromServer;
        try
        {
            while ((fromServer = in.readLine()) != null)
            {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                Scanner sc = new Scanner(System.in);
                String fromUser = sc.nextLine();
                if (fromUser != null)
                {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }

            }
        } catch (IOException e) {
            System.out.println("There is a problem reading input stream\n" + e);
        }


    }
}
