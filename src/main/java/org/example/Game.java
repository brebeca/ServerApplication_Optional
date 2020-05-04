package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Board> boardList=new ArrayList<>();

    public  Board create(Player player){

        Board newBoard= new Board(player);
        boardList.add(newBoard);
        return newBoard;
    }

    public Board get_games(){
        for(Board b : boardList){
            if(b.getNr_players()<2)
                return b;
        }
        return null;
    }

    public  synchronized Board join(Player player){
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
