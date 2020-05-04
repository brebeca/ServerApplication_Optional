package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public List<List<Integer>> board;
    public int nr_players;
    public List<Player> playerList=new ArrayList<>();
    public Integer winner=-1;

    /**
     * se initializeaza boardul cu -1 si se adauga playerul la joc
     * se seteaza "piesa" cu care va juca playerul
     * @param player
     */
    Board(Player player){
        board= new ArrayList<>();
        nr_players=1;
        List<Integer> list=new ArrayList<>();
        for(int j =0 ; j<5 ;j++)
            list.add(-1);
        for(int i =0 ; i< 5 ; i++){
            board.add(list);
        }
        System.out.println("s a constuit");
        display();

        player.setPiesa(1);
        playerList.add(player);
    }

    /**
     * functia syncronized pt ca se face acces la resursa partajaya intre jucatori boardul
     * daca winner este setat inseamna ca cineva a castigat si intoarce null
     * se creaza o copie a boardului in care se inregistreaza piesa pe pozitia [i][j]
     * ->aici este problema :nu am reusit sa adaug cum trebuie piesa pe tabla
     *  copia se adauga in original si se verifica daca exista 5 piese in linie
     *  daca exista 5 piese in linie se inregistreaza in winner piesa castigatoare si returneaza 1
     *  altfel se returneaza 0 si jocul continuna
     * @param i linia
     * @param j coloana
     * @param player playerul care face miscarea
     * @return
     */
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
                //System.out.print(integer.toString());
                j1++;
            }
           // System.out.print(aux.toString());
            newBoard.add(aux);
            aux.clear();
            i1++;
        }

        for(List<Integer> list: newBoard){
           board.set(newBoard.indexOf(list),list);

        }
        System.out.println("s a constuit in pick ");
        display();

        if( verify_winner(i, j, player.piesa)==true){
            System.out.println("s a returnat true la winner");
            display();
            winner=player.piesa;
            return 1;
        }
        return 0;
    }

    public void display() {
        for (List<Integer> list1 : board) {
            System.out.println(" ");
            for (Integer integer : list1) {
                System.out.print(integer.toString() + " ");
            }
        }
    }

    /**
     * cauta daca pe coloana j sau pe linia i exista 5 piese la fel (tabla e 5x5)
     * daca se gasesc 5 piese la rand returneaza true
     * @param i lina
     * @param j coloana
     * @param piesa
     * @return
     */
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
           // System.out.println("in j :"+list.get(j).toString()+" "+piesa);
            if(list.get(j)!=piesa) {
                //System.out.println("in j :"+list.get(j).toString()+" "+piesa);
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

    /**
     * face un string cu pozitiile disponibile, cu -1
     * @return
     */
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
