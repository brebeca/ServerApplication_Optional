package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Board> boardList=new ArrayList<>();

    /**
     * la crearea unui joc se initializeaza un nou board cu playerul care a facut create si se adauga in lista
     * @param player
     * @return boardul creat
     */
    public  Board create(Player player){

        Board newBoard= new Board(player);
        boardList.add(newBoard);
        return newBoard;
    }

    /**
     * returneaza jocurile disponibile, adica care au mai putin de 2 jucatori
     * @return
     */
    public Board get_games(){
        for(Board b : boardList){
            if(b.getNr_players()<2)
                return b;
        }
        return null;
    }

    /**
     * se face join la primul joc/board disponibil
     * @param player plajerul care face join
     * @return bordul la care s a dat join sau null daca nu s a gasit board disponibil
     */
    public  Board join(Player player){
        player.setPiesa(0);
        for(Board board:boardList) {
            if (board.getNr_players() <2){
                board.setNr_players(board.getNr_players() + 1);
                board.addPlayer(player);
                return board;
            }

        }
        return null;
    }

}
