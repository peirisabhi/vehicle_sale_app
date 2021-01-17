package com.abhipeiris.androidassignment1shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abhipeiris.androidassignment1shop.R;
import com.abhipeiris.androidassignment1shop.model.CategoryModel;
import com.abhipeiris.androidassignment1shop.model.UserModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> categoryModelList;
    private Listener listener;

    public interface Listener {
        void onClick(int position);
    }

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout linearLayout = holder.linearLayout;
        TextView category = linearLayout.findViewById(R.id.name);
        ImageView img = linearLayout.findViewById(R.id.imageView);

        category.setText(categoryModelList.get(position).getCategory());

        Glide.with(context)
                .load(Uri.parse(categoryModelList.get(position).getImg()))
                .into(img);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            linearLayout = itemView;
        }
    }
}
