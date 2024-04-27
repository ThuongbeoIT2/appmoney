package com.example.dtapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dtapp.R;
import com.example.dtapp.adapter.TradeAdapter;
import com.example.dtapp.model.CateRespone;
import com.example.dtapp.model.TradeResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.example.dtapp.viewmodel.TradeViewModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Button newButton;
    private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObject();
        welcome.setText("Welcome : "+ ApiClient.getUsername());
        ActionBar();
        ActionViewFlipper();
        TradeViewModel viewModel = new ViewModelProvider(this).get(TradeViewModel.class);

        // Khởi tạo RecyclerView và Adapter
        RecyclerView recyclerView = findViewById(R.id.rcvTrade);


        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        TradeAdapter adapter = new TradeAdapter();

        // Add sample data
//        tradeItemList.add(new TradeResponse(1, "Title 1", "Date 1", 1000, "Category 1"));
//        tradeItemList.add(new TradeResponse(2, "Title 2", "Date 2", 2000, "Category 2"));
//        tradeItemList.add(new TradeResponse(3, "Title 3", "Date 3", 3000, "Category 3"));
        ApiService apiService= ApiClient.getInstance().getMyApi();
        Call<List<TradeResponse>> call = apiService.GetAllTrade(ApiClient.getAccessToken());
        call.enqueue(new Callback<List<TradeResponse>>() {
            @Override
            public void onResponse(Call<List<TradeResponse>> call, Response<List<TradeResponse>> response) {
                List<TradeResponse> tradeItemList = new ArrayList<>();
                tradeItemList= response.body();
                adapter.setTradeList(tradeItemList);
            }

            @Override
            public void onFailure(Call<List<TradeResponse>> call, Throwable t) {

            }
        });

//        // Gắn Adapter cho RecyclerView
        recyclerView.setAdapter(adapter);
//
//        // Lắng nghe LiveData từ ViewModel để cập nhật dữ liệu
//        viewModel.getTradeListLiveData().observe(this, new Observer<List<TradeResponse>>() {
//            @Override// Khởi tạo ViewModel
//
//            public void onChanged(List<TradeResponse> tradeItems) {
//                // Update UI with new data
//                adapter.setTradeList(tradeItems);
//            }
//        });
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao= new ArrayList<>();
        mangquangcao.add("https://res.cloudinary.com/dqvr7kat6/image/upload/v1713225766/usz8nfczs6auhqhpq3wv.png");
        mangquangcao.add("https://res.cloudinary.com/dqvr7kat6/image/upload/v1713225766/usz8nfczs6auhqhpq3wv.png");
        mangquangcao.add("https://res.cloudinary.com/dqvr7kat6/image/upload/v1713225766/usz8nfczs6auhqhpq3wv.png");
        mangquangcao.add("https://res.cloudinary.com/dqvr7kat6/image/upload/v1713225766/usz8nfczs6auhqhpq3wv.png");

        for (int i=0;i< mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
    }


    private void initObject() {
        viewFlipper=findViewById(R.id.viewflipper);
        welcome= findViewById(R.id.welcome);
    }
}