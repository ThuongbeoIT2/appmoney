package com.example.dtapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.dtapp.R;
import com.example.dtapp.model.AuthenticationResponse;
import com.example.dtapp.model.RegisterRequest;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private TextView btnback;
    private TextInputLayout inputemail, inputpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initObject();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getEditText().getText().toString();
                String password = inputpass.getEditText().getText().toString();
                RegisterRequest registerRequest= new RegisterRequest(email,password);
                ApiService apiService = ApiClient.getInstance().getMyApi();
                Call<AuthenticationResponse>  call = apiService.Register(registerRequest);
                call.enqueue(new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                        Toast.makeText(getApplicationContext(),"Vui long kich hoat tai khoan bang tai khoan email.",Toast.LENGTH_LONG);
                        Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Lỗi server",Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }
    private void initObject() {
        btnRegister= findViewById(R.id.btnRegister);
        btnback=findViewById(R.id.btnSignInOfRegister);
        inputemail= findViewById(R.id.txtemail);
        inputpass= findViewById(R.id.txtpassword);
    }
}