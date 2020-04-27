package org.example;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )  {
        try {
            ClientThread clientThread = new ClientThread(new GameServer());
            clientThread.communication();
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}
