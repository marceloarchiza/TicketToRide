package com.emmettito.tickettoride.presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class LobbyPresenter extends Observable {

    private List<lobbyView> observers;

    public LobbyPresenter() {
        observers = new ArrayList<>();
    }

    public interface lobbyView {
        int createNewGame(String gameName);

        boolean joinGame(int gameID);
    }
}
