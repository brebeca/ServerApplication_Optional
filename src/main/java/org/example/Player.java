package org.example;

public class Player {
    public String name;
    public int piesa;

    Player(String name){
        this.name=name;
    }
    public void setPiesa(int piesa){
        this.piesa=piesa;
    }

    public int getPiesa() {
        return piesa;
    }
}
