package com.emmettito.tickettorideserver.database;

import com.emmettito.models.Cards.DestinationCard;
import com.emmettito.models.Cards.TrainCard;
import com.emmettito.models.Cards.TrainColor;

public class DeckDao {
    /** Variables **/
    private static Database dbInstance = Database.getInstance();

    public boolean addDestCard(String gameName){

        return false;
    }
    public DestinationCard removeDestCard(String gameName){

        return new DestinationCard();
    }
    public DestinationCard getDestCard(String gameName){

        return new DestinationCard();
    }
    public boolean addTrainCard(String gameName){

        return false;
    }
    public TrainCard removeTrainCard(String gameName){

        return new TrainCard(TrainColor.Black);
    }
    public TrainCard getTrainCard(String gameName){

        return new TrainCard(TrainColor.Black);
    }
}
