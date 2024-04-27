package com.example.dtapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtapp.R;
import com.example.dtapp.model.TradeResponse;

import java.util.ArrayList;
import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private List<TradeResponse> tradeList = new ArrayList<>();

    public void setTradeList(List<TradeResponse> tradeList) {
        this.tradeList = tradeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trade, parent, false);
        return new TradeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        TradeResponse tradeResponse = tradeList.get(position);
        holder.bind(tradeResponse);
    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public class TradeViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView categoryTextView;
        private TextView valueTextView;
        private TextView dateTextView;


        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView= itemView.findViewById(R.id.tradeid);
            categoryTextView = itemView.findViewById(R.id.item_cate);

            valueTextView = itemView.findViewById(R.id.item_value);
            dateTextView = itemView.findViewById(R.id.item_date);
        }

        public void bind(TradeResponse tradeItem) {
            idTextView.setText(String.valueOf(tradeItem.getTradeId()));
            categoryTextView.setText(tradeItem.getCateName());

            valueTextView.setText(String.valueOf(tradeItem.getCost()));
            dateTextView.setText(tradeItem.getDate());
        }
    }
}