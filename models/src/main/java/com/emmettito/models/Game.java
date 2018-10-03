package com.emmettito.models;

import java.util.ArrayList;

public class Game {
    /** Variables **/
    String gameName;
    ArrayList<Player> players;

    /** Setters **/
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /** Getters **/
    public String getGameName() {
        return gameName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
