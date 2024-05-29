package com.example.dtapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dtapp.R;
import com.example.dtapp.adapter.CategoryAdapter;
import com.example.dtapp.adapter.SpendingLimitAdapter;
import com.example.dtapp.adapter.TradeAdapter;
import com.example.dtapp.model.CateRespone;
import com.example.dtapp.model.SpendingLimitResponse;
import com.example.dtapp.model.Storerage;
import com.example.dtapp.model.TradeResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalActivity extends AppCompatActivity {

    private SearchView searchView;
    private Button searchButton;
    private Button addSpend;
    private RecyclerView rcvSpend;
    private ImageView backButton;
    private TextInputLayout item_tongquy,item_tongchi;
    List<SpendingLimitResponse> spendingLimitResponses = new ArrayList<>();
    private List<SpendingLimitResponse> filteredList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        anhxa();
        rcvSpend.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập LayoutManager cho RecyclerView
        rcvSpend.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvSpend.addItemDecoration(itemDecoration);
        // Tạo danh sách giả định các danh mục


        ApiService apiService= ApiClient.getInstance().getMyApi();
        Call<List<SpendingLimitResponse>> call = apiService.getAllSpending(ApiClient.getAccessToken());
        SpendingLimitAdapter adapter = new SpendingLimitAdapter();
        call.enqueue(new Callback<List<SpendingLimitResponse>>() {
            @Override
            public void onResponse(Call<List<SpendingLimitResponse>> call, Response<List<SpendingLimitResponse>> response) {
                spendingLimitResponses= response.body();
                long quy=0;
                long chi=0;
                for (SpendingLimitResponse item: spendingLimitResponses){
                    quy+=item.getSpendingLimit();
                    chi+=item.getExpenditure();
                }
                item_tongquy.getEditText().setText(String.valueOf(quy));
                item_tongchi.getEditText().setText(String.valueOf(chi));
                adapter.setSpendingLimitList(spendingLimitResponses);
                rcvSpend.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SpendingLimitResponse>> call, Throwable t) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchButton.setOnClickListener(v -> {
            String query = searchView.getQuery().toString();
            filteredList.clear();
            for (SpendingLimitResponse item : spendingLimitResponses){
                if (item.getCateName().toLowerCase().contains(query.toLowerCase())){
                    filteredList.add(item);
                }
            }
            adapter.setSpendingLimitList(filteredList);
            rcvSpend.setAdapter(adapter);
        });

        addSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSpendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        // Ánh xạ các đối tượng từ layout
        item_tongquy=findViewById(R.id.item_tongquy);
        item_tongchi=findViewById(R.id.item_tongchi);
        searchView = findViewById(R.id.searchView);
        searchButton = findViewById(R.id.searchButton);
        rcvSpend = findViewById(R.id.rcvSpend);
        backButton=findViewById(R.id.btnBack);
        addSpend=findViewById(R.id.buttonaddnewspend);
    }
}