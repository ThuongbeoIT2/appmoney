package com.example.dtapp.activity;

import android.content.Intent;
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
import com.example.dtapp.adapter.CategoryAdapter;
import com.example.dtapp.adapter.CategorySpendAdapter;
import com.example.dtapp.model.CateRespone;
import com.example.dtapp.model.Storerage;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySpendActivity extends AppCompatActivity {

    private SearchView searchView;
    private Button searchButton;
    private RecyclerView rcvCate;
    private ImageView backButton;
    List<CateRespone> cateList = new ArrayList<>();
    private List<CateRespone> filteredList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Ánh xạ các đối tượng từ layout
        searchView = findViewById(R.id.searchView);
        searchButton = findViewById(R.id.searchButton);
        rcvCate = findViewById(R.id.rcvCate);
        backButton=findViewById(R.id.btnBack);
        // Khởi tạo Adapter cho RecyclerView
        rcvCate.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập LayoutManager cho RecyclerView
        rcvCate.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvCate.addItemDecoration(itemDecoration);
        // Tạo danh sách giả định các danh mục


        ApiService apiService= ApiClient.getInstance().getMyApi();
        Call<List<CateRespone>> call = apiService.GetCate(ApiClient.getAccessToken());
        CategorySpendAdapter adapter = new CategorySpendAdapter();
        call.enqueue(new Callback<List<CateRespone>>() {
            @Override
            public void onResponse(Call<List<CateRespone>> call, Response<List<CateRespone>> response) {

                cateList= response.body();
                adapter.setCateList(cateList);
                rcvCate.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CateRespone>> call, Throwable t) {

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
            for (CateRespone item : cateList){
                if (item.getCateName().toLowerCase().contains(query.toLowerCase())){
                    filteredList.add(item);
                }
            }
            adapter.setCateList(filteredList);
            rcvCate.setAdapter(adapter);
        });
        adapter.setOnItemClickListener(new CategorySpendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CateRespone cateResponse) {
                Storerage.setCateId(cateResponse.getCateId());
                Storerage.setCatename(cateResponse.getCateName());
                Intent intent = new Intent(CategorySpendActivity.this, AddSpendActivity.class);
                startActivity(intent);
            }
        });

    }
}
