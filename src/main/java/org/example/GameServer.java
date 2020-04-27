package org.example;

import javax.lang.model.element.AnnotationValue;
import java.io.IOException;
import java.net.ServerSocket ;
import java.net.Socket;

public class GameServer {

    ServerSocket serverSocket;
    Socket socket;

    /**
     * initializeaza soketul pt portul 8000
     * @throws IOException
     */
    GameServer() throws IOException {
        serverSocket=new ServerSocket(8000);
    }

    /**
     * face serverul sa atepte conexiuni
     * @throws IOException
     */
    public  void  accept() throws IOException {
        System.out.println("va astepta conexiune");
        socket = serverSocket.accept();
        System.out.println("[serverul] s a conectat un client");
    }

    /**
     * inchide soketul
     * @throws IOException
     */
    public void cloase() throws IOException {
        serverSocket.close();
    }

    /**
     * returneaza soketul initializat
     * @return
     */
    public Socket getSocket() {
        return socket;
    }
}
