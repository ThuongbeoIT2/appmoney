package com.example.dtapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dtapp.R;
import com.example.dtapp.model.ProfileResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextInputLayout  firstNameTextView, lastNameTextView, phoneTextView, addressTextView;
    private TextView emailTextView;
    private ImageButton backButton;
    private CircleImageView avatarImageView;
    private TextView changePasswordTextView, logoutTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        anhxa();
        ApiService apiService= ApiClient.getInstance().getMyApi();
        Call<ProfileResponse> call= apiService.GetProfile(ApiClient.getAccessToken());
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                emailTextView.setText(ApiClient.getUsername());
                firstNameTextView.getEditText().setText(profileResponse.getFirstname());
                firstNameTextView.getEditText().setEnabled(true);
                lastNameTextView.getEditText().setText(profileResponse.getLastname());
                lastNameTextView.getEditText().setEnabled(true);
                addressTextView.getEditText().setText(profileResponse.getAddress());
                addressTextView.getEditText().setEnabled(true);
                phoneTextView.getEditText().setText(profileResponse.getPhoneNumber());
                phoneTextView.getEditText().setEnabled(true);
                Glide.with(getApplicationContext())
                        .load(profileResponse.getAvatar())
                        .apply(new RequestOptions().override(avatarImageView.getWidth(), avatarImageView.getHeight()))
                        .into(avatarImageView);

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
        // Thiết lập sự kiện click cho nút quay lại (backButton)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút quay lại được click
                finish(); // Đóng Activity hiện tại và quay lại Activity trước đó (nếu có)
            }
        });

        // Thiết lập sự kiện click cho TextView "Đổi mật khẩu" (changePasswordTextView)
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện click cho TextView "Đăng xuất" (logoutTextView)
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.setUsername("");
                ApiClient.setAccessToken("");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void anhxa() {
        emailTextView = findViewById(R.id.email);
        firstNameTextView = findViewById(R.id.firstname);
        lastNameTextView = findViewById(R.id.lastname);
        phoneTextView = findViewById(R.id.phone);
        addressTextView = findViewById(R.id.address);
        backButton = findViewById(R.id.btnBack);
        avatarImageView = findViewById(R.id.avatar);
        changePasswordTextView = findViewById(R.id.changepass);
        logoutTextView = findViewById(R.id.logout);
    }
}