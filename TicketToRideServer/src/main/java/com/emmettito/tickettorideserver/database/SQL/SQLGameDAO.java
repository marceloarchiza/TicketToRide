package com.emmettito.tickettorideserver.database.SQL;

import com.emmettito.models.CommandModels.Command;
import com.emmettito.models.Game;
import com.emmettito.tickettorideserver.database.IGameDAO;

import java.util.List;

public class SQLGameDAO implements IGameDAO {

    @Override
    public boolean addGame(Game game) {
        return false;
    }

    @Override
    public boolean addCompletedGame(String gameName) {
        return false;
    }

    @Override
    public Game getGame(String gameName) {
        return null;
    }

    @Override
    public boolean clearGames() {
        return false;
    }

    @Override
    public boolean removeGame(String gameName) {
        return false;
    }

    @Override
    public Game updateGame(String gameName, List<Command> commands) {
        return null;
    }

}
