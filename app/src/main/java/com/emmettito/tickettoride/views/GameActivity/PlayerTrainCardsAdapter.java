package com.emmettito.tickettoride.views.GameActivity;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emmettito.models.Cards.Card;
import com.emmettito.models.Cards.TrainCard;
import com.emmettito.models.Cards.TrainColor;
import com.emmettito.tickettoride.R;

import java.util.List;

public class PlayerTrainCardsAdapter extends RecyclerView.Adapter<PlayerTrainCardsAdapter.PlayerTrainCardsHolder>  {

    private List<TrainCard> data;

    class PlayerTrainCardsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView trainCard;
        public String color = "";

        public PlayerTrainCardsHolder(View v) {
            super(v);
            trainCard = v.findViewById(R.id.trainCardView);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.w("adapter", color);
        }
    }

    public PlayerTrainCardsAdapter(List<TrainCard> playerTrainCards) {
        data = playerTrainCards;
    }

    @Override
    public PlayerTrainCardsAdapter.PlayerTrainCardsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_train_cards, parent, false);
        return new PlayerTrainCardsHolder(v);
    }

    @Override
    public void onBindViewHolder(PlayerTrainCardsHolder holder, int position) {
        int trainColor;
        TrainCard card = data.get(position);
        holder.color = card.getColor().toString();

        switch (card.getColor()) {
            case Red: trainColor = R.drawable.red_train_card; break;
            case Pink: trainColor = R.drawable.pink_train_card; break;
            case Orange: trainColor = R.drawable.orange_train_card; break;
            case Yellow: trainColor = R.drawable.yellow_train_card; break;
            case Green: trainColor = R.drawable.green_train_card; break;
            case Blue: trainColor = R.drawable.blue_train_card; break;
            case White: trainColor = R.drawable.white_train_card; break;
            case Black: trainColor = R.drawable.black_train_card; break;
            case Wild: trainColor = R.drawable.wild_train_card; break;
            default: trainColor = R.drawable.back_of_train_card; break;
        }

        Drawable trainImage = holder.trainCard.getResources().getDrawable(trainColor);
        holder.trainCard.setBackground(trainImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

