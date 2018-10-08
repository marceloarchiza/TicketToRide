package com.emmettito.tickettoride;

public class Client {

    private static Client client = null;

    //adding a comment to test my pushing capabilities

    String token;
    String user;
    String gameName;

    private Client()
    {
        token = null;
        user = null;
        gameName = null;
    }

    public static Client getInstance()
    {
        if (client == null) {
            client = new Client();
        }

        return client;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void deleteToken() {
        this.token = null;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void userToken() {
        this.user = null;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void deleteGameName() {
        this.gameName = null;
    }
}
