package com.example.dtapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dtapp.R;
import com.example.dtapp.model.ChangePasswordRequest;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    private ImageButton backButton;
    private TextInputLayout taikkhoan, currentPassword, newPassword, confirmationPassword;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Ánh xạ view từ layout
        backButton = findViewById(R.id.btnBack);
        taikkhoan = findViewById(R.id.taikkhoan);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmationPassword = findViewById(R.id.confirmationPassword);
        changePasswordButton = findViewById(R.id.buttondangnhap);
        taikkhoan.getEditText().setText(ApiClient.getUsername());
        // Xử lý sự kiện khi nhấn nút "Quay lại"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại và quay lại Activity trước đó (nếu có)
            }
        });

        // Xử lý sự kiện khi nhấn nút "Đổi mật khẩu"
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                String username = taikkhoan.getEditText().getText().toString();
                String currentPass = currentPassword.getEditText().getText().toString();
                String newPass = newPassword.getEditText().getText().toString();
                String confirmPass = confirmationPassword.getEditText().getText().toString();

                // Kiểm tra xác nhận mật khẩu
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(ChangePassword.this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gọi API để thay đổi mật khẩu
                ApiService apiService = ApiClient.getInstance().getMyApi();
                ChangePasswordRequest changePasswordRequest= new ChangePasswordRequest(currentPass,newPass,confirmPass);
                Call<String> call = apiService.changePassword(ApiClient.getAccessToken(),changePasswordRequest);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ChangePassword.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ChangePassword.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
