package com.example.dtapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.dtapp.R;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FGPassWordActivity extends AppCompatActivity {
    private Button btnFG;
    private ImageButton backButton;
    private TextInputLayout email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgpass_word);
        anhxa();
        btnFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailfg = email.getEditText().getText().toString();
                ApiService apiService = ApiClient.getInstance().getMyApi();
                Call<String> call = apiService.ForgetPassWord(emailfg);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Lỗi server",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút quay lại được click
                finish(); // Đóng Activity hiện tại và quay lại Activity trước đó (nếu có)
            }
        });
    }

    private void anhxa() {
        backButton= findViewById(R.id.btnBack);
        email= findViewById(R.id.emailfg);
        btnFG= findViewById(R.id.btnFG);
    }
}