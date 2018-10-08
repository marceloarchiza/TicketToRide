package com.emmettito.tickettoride.facades;


import com.emmettito.models.CommandModels.GameLobbyCommands.CreateGameRequest;
import com.emmettito.models.CommandModels.GameLobbyCommands.JoinGameRequest;
import com.emmettito.models.CommandModels.UserCommands.LoginRequest;
import com.emmettito.models.CommandModels.UserCommands.RegisterRequest;
import com.emmettito.models.Results.GameLobbyResult;
import com.emmettito.models.Results.Result;
import com.emmettito.tickettoride.communication.proxy.GameLobbyProxy;
import com.emmettito.tickettoride.communication.proxy.GameProxy;
import com.emmettito.tickettoride.communication.proxy.GameRoomProxy;
import com.emmettito.tickettoride.communication.proxy.LoginProxy;
import com.emmettito.tickettoride.communication.proxy.ScoreProxy;

public class ServerFacade {
    private static ServerFacade instance = null;

    private GameLobbyProxy gameLobbyProxy;
    private GameProxy gameProxy;
    private GameRoomProxy gameRoomProxy;
    private LoginProxy loginProxy;
    private ScoreProxy scoreProxy;

    private ServerFacade() {}

    public static ServerFacade getInstance() {
        if (instance == null) {
            instance = new ServerFacade();
        }

        return instance;
    }

    public GameLobbyResult createNewGame(CreateGameRequest request, String host, String port) {
        gameLobbyProxy = new GameLobbyProxy(host, port);

        return gameLobbyProxy.createGame(request);
    }

    public GameLobbyResult joinGame(JoinGameRequest request, String host, String port) {
        gameLobbyProxy = new GameLobbyProxy(host, port);

        return gameLobbyProxy.joinGame(request);
    }

    public Result login(LoginRequest request) {
        loginProxy = new LoginProxy();

        return loginProxy.login(request);
    }

    public Result register(RegisterRequest request) {
        loginProxy = new LoginProxy();

        return loginProxy.register(request);
    }

    public boolean startGame() {
        gameRoomProxy = new GameRoomProxy();

        return gameRoomProxy.startGame().getSuccess();
    }

    public boolean leaveGame() {
        gameRoomProxy = new GameRoomProxy();

        return gameRoomProxy.leaveGame();
    }
}
