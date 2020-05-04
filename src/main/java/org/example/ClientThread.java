package org.example;

import java.io.*;
import java.net.Socket;

public class ClientThread extends  Thread {
    Socket socket;
    Game game;
    OutputStream toClient;
    InputStream fromClient;
    PrintWriter writer ;
    BufferedReader reader ;
    Board board=null;
    Player player=null;
    /**
     * aici se intitializeaza socketul cu cel din GameServer si toate celelate clase nesesare citrii si crriei in socket
     * @param game
     * @throws IOException
     */
    ClientThread(Socket socket, String name, Game game) throws IOException {
        this.game=game;
       // g.accept();
        socket=socket;
        player=new Player(name);
        toClient= socket.getOutputStream();
        fromClient = socket.getInputStream();
         writer = new PrintWriter(toClient, true);
         reader = new BufferedReader(new InputStreamReader(fromClient));
    }


    public void create(){
        if(board!=null) {
            writer.println("esti deja intr un joc ");
            writer.flush();
        }
        else{
            board=game.create(player);
            writer.println("joc creat. Mutari disponibile "+board.getEmptySpots());
            writer.flush();
        }
    }

    public void joinGame() {
        if (board != null) {
            writer.println("esti deja intr un joc ");
            writer.flush();
        } else {

            board =game.join(player);
            if (board == null) {
                writer.println(" nu exista niciun joc disponibil ");
                writer.flush();
            } else {
                writer.println("ai intrat in joc. Mutarile disponibile sunt : "+board.getEmptySpots());
                writer.flush();

            }
        }
    }



    public void run() {
        String line;

        while(true){
            try {
                line = reader.readLine();

                if (line.contains("exit")) {
                    writer.println("Server stoped ");
                    break;
                } else if (line.contains("create")) {
                     create();

                } else if (line.contains("join")) {
                    joinGame();
                } else if (line.contains("submit")) {
                    System.out.print(line.substring(7, 8));
                    System.out.print(line.substring(8, 9));

                   if(submit(line)==false)
                       break;
                }else {
                    writer.println("comanda nerecunoscuta");
                    writer.flush();
                }

                } catch (IOException e) {
                    e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean submit(String line) {

            if(board==null){
                writer.println("nu esti inca intr un joc  ");
                writer.flush();
                return true;
            }


            Integer i = Integer.parseInt(line.substring(7, 8));
            Integer j = Integer.parseInt(line.substring(8, 9));

            Integer result =board.picked(i,j,player);
            if(result==null){
                writer.println("jocul s a terminat. a castigant jucatorul "+player.piesa);
                writer.flush();
               return false;
            }
            if(result==1)
            {
                writer.println("jocul s a terminat. ai catigat ");
                writer.flush();
                return false;
            }
            writer.println("submited "+i.toString()+""+j.toString()+". Mutari disponibile "+board.getEmptySpots());
            writer.flush();
            return true;



    }
}

