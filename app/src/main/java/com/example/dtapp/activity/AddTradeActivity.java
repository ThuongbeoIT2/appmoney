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

public class AddTradeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView textView3;
    private TextInputLayout titleEditText;
    private EditText dateEditText;
    private TextInputLayout costEditText;
    private TextInputLayout cateNameEditText;
    private Button btnSelectCate;
    private Button btnAddTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);

        // Ánh xạ đối tượng từ layout XML
        mapViews();
        cateNameEditText.getEditText().setText(Storerage.getCatename());
        // Thêm các sự kiện hoặc xử lý tương ứng ở đây
        setUpButtons();

    }

    private void mapViews() {
        btnBack = findViewById(R.id.btnBack);
        titleEditText = findViewById(R.id.titletrade);
        dateEditText = findViewById(R.id.dateEditText);
        costEditText = findViewById(R.id.Cost);
        cateNameEditText = findViewById(R.id.cateName);
        btnSelectCate = findViewById(R.id.btnSlCate);
        btnAddTrade = findViewById(R.id.buttonaddtrade);
    }

    private void setUpButtons() {
        btnBack.setOnClickListener(view -> {
            Storerage.setCatename("Chưa xác định");
            Storerage.setCateId(-1);
            // Xử lý khi nhấn nút back
            finish(); // Kết thúc hoạt động hiện tại
        });
        cateNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        btnSelectCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        btnAddTrade.setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường nhập liệu
            String title = titleEditText.getEditText().getText().toString();
            String date = dateEditText.getText().toString();
            String cost = costEditText.getEditText().getText().toString();

            // Kiểm tra xem có thông tin đủ để thêm Trade không
            if (title.isEmpty() || date.isEmpty() || cost.isEmpty()) {
                // Nếu một trong các trường trống, hiển thị thông báo
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                ApiService apiService= ApiClient.getInstance().getMyApi();
                Call<String> rs = apiService.InsertTrade(ApiClient.getAccessToken(), Long.parseLong(cost),Storerage.getCateId(),title,date);
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

        // Xử lý sự kiện khi nhấn vào trường ngày
        dateEditText.setOnClickListener(view -> {
            showDatePickerDialog();
        });

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel(calendar);
        };
        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateLabel(Calendar calendar) {
        String myFormat = "dd/MM/yyyy"; // Định dạng ngày
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(calendar.getTime()));
    }
}
