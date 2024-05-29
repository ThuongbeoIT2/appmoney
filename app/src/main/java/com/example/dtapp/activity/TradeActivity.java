    package com.example.dtapp.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dtapp.R;
import com.example.dtapp.adapter.TradeAdapter;
import com.example.dtapp.model.TradeResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class TradeActivity extends AppCompatActivity {

        private SearchView searchView;
        private Button searchButton;
        private RecyclerView rcvTrade;
        private ImageView backButton;
        List<TradeResponse> tradeResponses = new ArrayList<>();
        private List<TradeResponse> filteredList = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trade);

           anhxa();
            // Khởi tạo Adapter cho RecyclerView
            rcvTrade.setLayoutManager(new LinearLayoutManager(this));

            // Thiết lập LayoutManager cho RecyclerView
            rcvTrade.setLayoutManager(new LinearLayoutManager(this));
            DividerItemDecoration itemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
            rcvTrade.addItemDecoration(itemDecoration);
            // Tạo danh sách giả định các danh mục


            ApiService apiService= ApiClient.getInstance().getMyApi();
            Call<List<TradeResponse>> call = apiService.GetTradeLogNow(ApiClient.getAccessToken());
            TradeAdapter adapter = new TradeAdapter();
            call.enqueue(new Callback<List<TradeResponse>>() {
                @Override
                public void onResponse(Call<List<TradeResponse>> call, Response<List<TradeResponse>> response) {

                    tradeResponses= response.body();
                    adapter.setTradeList(tradeResponses);
                    rcvTrade.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<TradeResponse>> call, Throwable t) {

                }
            });
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Đóng Activity hiện tại và quay lại Activity trước đó (nếu có)
                }
            });
            // Xử lý sự kiện khi nhấn nút tìm kiếm
            searchButton.setOnClickListener(v -> {
                String query = searchView.getQuery().toString();
                filteredList.clear();
                for (TradeResponse item : tradeResponses){
                    if (item.getCateName().toLowerCase().contains(query.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                adapter.setTradeList(filteredList);
                rcvTrade.setAdapter(adapter);
            });


        }

        private void anhxa() {
            // Ánh xạ các đối tượng từ layout
            searchView = findViewById(R.id.searchView);
            searchButton = findViewById(R.id.searchButton);
            rcvTrade = findViewById(R.id.rcvTrade);
            backButton=findViewById(R.id.btnBack);
        }
    }