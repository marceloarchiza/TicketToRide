package com.emmettito.tickettoride.views.GameActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emmettito.models.Cards.TrainCard;
import com.emmettito.models.Game;
import com.emmettito.models.Player;
import com.emmettito.tickettoride.R;
import com.emmettito.tickettoride.TestDriver;
import com.emmettito.tickettoride.presenters.GamePresenter;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private Button chatButton;
    GamePresenter presenter = new GamePresenter(this);
    private Button trainCard1;
    private Button trainCard2;
    private Button trainCard3;
    private Button trainCard4;
    private Button trainCard5;
    private Button deckTrainCards;
    private Button deckDestinationCards;
    private Button viewDestinationCardsButton;
    private Button leaveGameButton;
    private RecyclerView playerListRecycle;
    private RecyclerView.Adapter playerListAdapter;
    private RecyclerView.LayoutManager playerListLayoutManager;
    private List<String[]> players = new ArrayList<>();


    private Button testDriverButton;
    private GameActivity mGameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = Game.getInstance();
        // Get players
        ArrayList<Player> playerList = presenter.getPlayers();
        setupPlayerList(playerList);
        game.setPlayers(playerList);
        Toast.makeText(this, "Game Started!", Toast.LENGTH_SHORT).show();

        chatButton = (Button) findViewById(R.id.openChatButton);
        chatButton.setEnabled(true);
        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                startActivity(intent);
            }
        });

        leaveGameButton = (Button) findViewById(R.id.leaveGameButton);
        leaveGameButton.setEnabled(true);
        leaveGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the join game screen
                Toast.makeText(v.getContext(), "Leaving game", Toast.LENGTH_SHORT).show();
                //GameActivity.super.onBackPressed();
            }
        });

        deckTrainCards = (Button) findViewById(R.id.TrainCardsDeck);
        deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
        deckTrainCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Drawing train card", Toast.LENGTH_SHORT).show();
                //helper function that makes sure there's cards in the deck
                if(checkTrainCardDeck()){
                    game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                      game.getTrainCardDeck().getAvailable().remove(0));
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                else{
                    Toast.makeText(v.getContext(), "No available train cards!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trainCard1 = (Button) findViewById(R.id.trainCard1);
        trainCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Drawing face-up card 1", Toast.LENGTH_SHORT).show();
                //remove the card from faceUp and add it to the player's hand
                game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                        game.getTrainCardDeck().getFaceUpCards().remove(0));
                //check if the deck is empty
                if(checkTrainCardDeck()) {
                    //substitute the card from the available deck for the card in the faceUp array
                    TrainCard newCard = game.getTrainCardDeck().getAvailable().remove(0);
                    game.getTrainCardDeck().getFaceUpCards().add(0, newCard);
                    //update the faceUp card's background
                    trainCard1.setBackground(updateFaceUpCard(newCard));
                    //update the available train cards
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                //no available cards
                else{
                    //set the background as nothing
                    trainCard1.setBackgroundColor(0x00);
                    trainCard1.setBackgroundResource(android.R.drawable.btn_default);
                    //insert a null object into the array (so the other cards don't shift indexes)
                    game.getTrainCardDeck().getFaceUpCards().add(0, null);
                    //turn off the button
                    trainCard1.setEnabled(false);
                    /**
                     * TODO: A listener/poller that refreshes the button when cards become available (after someone discards)
                     */
                }
            }
        });
        //when the game starts, we don't have to check if there's at least 5 cards in the faceUp pile
        trainCard1.setBackground(updateFaceUpCard(game.getTrainCardDeck().getFaceUpCards().get(0)));

        trainCard2 = (Button) findViewById(R.id.trainCard2);
        trainCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Drawing face-up card 2", Toast.LENGTH_SHORT).show();
                game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                  game.getTrainCardDeck().getFaceUpCards().remove(1));
                if(checkTrainCardDeck()) {
                    TrainCard newCard = game.getTrainCardDeck().getAvailable().remove(0);
                    game.getTrainCardDeck().getFaceUpCards().add(1, newCard);
                    trainCard2.setBackground(updateFaceUpCard(newCard));
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                else{
                    trainCard2.setBackgroundColor(0x00);
                    trainCard2.setBackgroundResource(android.R.drawable.btn_default);
                    game.getTrainCardDeck().getFaceUpCards().add(1, null);
                    trainCard2.setEnabled(false);
                }
            }
        });
        trainCard2.setBackground(updateFaceUpCard(game.getTrainCardDeck().getFaceUpCards().get(1)));

        trainCard3 = (Button) findViewById(R.id.trainCard3);
        trainCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Drawing face-up card 3", Toast.LENGTH_SHORT).show();
                game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                        game.getTrainCardDeck().getFaceUpCards().remove(2));
                if(checkTrainCardDeck()) {
                    TrainCard newCard = game.getTrainCardDeck().getAvailable().remove(0);
                    game.getTrainCardDeck().getFaceUpCards().add(2, newCard);
                    trainCard3.setBackground(updateFaceUpCard(newCard));
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                else{
                    trainCard3.setBackgroundColor(0x00);
                    trainCard3.setBackgroundResource(android.R.drawable.btn_default);
                    trainCard3.setEnabled(false);
                    game.getTrainCardDeck().getFaceUpCards().add(2, null);
                }
            }
        });
        trainCard3.setBackground(updateFaceUpCard(game.getTrainCardDeck().getFaceUpCards().get(2)));

        trainCard4 = (Button) findViewById(R.id.trainCard4);
        trainCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Drawing face-up card 4", Toast.LENGTH_SHORT).show();
                game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                        game.getTrainCardDeck().getFaceUpCards().remove(3));
                if(checkTrainCardDeck()) {
                    TrainCard newCard = game.getTrainCardDeck().getAvailable().remove(0);
                    game.getTrainCardDeck().getFaceUpCards().add(3, newCard);
                    trainCard4.setBackground(updateFaceUpCard(newCard));
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                else{
                    trainCard4.setBackgroundColor(0x00);
                    trainCard4.setBackgroundResource(android.R.drawable.btn_default);
                    trainCard4.setEnabled(false);
                    game.getTrainCardDeck().getFaceUpCards().add(3, null);
                }
            }
        });
        trainCard4.setBackground(updateFaceUpCard(game.getTrainCardDeck().getFaceUpCards().get(3)));

        trainCard5 = (Button) findViewById(R.id.trainCard5);
        trainCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(), "Drawing face-up card 5", Toast.LENGTH_SHORT).show();
                game.getPlayers().get(game.getPlayerTurnIndex()).getTrainCards().add(
                        game.getTrainCardDeck().getFaceUpCards().remove(4));
                if(checkTrainCardDeck()) {
                    TrainCard newCard = game.getTrainCardDeck().getAvailable().remove(0);
                    game.getTrainCardDeck().getFaceUpCards().add(4, newCard);
                    trainCard5.setBackground(updateFaceUpCard(newCard));
                    deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
                }
                else{
                    trainCard5.setBackgroundColor(0x00);
                    trainCard5.setBackgroundResource(android.R.drawable.btn_default);
                    trainCard5.setEnabled(false);
                    game.getTrainCardDeck().getFaceUpCards().add(4, null);
                }
            }
        });
        trainCard5.setBackground(updateFaceUpCard(game.getTrainCardDeck().getFaceUpCards().get(4)));

        deckDestinationCards = (Button) findViewById(R.id.deckDestinationCards);
        deckDestinationCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Drawing 3 destination cards", Toast.LENGTH_SHORT).show();
            }
        });

        //activate the draw card buttons if it's the player's turn
        if(game.isPlayerTurn(game.getPlayers().get(game.getPlayerTurnIndex()))) {
            deckTrainCards.setEnabled(true);
            trainCard1.setEnabled(true);
            trainCard2.setEnabled(true);
            trainCard3.setEnabled(true);
            trainCard4.setEnabled(true);
            trainCard5.setEnabled(true);
            deckDestinationCards.setEnabled(true);
        }
        else{
            deckTrainCards.setEnabled(false);
            trainCard5.setEnabled(false);
            trainCard4.setEnabled(false);
            trainCard3.setEnabled(false);
            trainCard2.setEnabled(false);
            trainCard1.setEnabled(false);
            deckDestinationCards.setEnabled(false);
        }

        viewDestinationCardsButton = (Button) findViewById(R.id.viewDesinationCardsButton);
        viewDestinationCardsButton.setEnabled(true);
        viewDestinationCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Open a view to see the player's destination cards", Toast.LENGTH_SHORT).show();
            }
        });

        /** Set up recycler view **/
        playerListRecycle = (RecyclerView) findViewById(R.id.my_recycler_view);
        playerListLayoutManager = new LinearLayoutManager(this);
        playerListRecycle.setLayoutManager(playerListLayoutManager);
        playerListAdapter = new PlayerInfoAdapter(players);
        playerListRecycle.setAdapter(playerListAdapter);

        mGameActivity = this;

        testDriverButton = (Button) findViewById(R.id.testDriverButton);
        testDriverButton.setEnabled(true);
        testDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Starting test driver", Toast.LENGTH_SHORT).show();

                TestDriver driver = new TestDriver(mGameActivity, game);

                try {
                    driver.runTests();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //after setting up/inflating, initialize the game-starting processes
        startGame();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void startGame(){
        //assign each player a color: DONE
        //determine player order: DONE
        //have the server randomly select select 4 train cards for each player
        //have the server select 3 destination cards for each player.
            //and allow the player to discard 0 or 1 of them
    }

    public void updatePlayerDisplay() {
        System.out.println(players);
        setupPlayerList(game.getPlayers());

        if (playerListAdapter == null) {
            return;
        }

        playerListAdapter.notifyDataSetChanged();
        System.out.println(players);
    }

    private void setupPlayerList(ArrayList<Player> playerList){
        List<String[]> newPlayersList = new ArrayList<>();
        for (Player p : playerList){
            String[] newPlayer = new String[6];
            newPlayer[0] = p.getColor().toString();
            newPlayer[1] = p.getPlayerName();
            newPlayer[2] = Integer.toString(p.getPoints());
            newPlayer[3] = Integer.toString(p.getPosition());
            newPlayer[4] = Integer.toString(p.getTrainCards().size());
            newPlayer[5] = Integer.toString(p.getDestinationCards().size());
            newPlayersList.add(newPlayer);
        }
        if (newPlayersList.size() > 0){
            players.clear();
            players.addAll(newPlayersList);
        }
    }

    //this will update the chosen faceUp card background
    private Drawable updateFaceUpCard(TrainCard newCard){
        switch (newCard.getColor()){
            case Wild:
                return getDrawable(R.drawable.wild_train_card);
            case Red:
                return getDrawable(R.drawable.red_train_card);
            case Blue:
                return getDrawable(R.drawable.blue_train_card);
            case Pink:
                return getDrawable(R.drawable.pink_train_card);
            case Black:
                return getDrawable(R.drawable.black_train_card);
            case Green:
                return getDrawable(R.drawable.green_train_card);
            case White:
                return getDrawable(R.drawable.white_train_card);
            case Orange:
                return  getDrawable(R.drawable.orange_train_card);
            case Yellow:
                return getDrawable(R.drawable.yellow_train_card);

            default: //should be unreachable
                return getDrawable(R.drawable.back_of_train_card);
        }
    }

    //determines whether drawing a train card from the deck is possible
    private boolean checkTrainCardDeck(){
        if(game.getTrainCardDeck().getAvailable().size() > 0){
            return true;
        }
        else if(game.getTrainCardDeck().getDiscardPile().size() > 0){
            //set the discard pile as the available pile
            game.getTrainCardDeck().setAvailable(game.getTrainCardDeck().getDiscardPile());
            //reset the discard pile
            game.getTrainCardDeck().setDiscardPile(new ArrayList<TrainCard>());
            //shuffle the deck
            game.getTrainCardDeck().shuffle();
            //reset the deck size
            deckTrainCards.setText(String.valueOf(game.getTrainCardDeck().getSizeAvailable()));
            return true;
        }
        else return false;
    }
}
