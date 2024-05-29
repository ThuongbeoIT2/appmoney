package com.example.dtapp.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dtapp.R;

import com.example.dtapp.model.SpendingLimitResponse;
import com.example.dtapp.retrofit.ApiClient;
import com.example.dtapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpendingLimitAdapter extends RecyclerView.Adapter<SpendingLimitAdapter.CategoryViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(SpendingLimitResponse spendingLimitResponse);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    private List<SpendingLimitResponse> spendingLimitResponses = new ArrayList<>();

    public void setSpendingLimitList(List<SpendingLimitResponse> spendingLimitResponses) {
        this.spendingLimitResponses = spendingLimitResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spending_limit, parent, false);
        return new CategoryViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        SpendingLimitResponse spendingLimitResponse = spendingLimitResponses.get(position);
        holder.bind(spendingLimitResponse);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                holder.itemView.setBackgroundColor(Color.LTGRAY);
                builder.setMessage("Có thực sự muốn xóa quỹ này không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteSpend(spendingLimitResponse);
                                Toast.makeText(holder.itemView.getContext(), "Xóa thành công",Toast.LENGTH_LONG).show();
                            }

                            private void deleteSpend(SpendingLimitResponse spendingLimitResponse) {
                                ApiService apiService= ApiClient.getInstance().getMyApi();
                                Call<String> call = apiService.DeleteSpend(ApiClient.getAccessToken(),spendingLimitResponse.getSpendId());
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        spendingLimitResponses.remove(spendingLimitResponse);
                                        Toast.makeText(v.getContext(), "Xóa thành công",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Lỗi server",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(spendingLimitResponse);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return spendingLimitResponses.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTextView;
        private TextInputLayout Spendingmlimit;
        private TextInputLayout expenditure;
        private TextInputLayout remainingAmount;
        private ImageView btndelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            Spendingmlimit = itemView.findViewById(R.id.item_spendinglimit);
            categoryNameTextView = itemView.findViewById(R.id.item_cate);
            expenditure = itemView.findViewById(R.id.item_expenditure);
            remainingAmount = itemView.findViewById(R.id.item_remainingAmount);
            btndelete= itemView.findViewById(R.id.btndelete);
        }

        public void bind(SpendingLimitResponse spendingLimitResponse) {
            Spendingmlimit.getEditText().setText(String.valueOf(spendingLimitResponse.getSpendingLimit()));
            categoryNameTextView.setText(spendingLimitResponse.getCateName());
            expenditure.getEditText().setText(String.valueOf(spendingLimitResponse.getExpenditure()));
            remainingAmount.getEditText().setText(String.valueOf(spendingLimitResponse.getRemainingAmount()));

        }
    }
}
