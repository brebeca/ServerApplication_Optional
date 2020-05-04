package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null ;
        try {
            Game game=new Game();
            serverSocket = new ServerSocket(8000);
            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread( socket,"name",game ).start();
            }
        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }

    }
}
