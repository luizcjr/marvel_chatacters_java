package com.luiz.marvelcharacters.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luiz.marvelcharacters.R;

public class NoResultAdapter extends RecyclerView.Adapter<NoResultAdapter.NoResultHolder> {

    private String message;
    private int color;
    private Context context;
    private int icon;
    private int marginTop;

    public NoResultAdapter(Context context, String message, int color, int icon, int marginTop) {
        this.context = context;
        this.message = message;
        this.color = color;
        this.icon = icon;
        this.marginTop = marginTop;
    }

    @NonNull
    @Override
    public NoResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_no_result, parent, false);
        return new NoResultAdapter.NoResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoResultHolder holder, int position) {
        holder.message.setText(message);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.icon.getLayoutParams();

        layoutParams.setMargins(0, marginTop, 0, 0);

        holder.icon.setLayoutParams(layoutParams);
        holder.message.setTextColor(context.getResources().getColor(color));

        if (icon != 0) {
            Glide.with(context)
                    .asBitmap()
                    .load(icon)
                    .into(holder.icon);
        } else {
            LinearLayout.LayoutParams layoutParamsMessage = (LinearLayout.LayoutParams) holder.message.getLayoutParams();

            layoutParamsMessage.setMargins(0, marginTop, 0, 0);

            holder.message.setLayoutParams(layoutParamsMessage);

            holder.icon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class NoResultHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private ImageView icon;

        private NoResultHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.txtMessage);
            icon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
