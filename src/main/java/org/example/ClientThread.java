package org.example;

import java.io.*;
import java.net.Socket;

public class ClientThread {
    Socket socket;
    GameServer gameServer;
    OutputStream toClient;
    InputStream fromClient;
    PrintWriter writer ;
    BufferedReader reader ;

    /**
     * aici se intitializeaza socketul cu cel din GameServer si toate celelate clase nesesare citrii si crriei in socket
     * @param g
     * @throws IOException
     */
    ClientThread(GameServer g) throws IOException {
        gameServer=g;
        g.accept();
        socket=g.getSocket();

        toClient= socket.getOutputStream();
        fromClient = socket.getInputStream();
         writer = new PrintWriter(toClient, true);
         reader = new BufferedReader(new InputStreamReader(fromClient));
    }

    /**
     * se citeste din socket
     * daca comanda este exit iese bucla dupa ce se incgide socketul
     * atfel trimite/ scrie in socket "Server received the request "+ ce s a primit de la client
     * @throws IOException
     */
    public void communication() throws IOException {
        String line;
        while(true){
            line = reader.readLine();
            if(line.contains("exit")){
                writer.println("Server stoped ");
                gameServer.cloase();
                break;
            }
           else  writer.println("Server received the request "+line);
        }
    }
}
