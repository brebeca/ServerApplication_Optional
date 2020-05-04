package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public List<List<Integer>> board;
    public int nr_players;
    public List<Player> playerList=new ArrayList<>();
    public Integer winner=-1;

    Board(Player player){
        board= new ArrayList<>();
        nr_players=1;
        List<Integer> list=new ArrayList<>();
        for(int j =0 ; j<5 ;j++)
            list.add(-1);
        for(int i =0 ; i< 5 ; i++){
            board.add(list);
        }
        for(List<Integer> list1: board){
            System.out.println(" ");
            for(Integer integer:list){
                System.out.print(integer.toString()+" ");
            }
        }
        player.setPiesa(1);
        playerList.add(player);
    }

    public synchronized Integer  picked(int i, int j , Player player){
        if(winner!=-1)
            return null;
        List<List<Integer>> newBoard= new ArrayList<>();
        List<Integer> aux= new ArrayList<>();
        Integer j1=0,i1=0;
        for(List<Integer> list: board){
            j1=0;
            for(Integer integer:list){
                if(j1==j&&i1==i)
                    aux.add(player.piesa);
                else aux.add(integer);
                j1++;
            }
            newBoard.add(aux);
            aux.clear();
            i1++;
        }

        if( verify_winner(i, j, player.piesa)==true){
            System.out.println("s a returnat true la winner");
            winner=player.piesa;
            return 1;
        }
        return 0;
    }

    private boolean verify_winner(int i , int j , int piesa) {

        List<Integer> aux= board.get(i);
        boolean win=true;
        for(Integer integer : aux){

           if(integer!=piesa){
               System.out.println("in i :"+integer.toString()+" "+piesa);
                    win=false;}
        }

        if(win==true)
            return true;

        win=true;
        for(List<Integer> list: board){
            System.out.println("in j :"+list.get(j).toString()+" "+piesa);
            if(list.get(j)!=piesa) {
                System.out.println("in j :"+list.get(j).toString()+" "+piesa);
                win = false;
            }
        }
        return win;

    }

    public int getNr_players() {
        return nr_players;
    }

    public void setNr_players(int nr_players) {
        this.nr_players = nr_players;
    }

    public void addPlayer(Player player) {
        player.setPiesa(0);
        playerList.add(player);
    }

    public String getEmptySpots() {
        String to_return="";
        Integer i=0,j=0;
        for(List<Integer> list : board){
            j=0;
            for(Integer integer :list)
            {
                if(integer==-1)
                {
                    to_return+=i.toString();
                    to_return+=j.toString();
                    to_return+=" ";
                }
                j++;
            }
            i++;
        }
        return to_return;
    }
}
