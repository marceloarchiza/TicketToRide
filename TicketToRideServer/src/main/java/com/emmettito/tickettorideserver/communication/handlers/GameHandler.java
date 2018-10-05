package com.emmettito.tickettorideserver.communication.handlers;

import com.emmettito.models.CommandModels.GameCommandType;
import com.emmettito.models.Results.Result;
import com.emmettito.tickettorideserver.communication.Serializer;
import com.emmettito.tickettorideserver.game.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class GameHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        /** Variables */
        Serializer serializer = new Serializer();
        Result result = new Result();
        InputStream input = httpExchange.getRequestBody();
        URI uri;
        String[] requestURI;
        String commandType;
        Headers head = httpExchange.getRequestHeaders();
        String authToken = head.getFirst("Authorization");

        try {
            /** Get Path */
            uri = httpExchange.getRequestURI();
            requestURI = uri.getPath().split("/");

            if (requestURI.length < 3){
                List<GameCommandType> commandTypes = Arrays.asList(GameCommandType.values());
                throw new Exception("GameCommandType is invalid. Make sure to use one of the following commands: " + commandTypes);
            }else{
                commandType = requestURI[2];
            }

            switch(commandType.toLowerCase()){
                case "completedestcard":
                    result = new CompleteDestCardCommand().execute(input, authToken);
                    break;
                case "endgame":
                    result = new EndGameCommand().execute(input, authToken);
                    break;
                case "startgame":
                    result = new StartGameCommand().execute(input, authToken);
                    break;
                case "drawtrain":
                    result = new DrawTrainCommand().execute(input, authToken);
                    break;
                case "claimroute":
                    result = new ClaimRouteCommand().execute(input, authToken);
                    break;
                case "drawdestcard":
                    result = new DrawDestCardCommand().execute(input, authToken);
                    break;
                case "getscore":
                    result = new GetScoreCommand().execute(input, authToken);
                    break;
                case "playerturn":
                    result = new PlayerTurnCommand().execute(input, authToken);
                    break;
                case "quitgame":
                    result = new QuitGameCommand().execute(input, authToken);
                    break;
                case "removegame":
                    result = new RemoveGameCommand().execute(input, authToken);
                    break;
                default:
                    throw new Exception("Path is invalid. This URL Path does not have permissions to make those changes.");
            }
        }
        catch(Exception e){
            result = new Result(false, "GameHandler: " + e.getMessage());
        }
        finally {
            /** Return Result Message */
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStreamWriter send = new OutputStreamWriter(httpExchange.getResponseBody(), Charset.forName("UTF-8"));
            String output = serializer.serialize(result);
            send.write(output);
            send.close();
        }
    }
}
