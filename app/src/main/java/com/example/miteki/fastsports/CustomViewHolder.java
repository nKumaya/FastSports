package com.example.miteki.fastsports;

import android.view.View;

import com.example.miteki.fastsports.model.CardViewModel;
import com.stfalcon.chatkit.messages.MessageHolders;

public class CustomViewHolder extends MessageHolders.BaseMessageViewHolder<CardViewModel>{
    @Override
    public void onBind(CardViewModel cardViewModel) {

    }

    public CustomViewHolder(View itemView) {
        super(itemView);
    }
}
