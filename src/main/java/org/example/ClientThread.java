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


    ClientThread(Socket socket, String name, Game game) throws IOException {
        this.game=game;
        socket=socket;
        player=new Player(name);
        toClient= socket.getOutputStream();
        fromClient = socket.getInputStream();
         writer = new PrintWriter(toClient, true);
         reader = new BufferedReader(new InputStreamReader(fromClient));
    }


    /**
     * se verifica daac exista un board initializat si daca nu se initializeaza unul
     * se returneaza si mutarile disponibile
     */
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

    /**
     * se verifica daca boardul este initializat
     * daca nu este intializat servarul il adauga la un joc existent deja(daca exista vreunul)
     */
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


    /**
     * in functie de comenzile introduse de apeleaza functile corespunzatoare
     * daca comanda nu este recunoscuta se trmite un mesaj de eroare
     */
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

    /**
     * daca boardul nu este intializat mutarea nu se executa si se trmite mesaj explicativ
     * se separa i-ul si j-ul din comanda clientului
     * se apeleaza functia picked din board care "pune piesa" daca se poate
     * daca intoarece null inseamna ca jocul s a terminat
     * daca intoarce e inseamana ca jucatorul este castigator
     * daca intoarce 0 jocul continua
     * @param line comanda
     * @return
     */

    private boolean submit(String line) {

            if(board==null){
                writer.println("nu esti inca intr un joc  ");
                writer.flush();
                return true;
            }


            Integer i = Integer.parseInt(line.substring(7, 8));
            Integer j = Integer.parseInt(line.substring(8, 9));

            Integer result =board.picked(i,j,player);
            System.out.println("rezultatul este" + result);
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

