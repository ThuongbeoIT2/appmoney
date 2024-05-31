package com.example.dtapp.activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dtapp.R;
import com.example.dtapp.model.AuthenticationRequest;
import com.example.dtapp.model.AuthenticationResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.example.dtapp.retrofit.NetworkUtils;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextInputLayout inputemail, inputpass;
    private TextView btnSignUp, btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initid();
        if (NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No network available", Toast.LENGTH_SHORT).show();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getEditText().getText().toString();
                String password = inputpass.getEditText().getText().toString();
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
                ApiService apiService = ApiClient.getInstance().getMyApi();
                    Call<AuthenticationResponse> call = apiService.Login(authenticationRequest);
                    call.enqueue(new Callback<AuthenticationResponse>() {
                        @Override
                        public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                            if (response.isSuccessful()) {
                                AuthenticationResponse authenticationResponse = response.body();
                                String accessToken = authenticationResponse.getAccesstoken();
                                Log.i("token",accessToken);
                                // Lưu trữ access token
                                ApiClient.setAccessToken(accessToken);
                                ApiClient.setUsername(email);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Xử lý khi đăng nhập không thành công
                                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                            // Xử lý khi gặp lỗi kết nối
                            Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, FGPassWordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initid() {
        btnLogin = findViewById(R.id.buttondangnhap);
        inputemail = findViewById(R.id.taikkhoan);
        inputpass = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.txtregister);
        btnForgot = findViewById(R.id.txtforgotpass);
    }

    // Kiểm tra dữ liệu trên giao diện
    public boolean validateData(String email, String password) {
        if (email == null || email.isEmpty()) {
            inputemail.setError(getString(R.string.error_message));
            inputemail.requestFocus();
            return false;
        } else if (password == null || password.isEmpty()) {
            inputpass.setError(getString(R.string.error_password));
            inputpass.requestFocus();
            return false;
        }
        return true;
    }
}
