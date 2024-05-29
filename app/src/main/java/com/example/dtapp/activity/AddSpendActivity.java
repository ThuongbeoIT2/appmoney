package com.example.dtapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dtapp.R;
import com.example.dtapp.model.Storerage;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSpendActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView textView3;

    private TextInputLayout costEditText;
    private TextInputLayout cateNameEditText;
    private Button btnSelectCate;
    private Button btnAddSpend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spend);

        // Ánh xạ đối tượng từ layout XML
        mapViews();
        cateNameEditText.getEditText().setText(Storerage.getCatename());
        // Thêm các sự kiện hoặc xử lý tương ứng ở đây
        setUpButtons();

    }

    private void mapViews() {
        btnBack = findViewById(R.id.btnBack);

        costEditText = findViewById(R.id.spendinglimit);
        cateNameEditText = findViewById(R.id.cateName);
        btnSelectCate = findViewById(R.id.btnSlCate);
        btnAddSpend = findViewById(R.id.buttonaddspend);
    }

    private void setUpButtons() {
        btnBack.setOnClickListener(view -> {
            Storerage.setCatename("Chưa xác định");
            Storerage.setCateId(-1);
            finish(); // Kết thúc hoạt động hiện tại
        });
        cateNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategorySpendActivity.class);
                startActivity(intent);
            }
        });
        btnSelectCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategorySpendActivity.class);
                startActivity(intent);
            }
        });
        btnAddSpend.setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường nhập liệu

            String cost = costEditText.getEditText().getText().toString();

            // Kiểm tra xem có thông tin đủ để thêm Trade không
            if ( cost.isEmpty()) {
                // Nếu một trong các trường trống, hiển thị thông báo
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                ApiService apiService= ApiClient.getInstance().getMyApi();
                Call<String> rs = apiService.InsertSpendingLimit(ApiClient.getAccessToken(), Long.parseLong(cost),Storerage.getCateId());
                rs.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
                        Storerage.setCatename("Chưa xác định");
                        Storerage.setCateId(-1);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Storerage.setCatename("Chưa xác định");
                        Storerage.setCateId(-1);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
