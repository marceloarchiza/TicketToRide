package com.emmettito.tickettorideserver.gameLobby;

import com.emmettito.models.CommandModels.GameLobbyCommand;
import com.emmettito.models.Result;

public class CreateGameCommand implements IGameLobbyCommand{
    public Result execute(GameLobbyCommand obj) throws Exception {

        return new Result();
    }
}
