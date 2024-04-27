package com.example.dtapp.model;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dtapp.R;

import java.util.Calendar;

public class Add_trade extends AppCompatActivity {
    int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);

        EditText dateEditText = findViewById(R.id.dateEditText);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_trade.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedYear = year;
                                selectedMonth = month + 1; // Ghi nhớ rằng tháng bắt đầu từ 0
                                selectedDay = dayOfMonth;

                                // Cập nhật EditText với ngày được chọn
                                String selectedDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                                dateEditText.setText(selectedDate);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.cateAutoCompleteTextView);
        String[] suggestions = {"Tùy chọn 1", "Tùy chọn 2", "Tùy chọn 3","Hello"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAllCaps(false);
    }
}