package com.example.dtapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtapp.R;
import com.example.dtapp.model.CateRespone;
import com.example.dtapp.model.TradeResponse;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(TradeResponse tradeResponse);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(tradeResponse);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public class TradeViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTextView;
        private TextView valueTextView;
        private TextView dateTextView;
        private TextView titleTextView;

        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.item_cate);
            valueTextView = itemView.findViewById(R.id.item_value);
            dateTextView = itemView.findViewById(R.id.item_date);
            titleTextView= itemView.findViewById(R.id.title);
        }

        public void bind(TradeResponse tradeItem) {
            categoryTextView.setText(tradeItem.getCateName());
            valueTextView.setText(String.valueOf(tradeItem.getCost()));
            dateTextView.setText(tradeItem.getDate());
            titleTextView.setText(tradeItem.getTitle());
        }
    }
}