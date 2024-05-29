package com.example.dtapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dtapp.R;
import com.example.dtapp.model.CateRespone;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(CateRespone cateResponse);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    private List<CateRespone> cateList = new ArrayList<>();

    public void setCateList(List<CateRespone> cateList) {
        this.cateList = cateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }


@Override
public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
    CateRespone cateResponse = cateList.get(position);
    holder.bind(cateResponse);

    // Xử lý sự kiện khi người dùng nhấn vào item
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(cateResponse);
            }
        }
    });
}

    @Override
    public int getItemCount() {
        return cateList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryIdTextView;
        private TextView categoryNameTextView;
        private CircleImageView cateImage; // Thay đổi từ CircleImageView thành ImageView
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIdTextView = itemView.findViewById(R.id.categoryid);
            categoryNameTextView = itemView.findViewById(R.id.category_name);
            cateImage = itemView.findViewById(R.id.image_category); // Ánh xạ ImageView
        }

        public void bind(CateRespone cateResponse) {
            categoryIdTextView.setText(String.valueOf(cateResponse.getCateId()));
            categoryNameTextView.setText(cateResponse.getCateName());
            Glide.with(itemView.getContext())
                    .load(cateResponse.getUrlImage())
                    .apply(new RequestOptions().override(cateImage.getWidth(), cateImage.getHeight()))
                    .into(cateImage);
        }
    }
}
