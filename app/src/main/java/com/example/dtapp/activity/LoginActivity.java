package com.example.dtapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtapp.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<TradeItem> tradeItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo danh sách tradeItemList
        TradeItem tradeItem1 = new TradeItem("Mục 1", "Ngày 1", "100,000.00");
        tradeItemList.add(tradeItem1);

        TradeItem tradeItem2 = new TradeItem("Mục 2", "Ngày 2", "200,000.00");
        tradeItemList.add(tradeItem2);

        TradeItem tradeItem3 = new TradeItem("Mục 3", "Ngày 3", "300,000.00");
        tradeItemList.add(tradeItem3);

        TradeItem tradeItem4 = new TradeItem("Mục 4", "Ngày 4", "400,000.00");
        tradeItemList.add(tradeItem4);

        // Tìm và tham chiếu đến RecyclerView
        recyclerView = findViewById(R.id.rcvTrade);

        // Tạo và cấu hình LayoutManager cho RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Tạo TradeAdapter và gán danh sách tradeItemList cho nó
        TradeAdapter adapter = new TradeAdapter(tradeItemList);

        // Gán adapter cho RecyclerView
        recyclerView.setAdapter(adapter);
    }

    // TradeItem class
    public static class TradeItem {
        private String itemName;
        private String date;
        private String price;

        public TradeItem(String itemName, String date, String price) {
            this.itemName = itemName;
            this.date = date;
            this.price = price;
        }

        public String getItemName() {
            return itemName;
        }

        public String getDate() {
            return date;
        }

        public String getPrice() {
            return price;
        }
    }

    // TradeAdapter class
    public static class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {
        private List<TradeItem> tradeItemList;

        public TradeAdapter(List<TradeItem> tradeItemList) {
            this.tradeItemList = tradeItemList;
        }

        @NonNull
        @Override
        public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemtrade_view, parent, false);
            return new TradeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
            TradeItem tradeItem = tradeItemList.get(position);
            holder.bind(tradeItem);
        }

        @Override
        public int getItemCount() {
            return tradeItemList.size();
        }

        // ViewHolder để ánh xạ và hiển thị dữ liệu cho mỗi item trong RecyclerView
        public static class TradeViewHolder extends RecyclerView.ViewHolder {
            private TextView titleTextView;
            private TextView dateTextView;
            private TextView textCost;

            public TradeViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
                textCost = itemView.findViewById(R.id.textCost);
            }

            public void bind(TradeItem tradeItem) {
                titleTextView.setText(tradeItem.getItemName());
                dateTextView.setText(tradeItem.getDate());
                textCost.setText(tradeItem.getPrice());
            }
        }
    }
}